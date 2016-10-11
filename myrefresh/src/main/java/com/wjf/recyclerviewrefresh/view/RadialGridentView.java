package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/26.
 */
public class RadialGridentView extends View {
    public RadialGridentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint;
    RadialGradient mRadialGradient;
    int mRadious = 100;

    private void init() {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadialGradient = new RadialGradient(w / 2, h / 2, mRadious, 0xff00ff00, 0xff0000ff, Shader.TileMode.REPEAT);
        mPaint.setShader(mRadialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadious, mPaint);
    }
}
