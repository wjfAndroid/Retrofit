package com.wjf.recyclerviewrefresh.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.bean.Point;

/**
 * Created by Administrator on 2016/9/20.
 */
public class SimpleAnimatorView extends View {
    public SimpleAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint = new Paint();
    Point mPoint;
    int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        System.out.println("color = " + color);
        mPaint.setColor(color);
        invalidate();
    }

    private void init() {
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoint == null) {
            mPoint = new Point(50, 50);
            drawPoint(canvas);
            startAnim();
        } else {
            drawPoint(canvas);
        }
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(mPoint.getX(), mPoint.getY(), 50, mPaint);
    }


    //下面是关于ValueAnimator和ObjectAnimator使用Evaluator的方式。
    private void startAnim() {
        Point point_start = new Point(50, 50);
        Point point_end = new Point(getWidth() - 50, getHeight() - 50);
        final ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), point_start, point_end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) anim.getAnimatedValue();
                invalidate();
            }
        });
        //使用自定义的Evaluator来实现颜色的变化
        //  ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#FF0000", "#0000FF");
        //使用官方已经写好的颜色变化的Evaluator来实现
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ArgbEvaluator(), 0xffff0000, 0xff0000ff);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim).with(anim2);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}
