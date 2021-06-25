package com.example.superadapterwrapper.moudle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.ImageGetterUtils;
import com.example.superadapterwrapper.util.RsaUtil;
import com.idonans.lang.Base64;

public class RsaActivity extends BaseActivity {
    private EditText edit_text;
    private TextView encryption_btn;
    private TextView init_rsa_btn;
    private TextView encryption_text;
    private TextView decrypt_btn;
    private TextView decrypt_text;
    private TextView html_text;

    public static void start(Context context) {
        Intent intent = new Intent(context, RsaActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_rsa);

    }

    @Override
    protected void initView() {
        edit_text = findViewById(R.id.edit_text);
        encryption_btn = findViewById(R.id.encryption_btn);
        init_rsa_btn = findViewById(R.id.init_rsa_btn);
        encryption_text = findViewById(R.id.encryption_text);
        decrypt_btn = findViewById(R.id.decrypt_btn);
        decrypt_text = findViewById(R.id.decrypt_text);
        html_text = findViewById(R.id.html_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String s0 = "课程：高三语文  时长：60分钟   <font color=\"#FF9900\">线上考试</font><img src=\"http://10.6.6.152/ynedut/assets/images/app/1.png\"\\/>";
        String s1 = "需评2人   已评0人 <font color=\"#247BE4\">ssss</font>";
        String s2 = "<div style=\\\" color: #FF6600; font-size: 10px; \\\">进行中</div>";
        String s3 = "需评2人   已评0人   <div style=\\\" color=\"#FF6600\" \\\">待评2</div>人";
        html_text.setText(Html.fromHtml(s0, new ImageGetterUtils.MyImageGetter(this, html_text), null));
    }

    @Override
    protected void initLinstener() {
        init_rsa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RsaUtil.loadPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXjTb6oR8czwHnjnfBJrl8C6DWHzd0Ce49WCxInfjiRh9BYq6twAS2r306zA0pnMPfIiSLYXMt1McnvvaISZ7ZUnEgA5WSLTJBmtOqTcY9f6DRpZQmWg0TtsDmPcJpvLM7Si/8eDD7iANmwWNcpkYBnPqYzM0K+3Kf1qaHLVbskQIDAQAB");
                    RsaUtil.loadPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJeNNvqhHxzPAeeOd8EmuXwLoNYfN3QJ7j1YLEid+OJGH0Firq3ABLavfTrMDSmcw98iJIthcy3Uxye+9ohJntlScSADlZItMkGa06pNxj1/oNGllCZaDRO2wOY9wmm8sztKL/x4MPuIA2bBY1ymRgGc+pjMzQr7cp/WpoctVuyRAgMBAAECgYAfs5V1lfCZKSA/3MhmKgCQ1yncwIjsCAvC30fcJNdkFcEB/2+Evu79e/YZL4ftVXKPoWe9lDPwuVqzkLmnRqcogVNRAVrayodPFIwSqMfgcHijRccty467Jmj8tbNhQYYC3NbylHSIjeCMa3Y/e3GSkfTz2MmdyELQg32jARWAJQJBAOPbRU0ok5KCc/8x6ebZLGUytiFxLR4wV8GcemEpZki1GI60mO5XKD2ZXSRRar984GksXcTGRvr5mjV4OvpnctsCQQCqRTbklxUsfYpY7QptkF2ag0BG+KXcJhv52HQtaqDgJZFSXs9aldChnoY0k83UUPHHj+ahXhe4FOplvTfZg/wDAkEAiBSYWvB00fcfF7XTn47sOLQEmgxhiboNbw/o9sYm1B1zwSaSfyWGXuONAPeu7RXDYIyio7ZXBeSz+GMJtiq6KQJAAPFvM2bu1BdB6+osM50oF5IAiiQYbxa68vBS/GlpyLQMkQM7bMoFpM+G+MWUz0c/f5Z5dY1a3kcnYTbiF09W1wJBAJB8pRG2UAm/ZDEe0mmqSW7y8suubNyw4ovyeB6IsddrqTaxlCzfVb+3QC//6C8DfmnyB4AkxtRgKP95o2EUUdI=");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        encryption_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] encrypt = RsaUtil.encrypt(RsaUtil.publicKey, edit_text.getText().toString().getBytes());
                    encryption_text.setText(Base64.encode(encrypt));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        decrypt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] encrypt = RsaUtil.decrypt(RsaUtil.privateKey, Base64.decode(encryption_text.getText().toString()));
                    decrypt_text.setText(new String(encrypt));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}