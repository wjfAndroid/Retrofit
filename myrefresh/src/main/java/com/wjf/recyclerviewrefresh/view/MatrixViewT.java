package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;


public class MatrixViewT extends View {
    Bitmap mBitmap;
    Matrix mMatrix;
    public MatrixViewT(Context context) {
        super(context);
    }

    public MatrixViewT(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixViewT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sence);
        mMatrix = new Matrix();
    }
}
