package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class SimpleBlurMaskFilterView extends View {
    public SimpleBlurMaskFilterView(Context context) {
        super(context);
        init();
    }

    public SimpleBlurMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleBlurMaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;
    Bitmap mBitmap;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(36);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,100,50,mPaint);
        canvas.drawText("hello 吴建峰",100,300,mPaint);
        canvas.drawBitmap(mBitmap,null,new Rect(100,500,100+mBitmap.getWidth(),500+mBitmap.getHeight()),mPaint);
    }
}
