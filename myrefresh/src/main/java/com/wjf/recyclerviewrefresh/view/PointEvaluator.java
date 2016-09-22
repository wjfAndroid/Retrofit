package com.wjf.recyclerviewrefresh.view;

import android.animation.TypeEvaluator;

import com.wjf.recyclerviewrefresh.bean.Point;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PointEvaluator implements TypeEvaluator<Point>{
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
        Point point = new Point(x,y);
        return point;
    }

//    @Override
//    public Object evaluate(float fraction, Object startValue, Object endValue) {
//        Point ps = (Point) startValue;
//        Point pe = (Point) endValue;
//
//        float x = ps.getX() + fraction * (pe.getX() - ps.getX());
//        float y = ps.getY() + fraction * (pe.getY() - ps.getY());
//        Point point = new Point(x,y);
//        return point;
//    }
}
