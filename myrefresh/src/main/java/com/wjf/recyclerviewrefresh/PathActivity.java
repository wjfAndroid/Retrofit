package com.wjf.recyclerviewrefresh;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wjf.recyclerviewrefresh.view.Wave2View;

public class PathActivity extends AppCompatActivity {
    int y;
    Wave2View wave2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        wave2View = (Wave2View) findViewById(R.id.w2v);
        wave2View.startAnim();
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
            }
        });
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 1000);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                y = (int) valueAnimator.getAnimatedValue();
                Log.d("PathActivity", "y:" + y);
                wave2View.setDy(y);
            }
        });
        animator.start();
    }

}
