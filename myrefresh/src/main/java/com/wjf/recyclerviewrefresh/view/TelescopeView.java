package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class TelescopeView extends View {
    public TelescopeView(Context context) {
        super(context);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int mDx = -1;
    int mDy = -1;
    Paint mPaint;
    Bitmap mBitmapBG, mBitmapSence;

    private void init() {
        mPaint = new Paint();
        mBitmapSence = BitmapFactory.decodeResource(getResources(), R.drawable.sence);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDx = -1;
                mDy = -1;
                break;
        }
        postInvalidate();
        boolean  b = super.onTouchEvent(event);
        System.out.println("b = " + b);
        return b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第一部分，就是新建一个空白的bitmap，这个bitmap的大小与控件一样，然后把我们的背景图进行拉伸，画到这个空白的bitmap上。
        //主要是为了将我们的图片放到mbitmapbg上面，尺寸和view的大小相同。相当于fit_xy操作.
        //mbitmapbg上面是一张mBitmapSence图片
        if (mBitmapBG == null) {
            mBitmapBG = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasBG = new Canvas(mBitmapBG);
            canvasBG.drawBitmap(mBitmapSence, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
        }
        //这里就是BitmapShader的使用了
        //s使用bitmapshader可以画出图片的指定的样式：
        // 参数1：图片；参数2：横向的多于空间的样式；参数1：图片；纵向的多于空间的样式
        //三种样式：镜像，重复，沿图片边缘颜色，
        //最后可以通过canvas画出各种样式，画到的位置不显示。
        if (mDx != -1 && mDy != -1) {
            mPaint.setShader(new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(mDx,mDy,150,mPaint);
        }

    }
}
