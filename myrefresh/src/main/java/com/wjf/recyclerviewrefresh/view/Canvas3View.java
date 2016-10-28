package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.base.BaseApplication;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Canvas3View extends View {
    public Canvas3View(Context context) {
        super(context);
        init();
    }

    public Canvas3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Canvas3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;
    Bitmap mBitmap;

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        drawSkew(canvas);
    }

    public void drawRect(Canvas canvas) {
        int layerID = canvas.saveLayer(0, 0, 100, 100, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, 400, 400, mPaint);
        canvas.restore();
    }

    public void drawSkew(Canvas canvas) {
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        Log.d("Canvas3View", "layerID:" + layerID);
        canvas.skew(1.7f, 0);
        canvas.drawRect(0, 0, 150, 150, mPaint);
        canvas.restoreToCount(layerID);

        canvas.rotate(30);
    }

    public void drawRotate(Canvas canvas) {
        canvas.rotate(30);
        canvas.drawRect(0, 0, 150, 150, mPaint);

    }


    public void drawAlpha(Canvas canvas) {
        canvas.drawRect(100, 100, 300, 300, mPaint);
        int LayerID = canvas.saveLayerAlpha(0, 0, 450, 450, 0x88, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 400, 400, mPaint);
        canvas.restore();
    }
}
