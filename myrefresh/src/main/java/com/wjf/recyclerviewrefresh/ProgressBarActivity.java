package com.wjf.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProgressBarActivity extends AppCompatActivity {

    @InjectView(R.id.pb)
    NumberProgressBar pb;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = msg.arg1;
            pb.setProgress(num);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.inject(this);

    }
}
