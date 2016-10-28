package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/14.
 */
public class BookShelfView extends View {
    public BookShelfView(Context context) {
        super(context);
    }

    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;

    public BookShelfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BookShelfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.book_light);
        dstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.book_bg);
        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);

    }
}
