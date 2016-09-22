package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SimpleShadowView extends View {
    public SimpleShadowView(Context context) {
        super(context);
        init();
    }


    public SimpleShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;
    int mRadious = 1, mDx = 10, mDy = 10;
    Bitmap mBitmap;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(36);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
    }

    public void changeRadious() {
        mRadious++;
        postInvalidate();
    }

    public void changeDy() {
        mDx += 2;
        postInvalidate();
    }

    public void changeDx() {
        mRadious += 3;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShadowLayer(mRadious,mDx,mDy,Color.GRAY);

        canvas.drawText("hello  吴建峰", 100, 100, mPaint);

        canvas.drawCircle(100,300,50,mPaint);

        canvas.drawBitmap(mBitmap,null,new Rect(100,500,100+mBitmap.getWidth(),500+mBitmap.getHeight()),mPaint);

    }
}
