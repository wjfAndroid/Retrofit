package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ExtractAlphaView extends View {
    public ExtractAlphaView(Context context) {
        super(context);
    }

    public ExtractAlphaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ExtractAlphaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Bitmap mBitmap;
    Bitmap mBitmapCopy;
    Paint mPaint = new Paint();

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blog12);
        mBitmapCopy = mBitmap.extractAlpha();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = 500;
        float height = mBitmapCopy.getHeight()*width/mBitmapCopy.getWidth();
        mPaint.setColor(Color.RED);
        mPaint.setMaskFilter(new BlurMaskFilter(14, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(mBitmapCopy, null, new RectF(10, 10,width,height), null);

     //   canvas.drawBitmap(mBitmap, null, new RectF(0, 0,width,height), null);
    }
}
