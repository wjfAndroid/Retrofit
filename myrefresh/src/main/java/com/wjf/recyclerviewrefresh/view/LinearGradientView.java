package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/13.
 */
public class LinearGradientView extends View {
    Paint mPaint;

    public LinearGradientView(Context context) {
        super(context);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //多色渐变
        int[] colors = {0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00, 0xff00ffff};
                float[] pos = {0.1f, 0.2f, 0.4f, 0.6f, 1.0f};
   //     int[] colors = {0xffff0000, 0xff00ff00, 0xff0000ff, 0xff00ffff, 0xff0fffff};
    //    float[] position = new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
        LinearGradient gradient = new LinearGradient(0, 0, getWidth() / 2, getHeight() / 2, colors, pos, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mPaint.setTextSize(50);
        canvas.drawText("欢迎关注启舰的blog world!",0,200,mPaint);

//

//        LinearGradient multiGradient = new LinearGradient(0, 0, getWidth() / 2, getHeight() / 2, colors, pos, Shader.TileMode.MIRROR);
//        mPaint.setShader(multiGradient);
//        mPaint.setTextSize(50);
//        canvas.drawText("欢迎关注启舰的blog", 0, 200, mPaint);
    }

    public void draw1(Canvas canvas) {
        LinearGradient gradient = new LinearGradient(0, 0, getWidth(), 0, 0xffff0000, 0xff00ff00, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        canvas.drawRect(100, 100, 200, 200, mPaint);
    }

}
