package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.base.BaseApplication;

/**
 * Created by Administrator on 2016/10/13.
 */
public class XfermodeView extends View {
    public XfermodeView(Context context) {
        super(context);
        init();
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int width = 400;
    int height = 400;
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        srcBitmap = getSrcBitmap(width, height);
        dstBitmap = getDstBitmap(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        Log.d("XfermodeView", "layerID:" + layerID);
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawBitmap(srcBitmap, width / 2, height / 2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);


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
