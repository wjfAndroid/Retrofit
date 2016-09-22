package com.wjf.recyclerviewrefresh.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.widget.TextView;


public class SimpleTextView extends TextView {
    Paint mPaint;
    int mDx;
    LinearGradient mLinearGradient;
    ValueAnimator valueAnimator;
    Matrix matrix;

    public SimpleTextView(Context context) {
        super(context);
        init();
    }


    public SimpleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = getPaint();
        matrix = new Matrix();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        valueAnimator = ValueAnimator.ofInt(0, getMeasuredWidth() * 2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mDx = value;
                postInvalidate();
            }
        });
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.start();

        mLinearGradient = new LinearGradient(-getMeasuredWidth(), 0, 0, 0,
                new int[]{getCurrentTextColor(), 0xff00ff00, getCurrentTextColor()}, new float[]{0, 0.5f, 1f}, Shader.TileMode.CLAMP);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        matrix.setTranslate(mDx, 0);


        mLinearGradient.setLocalMatrix(matrix);
        mPaint.setShader(mLinearGradient);
        super.onDraw(canvas);

    }
}
