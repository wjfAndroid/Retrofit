package com.wjf.recyclerviewrefresh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CustomExpandedableListView extends ExpandableListView implements AbsListView.OnScrollListener, ExpandableListView.OnGroupClickListener {
    public CustomExpandedableListView(Context context) {
        super(context);
        init();
    }

    public CustomExpandedableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomExpandedableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        setOnGroupClickListener(this);
    }
    /*
    1.原理是自己画一个headview取代groupview，在第一次测绘的时候覆盖在第一个groupview的上面，之后在第二个view上来时变化高度，，在完全退出时，变成第二个view的样子，
    2.headview点击效果使用的是ontouchevent方法实现，通过，up和down来实现。

     */

    int MAX_ALPHA = 255;
    View mHeadView;
    boolean headViewVisiable;
    int headViewWidth;
    int headViewHeight;
    HeadAdapter mHeadAdapter;

    public void setHeadView(View headView) {
        mHeadView = headView;
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeadView.setLayoutParams(lp);

        if (mHeadView != null) {
            //fadingEdge属性用来设置拉滚动条时 ，边框渐变的放向。none（边框颜色不变），horizontal（水平方向颜色变淡），vertical（垂直方向颜色变淡）。
            //  fadingEdgeLength用来设置边框渐变的长度。
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    private void headViewClick() {
        System.out.println("CustomExpandedableListView.headViewClick");
        long packPosition = getExpandableListPosition(getFirstVisiblePosition());
        int groupPosition = ExpandableListView.getPackedPositionGroup(packPosition);
            this.collapseGroup(groupPosition);//关闭group
          this.setSelectedGroup(groupPosition);//使当前group在最上面

    }

    float mDownX;
    float mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (headViewVisiable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //      System.out.println("CustomExpandedableListView.ACTION_DOWN");
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                    if (mDownX < mHeadView.getX() && mDownY < mHeadView.getY()) {
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //      System.out.println("CustomExpandedableListView.ACTION_UP");
                    float x = ev.getX();
                    float y = ev.getY();
                    //    System.out.println("mDownX = " + mDownX+"  mDownY = " + mDownY+"  x = " + x+"  y = " + y);
                    if (x <= headViewWidth && y <= headViewHeight) {
                        if (mHeadView != null) {
                            headViewClick();
                        }
                        return true;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        mHeadAdapter = (HeadAdapter) adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeadView != null) {
            measureChild(mHeadView, widthMeasureSpec, heightMeasureSpec);
            headViewWidth = mHeadView.getMeasuredWidth();
            headViewHeight = mHeadView.getMeasuredHeight();
        }
    }

    int mOldState = -1;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        long packPosition = getExpandableListPosition(getFirstVisiblePosition());
        int groupPosition = ExpandableListView.getPackedPositionGroup(packPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(packPosition);
        int state = mHeadAdapter.getHeadState(groupPosition, childPosition);
        if (mHeadView != null && mHeadAdapter != null && mOldState != state) {
            mOldState = state;
            mHeadView.layout(0, 0, headViewWidth, headViewHeight);
        }
        configHeadView(groupPosition, childPosition);
    }

    private void configHeadView(int groupPosition, int childPosition) {
        if (mHeadView == null || mHeadAdapter == null || ((ExpandableListAdapter) mHeadAdapter).getGroupCount() == 0) {
            return;
        }
        int state = mHeadAdapter.getHeadState(groupPosition, childPosition);
        System.out.println("state = " + state);
        switch (state) {
            case HeadAdapter.HEAD_STATE_GONE:
                headViewVisiable = false;
                break;
            case HeadAdapter.HEAD_STATE_VISIABLE:

                mHeadAdapter.configHeadView(mHeadView, groupPosition, childPosition, MAX_ALPHA);
                if (mHeadView.getTop() != 0) {
                    mHeadView.layout(0, 0, headViewWidth, headViewHeight);
                }
                headViewVisiable = true;
                break;
            case HeadAdapter.HEAD_STATE_PUSHED_UP:
                View firstView = getChildAt(0);
                int bottom = firstView.getBottom();
                int height = mHeadView.getHeight();

                int y;
                int alpha;
                if (bottom < height) {
                    y = (bottom - height);
                    alpha = (height - bottom) * MAX_ALPHA / height;
                } else {
                    y = 0;
                    alpha = MAX_ALPHA;
                }
                mHeadAdapter.configHeadView(mHeadView, groupPosition, childPosition, alpha);
                if (mHeadView.getTop() != y) {
                    mHeadView.layout(0, y, headViewWidth, headViewHeight + y);
                }
                headViewVisiable = true;

                break;
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        System.out.println("dispatchDraw    headViewVisiable = " + headViewVisiable);
//        System.out.println("mHeadView.getLeft() = " + mHeadView.getLeft() + "mHeadView.getTop() = " + mHeadView.getTop());
//        System.out.println("mHeadView.getRight() = " + mHeadView.getRight() + "mHeadView.getBottom() = " + mHeadView.getBottom());

        if (headViewVisiable) {
            drawChild(canvas, mHeadView, getDrawingTime());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        long packPos = getExpandableListPosition(firstVisibleItem);
        int groupPos = ExpandableListView.getPackedPositionGroup(packPos);
        int childPos = ExpandableListView.getPackedPositionChild(packPos);
//        System.out.println("view = [], firstVisibleItem = [" + firstVisibleItem + "], visibleItemCount = [" + visibleItemCount + "], totalItemCount = [" + totalItemCount + "]");
//        System.out.println("groupPosition= " + groupPos + "  childPosition = " + childPos);
        configHeadView(groupPos, childPos);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        System.out.println("onGroupClick parent = [" + parent + "], v = [" + v + "], groupPosition = [" + groupPosition + "], id = [" + id + "]");
        if (isGroupExpanded(groupPosition)) {
            parent.collapseGroup(groupPosition);
        } else {
            parent.expandGroup(groupPosition);
        }
        return true;
    }


    public interface HeadAdapter {
        public static final int HEAD_STATE_GONE = 0;
        public static final int HEAD_STATE_VISIABLE = 1;
        public static final int HEAD_STATE_PUSHED_UP = 2;

        int getHeadState(int groupPosition, int childPosition);

        void configHeadView(View headView, int groupPosition, int childPosition, int alpha);
    }

}
