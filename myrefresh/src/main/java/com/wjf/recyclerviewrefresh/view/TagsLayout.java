package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wjf.recyclerviewrefresh.R;


public class TagsLayout extends ViewGroup {
    int horizontalSpace = 10;
    int verticalSpace = 10;

    public TagsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagsLayout);
        if (typedArray != null) {
            horizontalSpace = typedArray.getDimensionPixelSize(R.styleable.TagsLayout_tagHorizontalSpace, 10);
            verticalSpace = typedArray.getDimensionPixelSize(R.styleable.TagsLayout_tagVerticalSpace, 10);
//            float v = typedArray.getDimension(R.styleable.TagsLayout_tagVerticalSpace, 9);
//            float h = typedArray.getDimension(R.styleable.TagsLayout_tagHorizontalSpace, 9);
//            float i = typedArray.getDimensionPixelOffset(R.styleable.TagsLayout_tagHorizontalSpace, 9);
//            float i2= typedArray.getDimensionPixelOffset(R.styleable.TagsLayout_tagVerticalSpace, 9);
//            System.out.println("horizontalSpace = [" + horizontalSpace + "], verticalSpace = [" + verticalSpace + "]");
//            System.out.println("h = [" + h + "], v = [" + v + "]");
//            System.out.println("h2 = [" + i + "], v2 = [" + i2 + "]");
            typedArray.recycle();
        }
    }

    public TagsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //测量子条目的宽度和高度， protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    // 1. child.getMeasuredWidth() 和 child.getMeasuredHeight() 2.考虑margin和padding    保存好子view的四个位置（l,t,r,b）
    //2.记录好viewgroup的宽度和高度，由子view的最高高度或者高度的累加，宽度也是最宽宽度或者宽度的累加。或者是最高高度之间的累加。
    //3.考虑viewgroup的宽高分别是warpcontent还是matchparent，还是具体数值。arpcontent使用2计算出来的数据。

    //放置view：   protected void onLayout(boolean changed, int l, int t, int r, int b)
    //遍历子view上使用记录的数据使用： public void layout(int l, int t, int r, int b)

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int count = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        System.out.println("left = " + left + "top = " + top);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + horizontalSpace;
            int childHeight = child.getMeasuredHeight() + lp.bottomMargin + lp.topMargin + verticalSpace;
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth);//最大宽度
                lineWidth = childWidth;//当前子条目宽度
                height = height + lineHeight;//到前一行的高度
                lineHeight = childHeight;//当前行高度
                child.setTag(new Location(left, height + top, left + childWidth - horizontalSpace, height + top + child.getMeasuredHeight()));
            } else {
                child.setTag(new Location(lineWidth + left, height + top, lineWidth + left + childWidth - horizontalSpace, height + top + child.getMeasuredHeight()));
                lineWidth = lineWidth + childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }
        width = Math.max(lineWidth, width) + getPaddingLeft() + getPaddingRight();
        height = height + lineHeight;
        sizeHeight = sizeHeight + getPaddingBottom() + getPaddingTop();
        height = height + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View c = getChildAt(i);
            Location location = (Location) c.getTag();
            c.layout(location.left, location.top, location.right, location.bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();

    }

    class Location {
        int left;
        int top;
        int right;
        int bottom;

        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }
}
