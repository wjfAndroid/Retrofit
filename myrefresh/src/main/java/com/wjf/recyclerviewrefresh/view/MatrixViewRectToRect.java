package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MatrixViewRectToRect extends View {
    Bitmap mBitmap;
    Matrix mMatrix;
    public MatrixViewRectToRect(Context context) {
        super(context);
        init();
    }

    public MatrixViewRectToRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixViewRectToRect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    RectF src;
    RectF dst;

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sence);
        mMatrix = new Matrix();
        src = new RectF(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        System.out.println("mBitmap getWidth= " + mBitmap.getWidth()+"  mBitmap getHeight= " + mBitmap.getHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dst = new RectF(0,0,w,h);
        System.out.println("w" + w+"  h= " +h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix.setRectToRect(src,dst, Matrix.ScaleToFit.CENTER);
        canvas.drawBitmap(mBitmap,mMatrix,new Paint());
    }
}
