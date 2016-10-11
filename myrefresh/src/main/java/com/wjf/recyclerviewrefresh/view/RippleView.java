package com.wjf.recyclerviewrefresh.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/26.
 * 通过RadialGradient来实现水波纹效果，
 * 1.记录下按下的位置画出初始的圆，在松开时开始动画，画渐变的圆。
 * 2.颜色变化：此处为从透明到天蓝色，可以设置为透明到style中的颜色
 * mRadialGradient = new RadialGradient(mDx, mDy, currentRadious, 0x00000000, 0xff58faac, Shader.TileMode.REPEAT);
 * 3.动态修改mRadialGradient里面设置的半径，不然效果不佳。这里使用的是objectanimator的设置radious属性的变化，然后在该view里面设置setRadious方法获取
 *  mAnimator = ObjectAnimator.ofInt(this, "radious", DEFAULT_RADIOUS, getMeasuredWidth());
 * 4.
 *
 */
public class RippleView extends View {
    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    int DEFAULT_RADIOUS = 50;
    int mDx, mDy;
    Paint mPaint;
    RadialGradient mRadialGradient;
    ObjectAnimator mAnimator;
    int currentRadious = 0;
    int color;

    private void init(Context context) {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        color = getResources().getColor(R.color.colorPrimary);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDx != event.getX() || mDy != event.getY()) {
            mDx = (int) event.getX();
            mDy = (int) event.getY();
            setRadious(DEFAULT_RADIOUS);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator = null;
                }
                if (mAnimator == null) {
                    mAnimator = ObjectAnimator.ofInt(this, "radious", DEFAULT_RADIOUS, getMeasuredWidth());
                    mAnimator.setInterpolator(new AccelerateInterpolator());

                    mAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            setRadious(0);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
                mAnimator.setDuration(2000);
                mAnimator.start();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setRadious(int radious) {
        System.out.println("radious = " + radious);
        currentRadious = radious;
        if (currentRadious > 0) {
            mRadialGradient = new RadialGradient(mDx, mDy, currentRadious, 0x00000000, color, Shader.TileMode.REPEAT);
            mPaint.setShader(mRadialGradient);
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mDx, mDy, currentRadious, mPaint);
    }
}
