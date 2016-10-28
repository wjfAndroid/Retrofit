package com.wjf.recyclerviewrefresh.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2016/10/14.
 */
public class Wave2View extends View {

    public Wave2View(Context context) {
        super(context);
    }

    public Wave2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Wave2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Path mPath;
    Paint mPaint;
    int waveHeight = 300;
    int waveLength = 700;
    int waveTop = 150;
    int dy;
    private int dx;

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);

    }



    public void setDy(int dy) {
        this.dy = dy;
        postInvalidate();
    }

    //使用rQuadTo，而不是quadTo。相对位移比绝对位移方便计算
    //需要将水波纹的起始点左移一个波浪的单位
    //path需要闭合
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-waveLength + dx, waveHeight+dy);

        for (int i = 0; i < getWidth() / waveLength + 2; i++) {
            mPath.rQuadTo(waveLength / 4, -waveTop, waveLength / 2, 0);
            mPath.rQuadTo(waveLength / 4, waveTop, waveLength / 2, 0);

        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    //该方法在activity中调用
    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, waveLength);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

}
