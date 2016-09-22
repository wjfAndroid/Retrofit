package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Clock extends View {
    //表盘的背景颜色
    @ColorInt
    private static final int DIAL_BG = 0xFFF0F0F0;
    //表外圆环的颜色
    @ColorInt
    private static final int RING_BG = 0xFFF8F8F8;
    //字体颜色
    @ColorInt
    private static final int TEXT_COLOR = 0xFF141414;
    //时针和分针的颜色
    @ColorInt
    private static final int HOUR_MINUTE_COLOR = 0xFF5B5B5B;
    //秒钟的颜色
    @ColorInt
    private static final int SECOND_COLOR = 0xFFB55050;
    int miniSize = 200;
    int mSize;
    Paint mPaintCircle;
    Paint mPaintBG;

    public Clock(Context context) {
        super(context);
        initPaint();
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    Paint mPaint = new Paint();
    Paint mPaintTxt = new Paint();
    Paint mPaintHour = new Paint();
    Paint mPaintSecond = new Paint();

    Paint mPaintBlack = new Paint();
    Paint mPaintRed = new Paint();


    Calendar mCalendar;

    boolean isFirst = true;

    private void initPaint() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mCalendar = Calendar.getInstance();
        mPaintBG = new Paint();
        mPaintBG.setColor(DIAL_BG);
        mPaintBG.setAntiAlias(true);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(RING_BG);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(dp2px(10));
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setShadowLayer(4, 4, 4, 0x80000000);

        mPaint.setColor(0xfff8f8f8);
        mPaint.setTextSize(30);

        mPaintTxt.setAntiAlias(true);
        mPaintTxt.setColor(TEXT_COLOR);

        mPaintHour.setColor(HOUR_MINUTE_COLOR);
        mPaintHour.setAntiAlias(true);
        mPaintHour.setShadowLayer(4, 0, 0, 0x80000000);
        mPaintHour.setStrokeWidth(dp2px(10));
        mPaintHour.setStrokeCap(Paint.Cap.ROUND);

        mPaintBlack.setColor(HOUR_MINUTE_COLOR);
        mPaintBlack.setAntiAlias(true);
        mPaintBlack.setShadowLayer(4, 0, 0, 0x80000000);

        mPaintSecond.setColor(SECOND_COLOR);
        mPaintSecond.setAntiAlias(true);
        mPaintSecond.setShadowLayer(4, 3, 0, 0x80000000);
        mPaintSecond.setStrokeWidth(6);
        mPaintSecond.setStrokeCap(Paint.Cap.ROUND);

        mPaintRed.setColor(SECOND_COLOR);
        mPaintRed.setAntiAlias(true);
        mPaintRed.setShadowLayer(5, 0, 0, 0x80000000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mSize = dp2px(miniSize);
        System.out.println("mSize = " + mSize);
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getTime();

        canvas.translate(mSize / 2, mSize / 2);

        canvas.drawCircle(0, 0, mSize / 2 - 8, mPaintBG);

        canvas.drawCircle(0, 0, mSize / 2 - 16, mPaintCircle);

        drawTime(canvas);

        drawHour(canvas);

        drawMinute(canvas);

        canvas.drawCircle(0, 0, mSize / 25, mPaintBlack);

        drawSecond(canvas);

        canvas.drawCircle(0, 0, mSize / 40, mPaintRed);
        if (isFirst) {
            postInvalidateDelayed(1000);
        }


    }


    float hour, minutes, second;

    private void getTime() {

        mCalendar = Calendar.getInstance();
        hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        minutes = mCalendar.get(Calendar.MINUTE);
        second = mCalendar.get(Calendar.SECOND);
        System.out.println("hour = " + hour + "  minutes = " + minutes + "  second = " + second);
    }


    private void drawTime(Canvas canvas) {
        int textSize = 15;
        mPaintTxt.setTextSize(sp2px(textSize));
        int radious = mSize / 2 - 10;
        float scaleWidth = mPaintTxt.measureText("12");
        canvas.save();
        canvas.drawText("12", -scaleWidth / 2, -radious + 30 + dp2px(textSize), mPaintTxt);
        canvas.rotate(90);
        canvas.drawText("3", -scaleWidth / 4, -radious + 30 + dp2px(textSize), mPaintTxt);
        //   canvas.rotate(-90);
        canvas.restore();
    }

    private void drawHour(Canvas canvas) {
        int width = mSize / 5;
        canvas.save();
        float hourRadious = (hour + minutes / 60) * 30;
        canvas.rotate(hourRadious);
        canvas.drawLine(0, 0, 0, -width, mPaintHour);
        canvas.restore();
    }

    private void drawMinute(Canvas canvas) {
        int width = mSize / 3;
        canvas.save();
        float minuteRadious = (minutes + second / 60) * 6;
        canvas.rotate(minuteRadious);
        canvas.drawLine(0, 0, 0, 0 - width, mPaintHour);
        canvas.restore();
    }

    private void drawSecond(Canvas canvas) {
        int width = mSize / 2;
        canvas.save();
        float secondRadious = second * 6;
        if (isFirst) {
            canvas.rotate(secondRadious + 1);
        } else {
            canvas.rotate(secondRadious);
        }

        canvas.drawLine(0, width / 5, 0, -width * 4 / 5, mPaintSecond);
        canvas.restore();
        isFirst = !isFirst;
        if (!isFirst) {
            invalidate();
        }
    }

    /**
     * 将 dp 转换为 px
     *
     * @param dp 需转换数
     * @return 返回转换结果
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
