package com.wjf.recyclerviewrefresh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wjf.recyclerviewrefresh.util.DisplayUtil;

public class dp2pxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp2px);

        int px = 36;
        Log.e("dp2pxActivity", " px2sp(this,px)" + DisplayUtil.px2sp(this, px));
        Log.e("dp2pxActivity", " convertDipOrPx(this,px)" +DisplayUtil. dip2px(this, px));

    }


}
