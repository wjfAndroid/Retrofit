package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class CircleView extends View {
//    public CircleView(Context context) {
//        super(context);
//        init();
//    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    Paint mPaint;
    Bitmap mBitmap;
    BitmapShader mBitmapShader;
    int mFormat = 0;
    float mRadious = 5f;

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        int bitmapID = typedArray.getResourceId(R.styleable.CircleView_src, 0);
        if (bitmapID == 0) {
            throw new RuntimeException("需要设置src");
        }
        mFormat = typedArray.getInt(R.styleable.CircleView_format, 0);

        if (mFormat == 1) {
            mRadious = typedArray.getDimension(R.styleable.CircleView_radious, 5);
        }
        typedArray.recycle();


        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), bitmapID);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int mWidth =mBitmap.getWidth();
        int mHeight =mBitmap.getHeight();

        setMeasuredDimension((widthMode==MeasureSpec.EXACTLY)?widthSize:mWidth,(heightMode==MeasureSpec.EXACTLY)?heightSize:mHeight);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        float scale =(float) getWidth() / mBitmap.getWidth();
        System.out.println("mBitmap.getWidth = " + mBitmap.getWidth());
        System.out.println("scale = " + scale);
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(mBitmapShader);

        float half = getWidth() / 2;
        System.out.println("mFormat = [" + mFormat + "]  "+"mRadious = [" + mRadious + "]");
        if (mFormat==0){
            canvas.drawCircle(half, half, getWidth() / 2, mPaint);
        }else {
            canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),mRadious,mRadious,mPaint);
        }
    }
}
