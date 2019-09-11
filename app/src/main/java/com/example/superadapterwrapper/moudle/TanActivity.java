package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutmanagerlib.layoutmanager.tantantest.TanTanControl;
import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.widget.manager.TanLayoutManager;
import com.example.superadapterwrapper.widget.manager.TanTouchCallBack;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.ACTION_DOWN;

public class TanActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private CardView cardView;
    private View like;
    private View noLike;
    //
    private CommonAdapter<String> mAdapter;
    private ItemTouchHelper itemTouchHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, TanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tan);

    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
        like = getView(R.id.like);
        noLike = getView(R.id.noLike);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("数据源" + i);
        }
        mAdapter = new CommonAdapter<String>(this, R.layout.item_tan_layout, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView view = holder.getView(R.id.numText);
                view.setText(s);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder holder = super.onCreateViewHolder(parent, viewType);
                holder.setIsRecyclable(false);
                return holder;
            }
        };
        TanTanControl tanTanControl = new TanTanControl(4
                , 0.05f
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
        mRecyclerView.setLayoutManager(new TanLayoutManager());
        //ItemTouchHelper.Callback callback = new TanTanTouchCallback(mRecyclerView, mAdapter, list, tanTanControl);
        TanTouchCallBack callback = new TanTouchCallBack(mRecyclerView, list);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initLinstener() {
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        toright();
                    }
                }.start();
            }
        });
        noLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        torleft();
                    }
                }.start();
            }
        });
    }

    private void toright() {
        final long downTime = SystemClock.currentThreadTimeMillis();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent down = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), ACTION_DOWN, 100, 300, 0);
                mRecyclerView.dispatchTouchEvent(down);
            }
        });

        //SystemClock.sleep(10);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 110, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 120, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 130, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 140, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent up = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_UP, 100, 300, 0);
                mRecyclerView.dispatchTouchEvent(up);
            }
        });
    }

    private void torleft() {
        final long downTime = SystemClock.currentThreadTimeMillis();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent down = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), ACTION_DOWN, 150, 300, 0);
                mRecyclerView.dispatchTouchEvent(down);
            }
        });

        //SystemClock.sleep(10);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 140, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 130, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 120, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent move = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_MOVE, 110, 300, 0);
                mRecyclerView.dispatchTouchEvent(move);
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MotionEvent up = MotionEvent.obtain(downTime, SystemClock.currentThreadTimeMillis(), MotionEvent.ACTION_UP, 100, 300, 0);
                mRecyclerView.dispatchTouchEvent(up);
            }
        });
    }

}
