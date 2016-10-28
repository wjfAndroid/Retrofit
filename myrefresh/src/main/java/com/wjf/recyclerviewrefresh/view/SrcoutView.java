package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SrcoutView extends View {
    public SrcoutView(Context context) {
        super(context);
        init();
    }

    public SrcoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SrcoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int width = 400;
    int height = 400;
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;
    Path mPath;
    Bitmap mBitmapTxt;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(50);
        mPaint.setStyle(Paint.Style.STROKE);

//        srcBitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.guaguaka_pic);
//        dstBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        dstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.guaguaka_pic);
        srcBitmap = Bitmap.createBitmap(dstBitmap.getWidth(), dstBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        mBitmapTxt = BitmapFactory.decodeResource(getResources(), R.drawable.guaguaka_text);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapTxt, 0, 0, mPaint);

//        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
//
//        Canvas c = new Canvas(dstBitmap);
//        c.drawPath(mPath, mPaint);
//
//        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
//        mPaint.setXfermode(null);
//
//        canvas.restoreToCount(layerID);

        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        Canvas c = new Canvas(srcBitmap);
        c.drawPath(mPath, mPaint);


        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    float mPreX, mPreY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mPath.moveTo(mPreX, mPreY);
                return true;
            case MotionEvent.ACTION_MOVE:
                float mEndX = (event.getX() + mPreX) / 2;
                float mEndY = (event.getY() + mPreY) / 2;
                mPath.quadTo(mPreX, mPreY, mEndX, mEndY);
                mPreX = mEndX;
                mPreY = mEndY;
                break;

        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
