package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MatrixViewPolyToPoly extends View {
    Bitmap mBitmap;
    Matrix mMatrix;


    public MatrixViewPolyToPoly(Context context) {
        super(context);
        System.out.println("context = [" + context + "]");
        init();
    }

    public MatrixViewPolyToPoly(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("context = [" + context + "], attrs = [" + attrs + "]");
        init();
    }

    public MatrixViewPolyToPoly(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sence);
        mMatrix = new Matrix();

        float[] src = {0, 0,
                mBitmap.getWidth(), 0,
                mBitmap.getWidth(), mBitmap.getHeight(),
                0, mBitmap.getHeight()};
        float[] dst = {0, 0,
                mBitmap.getWidth(), 150,
                mBitmap.getWidth(), mBitmap.getHeight() - 140,
                0, mBitmap.getHeight()};

        //将一个形状转化成另一个形状
        mMatrix.setPolyToPoly(src, 0, dst, 0,4);

        mMatrix.postScale(0.26f,0.26f);
        mMatrix.postTranslate(100,100);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,mMatrix,null);
    }
}
