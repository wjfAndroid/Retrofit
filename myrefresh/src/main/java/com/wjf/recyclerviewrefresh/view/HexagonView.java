package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.bean.Point;

/**
 * Created by Administrator on 2016/9/27.
 */
public class HexagonView extends View {
    public HexagonView(Context context) {
        super(context);
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private String[] str = {"击杀", "生存", "助攻", "物理", "魔法", "防御", "金钱"};
    Paint onePint;
    Paint ciclePaint;
    Paint pointPaint;
    int radius;
    Paint two_paint;
    Paint three_paint;
    Paint four_paint;
    Paint str_paint;
    Paint mPaint;
    Rect str_rect;
    float[] fs = {0, 1, 1, 1, 1, 1, 1, 1};
    Point PointDevider;
    int conrnerCount = 7;


    private void init() {
        ciclePaint = new Paint();
        ciclePaint.setColor(Color.BLACK);
        ciclePaint.setAntiAlias(true);
        ciclePaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.STROKE);

        onePint = new Paint();
        onePint.setColor(getResources().getColor(R.color.one));
        onePint.setAntiAlias(true);
        onePint.setStyle(Paint.Style.FILL);


        //初始化第二层多边形画笔
        two_paint = new Paint();
        two_paint.setAntiAlias(true);
        two_paint.setColor(getResources().getColor(R.color.two));
        two_paint.setStyle(Paint.Style.FILL);//设置实心

        //初始化第三层多边形画笔
        three_paint = new Paint();
        three_paint.setAntiAlias(true);
        three_paint.setColor(getResources().getColor(R.color.three));
        three_paint.setStyle(Paint.Style.FILL);//设置实心

        //初始化第四层多边形画笔
        four_paint = new Paint();
        four_paint.setAntiAlias(true);
        four_paint.setColor(getResources().getColor(R.color.four));
        four_paint.setStyle(Paint.Style.FILL);//设置实心

        //初始化各等级进度画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        //初始化字体画笔
        str_paint = new Paint();
        str_paint.setAntiAlias(true);
        str_paint.setColor(Color.BLACK);
        str_paint.setTextSize(dp_px(14));
        str_rect = new Rect();
        str_paint.getTextBounds(str[0], 0, str[0].length(), str_rect);

        PointDevider = new Point(str_rect.height() / 3, str_rect.height() / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOne(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getWidth();
        radius = w / 2 - w / 8;
    }

    private void drawOne(Canvas canvas) {
        //  canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, ciclePaint);
        canvas.translate(getWidth() / 2, getHeight() / 2);

        getTxt(canvas, 7, radius);

        canvas.save();
        Path path = getPath(7, radius);
        canvas.drawPath(path, onePint);

        path = getPath(7, radius * 3 / 4);
        canvas.drawPath(path, two_paint);

        path = getPath(7, radius * 2 / 4);
        canvas.drawPath(path, three_paint);

        path = getPath(7, radius * 1 / 4);
        canvas.drawPath(path, four_paint);

        path = getProgeress(7, radius * 1 / 4);
        canvas.drawPath(path, mPaint);


    }

    public Path getPath(int connerCount, int r) {
        if (connerCount <= 2) {
            return null;
        }
        Path path = new Path();
        path.moveTo(0, -r);
        for (int i = 1; i < connerCount; i++) {
            path.lineTo(r * ((float) Math.sin(Math.toRadians(360 * i / connerCount))), -r * ((float) Math.cos(Math.toRadians(360 * i / connerCount))));
        }
        path.close();
        return path;
    }

    public Path getProgeress(int connerCount, int defaultRadius) {
        if (connerCount <= 2) {
            return null;
        }
        Path path = new Path();
        path.moveTo(0, -fs[1] * defaultRadius);
        for (int i = 1; i < connerCount; i++) {
            for (int j = 1; j <= fs.length; j++) {
                if (i == j) {
                    path.lineTo(fs[j + 1] * defaultRadius * ((float) Math.sin(Math.toRadians(360 * i / connerCount))), -fs[j + 1] * defaultRadius * ((float) Math.cos(Math.toRadians(360 * i / connerCount))));
                }
            }
        }
        path.close();
        return path;
    }


    public void getTxt(Canvas canvas, int cornerCount, int radius) {
        // canvas.drawText(str[0], -str_rect.width() / 2, -radius, str_paint);

        for (int i = 0; i < cornerCount; i++) {
            Point point = new Point(radius * ((float) Math.sin(Math.toRadians(360 * i / cornerCount))), -radius * ((float) Math.cos(Math.toRadians(360 * i / cornerCount))));
            float c = 360 * i / cornerCount;
            if (c == 0) {
                canvas.drawText(str[i], point.getX() - str_rect.width() / 2, point.getY() - PointDevider.getY(), str_paint);
            } else if (c == 90) {
                canvas.drawText(str[i], point.getX() + PointDevider.getX(), point.getY() + str_rect.height() / 2, str_paint);
            } else if (c == 180) {
                canvas.drawText(str[i], point.getX() - str_rect.width() / 2, point.getY() + str_rect.height() + PointDevider.getY(), str_paint);
            } else if (c == 270) {
                canvas.drawText(str[i], point.getX() - PointDevider.getX(), point.getY() + str_rect.height() / 2, str_paint);
            } else if (c < 90) {
                Point pointBottom = new Point(point.getX() + PointDevider.getX(), point.getY() - PointDevider.getY());
                canvas.drawText(str[i], pointBottom.getX(), pointBottom.getY(), str_paint);
            } else if (c < 180) {
                Point pointBottom = new Point(point.getX() + PointDevider.getX(), point.getY() + str_rect.height() + PointDevider.getY());
                canvas.drawText(str[i], pointBottom.getX(), pointBottom.getY(), str_paint);
            } else if (c < 270) {
                Point pointBottom = new Point(point.getX() - str_rect.width() - PointDevider.getX(), point.getY() + str_rect.height() + PointDevider.getY());
                canvas.drawText(str[i], pointBottom.getX(), pointBottom.getY(), str_paint);
            } else if (c < 360) {
                Point pointBottom = new Point(point.getX() - str_rect.width() - PointDevider.getX(), point.getY() - PointDevider.getY());
                canvas.drawText(str[i], pointBottom.getX(), pointBottom.getY(), str_paint);
            }
        }
    }


    public void changeF(int position, float values) {
        fs[position] = values;
        invalidate();
    }


    public int dp_px(int values) {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }
}
