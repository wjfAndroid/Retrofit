package com.wjf.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ScrollerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_scroller);
        Button bt_scroll_by = (Button) findViewById(R.id.bt_scroll_by);
        Button bt_scroll_to = (Button) findViewById(R.id.bt_scroll_to);
        bt_scroll_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.scrollBy(-100,-100);
            }
        });
        bt_scroll_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl.scrollTo(-100,-100);
            }
        });
    }
}
