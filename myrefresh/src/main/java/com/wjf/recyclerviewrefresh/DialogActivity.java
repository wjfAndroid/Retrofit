package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wjf.recyclerviewrefresh.fragment.CustomDialogFragment;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment fragment = new CustomDialogFragment();
                fragment.show(getSupportFragmentManager(), "CustomDialogFragment");

                fragment.setUserInfoListener(new CustomDialogFragment.UserInfoListener() {
                    @Override
                    public void getuserInfo(String id, String psw) {
                        System.out.println("id = [" + id + "], psw = [" + psw + "]");
                    }
                });
            }
        });
    }
}
