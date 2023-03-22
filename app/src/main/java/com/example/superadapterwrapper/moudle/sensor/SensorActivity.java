package com.example.superadapterwrapper.moudle.sensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.widget.SensorView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    // 定义水平仪的仪表盘
    private SensorView view;
    // 定义水平仪能处理的最大倾斜角，超过该角度，气泡将直接位于边界
    private final int MAX_ANGLE = 30;
    // 定义真机的Sensor管理器
    private SensorManager mSensorManager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SensorActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        // 获取水平仪的组件
        view = findViewById(R.id.main_myview);
        // 获取真机的传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 真机上获取触发的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                // 获取与Y轴的夹角
                float yAngle = values[1];
                // 获取与Z轴的夹角
                float zAngle = values[2];
                // 气泡位于中间时（水平仪完全水平），气泡的X、Y坐标
                int x = (view.back.getWidth() - view.bubble.getWidth()) / 2;
                int y = (view.back.getHeight() - view.bubble.getHeight()) / 2;
                // 如果与z轴的倾斜角还在最大角度之内
                if (Math.abs(zAngle) <= MAX_ANGLE) {
                    // 根据与z轴的倾斜角计算x坐标的变化值（倾斜角度越大，x坐标变化越大）
                    int deltaX = (int) ((view.back.getWidth() - view.bubble
                            .getWidth()) / 2 * zAngle / MAX_ANGLE);
                    x += deltaX;
                }
                // 如果与z轴的倾斜角已经大于MAX_ANGLE,气泡应到最左边
                else if (zAngle > MAX_ANGLE) {
                    x = 0;
                }
                // 如果与Z轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
                else {
                    x = view.back.getWidth() - view.bubble.getWidth();
                }
                // 如果与Y轴的倾斜角还在最大角度之内
                if (Math.abs(yAngle) <= MAX_ANGLE) {
                    // 根据与Y轴的倾斜角计算Y坐标的变化值（倾斜角度越大，Y坐标变化越大）
                    int deltaY = (int) ((view.back.getHeight() - view.bubble
                            .getHeight()) / 2 * zAngle / MAX_ANGLE);
                    y += deltaY;
                }
                // 如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
                else if (yAngle > MAX_ANGLE) {
                    y = view.back.getHeight() - view.bubble.getHeight();
                }
                // 如果与Y轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
                else {
                    y = 0;
                }
                // 如果计算出来的X、Y坐标还位于水平仪的仪表盘内，更新水平仪的气泡坐标
                if (isContain(x, y)) {
                    view.bubbleX = x;
                    view.bubbleY = y;
                }
                // 通知系统重绘MyView组件
                view.postInvalidate();
                break;
        }
    }

    // 计算X、Y点的气泡是否处于水平仪的仪表盘内
    private boolean isContain(int x, int y) {
        // 计算气泡的圆心坐标X、Y
        int bubbleCx = x + view.bubble.getWidth() / 2;
        int bubbleCy = y + view.bubble.getHeight() / 2;
        // 计算水平仪仪表盘的圆心坐标X、Y
        int backCx = view.back.getWidth() / 2;
        int backCy = view.back.getHeight() / 2;
        // 计算气泡的圆心与水平仪仪表盘的圆心之间的距离
        double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx - backCx)
                + (bubbleCy - backCy) * (bubbleCy - backCy));
        // 若两个圆心的距离小于它们的半径差，即可认为处于该店的气泡依然位于仪表盘内
        if (distance < (view.back.getWidth() - view.bubble.getWidth()) / 2) {
            return true;
        } else {
            return false;
        }
    }
}