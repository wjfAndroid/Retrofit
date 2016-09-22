package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class ViewFilpperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_filpper);
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);
        vf.setFlipInterval(1000);
        vf.setInAnimation(this, R.anim.anim_marquee_in);
        vf.setOutAnimation(this, R.anim.anim_marquee_out);
        vf.startFlipping();

    }
}
