package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/13.
 */
public class InvertView extends View {
    public InvertView(Context context) {
        super(context);
    }

    public InvertView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public InvertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int width = 400;
    int height = 400;
    Bitmap srcBitmap;
    Bitmap dstBitmap;
    Paint mPaint;
    Bitmap mBitmap;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        dstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_invert_shade);
        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);
        mBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);

        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.translate(0, srcBitmap.getHeight());
        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerID);


    }
}
