package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wjf.recyclerviewrefresh.open.ThreeDLayout;

public class Layout3DActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout3_d);
        final ThreeDLayout layout = (ThreeDLayout) findViewById(R.id.td_header);
        //开启触摸模式
        layout.setTouchable(true);

        //改变触摸模式
        //  layout.setTouchMode(ThreeDLayout.MODE_BOTH_X_Y);



        findViewById(R.id.bt_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.startHorizontalAnimate(5000);
            }
        });

    }
}
