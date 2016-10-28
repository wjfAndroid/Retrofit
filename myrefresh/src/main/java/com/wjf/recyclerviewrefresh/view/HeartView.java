package com.wjf.recyclerviewrefresh.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/13.
 */
public class HeartView extends View {
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;
    Path mPath;
    private int mItemWaveLength = 0;
    private int dx = 0;

    public HeartView(Context context) {
        super(context);
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        dstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap);
        srcBitmap = Bitmap.createBitmap(dstBitmap.getWidth(), dstBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mItemWaveLength = dstBitmap.getWidth();

        startAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, mItemWaveLength);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });

        animator.start();
    }

}
