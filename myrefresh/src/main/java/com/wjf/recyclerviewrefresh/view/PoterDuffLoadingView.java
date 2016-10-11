package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/29.
 */
public class PoterDuffLoadingView extends View {
    public PoterDuffLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    PorterDuffXfermode mXfermode;
    Paint mPaint;
    Bitmap mBitmap;
    String ProgressTxt;
    int progressColor;
    int StopColor;


    private void init() {
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shape_rect);

    }
}
