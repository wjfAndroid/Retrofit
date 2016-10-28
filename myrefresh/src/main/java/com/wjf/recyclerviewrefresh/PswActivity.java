package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import com.wjf.recyclerviewrefresh.view.PayPwdEditText;


public class PswActivity extends AppCompatActivity {

    private PayPwdEditText payPwdEditText;
    private PayPwdEditText payPwdEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw);
        payPwdEditText = (PayPwdEditText) findViewById(R.id.ppe_pwd);
        payPwdEditText2 = (PayPwdEditText) findViewById(R.id.ppe_pwd2);

        payPwdEditText.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 30);
        payPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Toast.makeText(PswActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        payPwdEditText2.initStyle(R.drawable.edit_num_bg_red, 8, 0.33f, R.color.colorAccent, R.color.colorAccent, 20);
        payPwdEditText2.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                Toast.makeText(PswActivity.this, "显示明文：" + str, Toast.LENGTH_SHORT).show();
                payPwdEditText2.setShowPwd(false);
            }
        });
    }
}
