package com.wjf.recyclerviewrefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

public class ViewPropertyAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator);
        Button bt = (Button) findViewById(R.id.button3);
        bt.animate().x(200).y(200).setInterpolator(new AccelerateInterpolator()).setDuration(2000);
        bt.animate().alpha(0).setDuration(2000);



    }
}
