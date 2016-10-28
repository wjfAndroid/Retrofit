package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.base.BaseApplication;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Canvas2View extends View {
    public Canvas2View(Context context) {
        super(context);
        init();
    }

    public Canvas2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Canvas2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int width = 400;
    int height = 400;
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;
    Paint mPaintTxtRed;
    Paint mPaintTxtGreen;
    public void init() {
        Log.d(BaseApplication.TAG, "init() ");
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();

        mPaintTxtRed = new Paint();
        mPaintTxtRed.setColor(Color.RED);
        mPaintTxtRed.setTextSize(50);
        mPaintTxtRed.setStyle(Paint.Style.STROKE);
        mPaintTxtRed.setStrokeWidth(3);

        mPaintTxtGreen = new Paint();
        mPaintTxtGreen.setColor(Color.GREEN);
        mPaintTxtGreen.setTextSize(50);
        mPaintTxtGreen.setStyle(Paint.Style.STROKE);
        mPaintTxtGreen.setStrokeWidth(3);

        srcBitmap = getSrcBitmap(width, height);
        dstBitmap = getDstBitmap(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        Log.e(BaseApplication.TAG, "layerID:" + layerID);
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(srcBitmap, width / 2, height / 2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);

        canvas.clipRect(10, 10, 110, 110);        //第一个
        canvas.drawColor(Color.GRAY);


//        canvas.drawText("hello", width / 2, height / 2, mPaintTxtRed);
//        canvas.translate( 30,0 );
//        canvas.drawText("hello", width / 2, height / 2, mPaintTxtGreen);


    }

    private Bitmap getDstBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.cfc4));
        canvas.drawOval(new RectF(0, 0, width, height), paint);
        return bitmap;
    }

    private Bitmap getSrcBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.c6af));
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }

}
