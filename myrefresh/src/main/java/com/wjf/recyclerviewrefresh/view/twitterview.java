package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/13.
 */
public class twitterview extends View {

    public twitterview(Context context) {
        super(context);     init();
    }

    public twitterview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public twitterview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);     init();
    }

    int width = 400;
    int height = 400;
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        srcBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.twiter_light);
        dstBitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.twiter_bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        Log.d("XfermodeView", "layerID:" + layerID);
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(srcBitmap, 0,0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }
}
