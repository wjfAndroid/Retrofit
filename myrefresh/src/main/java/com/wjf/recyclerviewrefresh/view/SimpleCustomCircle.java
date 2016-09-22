package com.wjf.recyclerviewrefresh.view;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;


/**
 * Created by Administrator on 2016/9/20.
 */
public class SimpleCustomCircle extends View {
    public SimpleCustomCircle(Context context) {
        super(context);
        init();
    }

    public SimpleCustomCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleCustomCircle);

        colorGet = typedArray.getInt(R.styleable.SimpleCustomCircle_getcolor, 0xff0000ff);

        typedArray.recycle();

        init();
    }


    public SimpleCustomCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaintAll;
    Paint mPaintGet;
    int all = 360;
    int get = 150;
    int colorAll = 0xffff0000;
    int colorGet = 0xff0000ff;
    int radious = 150;
    int width = 20;
    float angle;


    private void init() {
        mPaintAll = new Paint();
        mPaintAll.setColor(colorAll);
        mPaintAll.setStyle(Paint.Style.STROKE);
        mPaintAll.setAntiAlias(true);
        mPaintAll.setStrokeWidth(5);

        mPaintGet = new Paint();
        mPaintGet.setColor(colorGet);
        mPaintGet.setAntiAlias(true);
        mPaintGet.setStyle(Paint.Style.STROKE);
        mPaintGet.setStrokeWidth(5);

        angle = (get * 1.0f / all) * 360;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int size = (radious + width) * 2;

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : size, (heightMode == MeasureSpec.EXACTLY) ? heightSize : size);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            startAnim();
            isFirst = false;
            return;
        }
        canvas.rotate(-(angle + 90) / 2, getWidth() / 2, getHeight() / 2);
        RectF rect = new RectF(10, 10, getWidth() - 10, getHeight() - 10);
//        Path pathGet = new Path();
//        pathGet.addArc(rect, 0, 360);
//
//        Path pathAll = new Path();
//        pathAll.addArc(rect, angle, 360);

        if (isDrawAll) {
            canvas.drawArc(rect, angle, angleChange, false, mPaintAll);
            return;
        }
        if (isDrawGet) {
            canvas.drawArc(rect, angle, 360 - angle, false, mPaintAll);
            canvas.drawArc(rect, 0, angleChange, false, mPaintGet);
            return;
        }
        if (!isDrawGet && !isDrawAll) {
            canvas.drawArc(rect, angle, 360 - angle, false, mPaintAll);
            canvas.drawArc(rect, 0, angle, false, mPaintGet);
        }
    }

    boolean isFirst = true;
    boolean isDrawAll = false;
    boolean isDrawGet = false;
    float angleChange;
    ValueAnimator anim2;
    ValueAnimator anim1;

    private void startAnim() {
        anim1 = ValueAnimator.ofFloat(0, 360 - angle);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isDrawAll = true;
                angleChange = (float) animation.getAnimatedValue();
                invalidate();
                if (angleChange == (360 - angle)) {
                    System.out.println("SimpleCustomCircle.onAnimationUpdate");
                    isDrawAll = false;
                    angleChange = 0;
                    //   anim2.start();
                }

            }
        });
        anim1.setDuration((long) ((360 - angle) * 5000 / 360));
        anim1.start();

        anim2 = ValueAnimator.ofFloat(0, angle);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isDrawGet = true;
                angleChange = (float) animation.getAnimatedValue();
                invalidate();
                if (angleChange == angle) {
                    isDrawGet = false;
                }
            }
        });
        anim2.setDuration((long) (angle * 5000 / 360));

        AnimatorSet set = new AnimatorSet();
        set.play(anim1).before(anim2);
        set.start();
    }


    public void stop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            anim1.pause();
            anim2.pause();
        } else {
            anim1.cancel();
            anim2.cancel();
        }
    }

    public void restart() {
        if (anim1 == null) {
            return;
        }
        System.out.println("anim1 = " + anim1.isStarted());
        if (anim1 != null && anim1.isStarted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                anim1.resume();
            } else {
                anim1.start();
            }
        }
        if (anim2 != null && anim2.isStarted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                anim2.resume();
            } else {
                anim2.start();
            }
        }

    }
}
