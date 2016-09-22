package com.wjf.recyclerviewrefresh.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wjf.recyclerviewrefresh.adapter.CustomWrapperAdapter;
import com.wjf.recyclerviewrefresh.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/12.
 * 带下拉刷新和加载更多的recyclerview
 */
public class CustomRecyclerView extends RecyclerView {

    boolean isOnTouch, isRefresh;
    float lastX, lastY;
    MyRefreshListener mMyRefreshListener;

    public void setMyRefreshListener(MyRefreshListener myRefreshListener) {
        mMyRefreshListener = myRefreshListener;
    }

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOnTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //当前可见的第一个item
                View lastVisibleView = this.getChildAt(0);
                //获取他的位置
                int lastVisiblePosition = this.getChildLayoutPosition(lastVisibleView);
                //判断是否到达顶部
                if (lastVisiblePosition == 0) {
                    int dx = (int) (lastX - x);
                    int dy = (int) (lastY - y);
                    if (Math.abs(dx) < Math.abs(dy)) {
                        changeHeight(dy);
                        isRefresh = true;
                    }
                }
//                if (!canScrollVertically(-1)) {
//                    int dx = (int) (lastX - x);
//                    int dy = (int) (lastY - y);
//                    System.out.println("dy = " + dy+"   dx = " + dx);
//                    if (Math.abs(dx) < Math.abs(dy)) {
//                        changeHeight(dy);
//                        isRefresh = true;
//                    }
//                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isOnTouch = false;
                isRefresh = false;
                if (mState == STATE_READY) {
                    onsStateChange(STATE_REFRESHING);
                }
                autoSize();
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(e);
    }

    private void changeHeight(int dy) {

        head_view.getLayoutParams().height -= dy;
        head_view.requestLayout();
        setStateByHeight(head_view.getHeight(), false);
    }

    private void setStateByHeight(int height, boolean isAuto) {
        if (mState == STATE_REFRESHING) {
            return;
        }
        if (height - headviewHeight < headviewHeight && !isAuto) {
            onsStateChange(STATE_NORMAL);
        }
        if (height - headviewHeight > headviewHeight) {
            onsStateChange(STATE_READY);
        }
        if (height - headviewHeight == headviewHeight && !isOnTouch && !isAuto) {
            onsStateChange(STATE_REFRESHING);
        }
    }

    private void onsStateChange(int state) {
        mState = state;
        switch (state) {
            case STATE_NORMAL:
                tv_state.setText("下拉刷新...");
                break;
            case STATE_READY:
                tv_state.setText("松开刷新...");
                break;
            case STATE_REFRESHING:
                tv_state.setText("正在刷新...");
                if (mMyRefreshListener != null) {
                    mMyRefreshListener.refresh();
                }
                break;
            case STATE_END:
                System.out.println("CustomRecyclerView.STATE_END");
                tv_state.setText("刷新结束");
                break;
        }
    }

    boolean isLoadMore;


    @Override
    public void setLayoutManager(final LayoutManager layout) {
        super.setLayoutManager(layout);


        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isRefresh) {
                    return;
                }
                if (mState != STATE_NORMAL) {
                    return;
                }
                //判断是否最后一item个显示出来
                LayoutManager layoutManager = getLayoutManager();

                //可见的item个数
                int visibleChildCount = layoutManager.getChildCount();
                if (visibleChildCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadMore) {
                    //当前可见的最后一个item                         //可见的item的数目
                    View lastVisibleView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    //获取他的位置
                    int lastVisiblePosition = recyclerView.getChildLayoutPosition(lastVisibleView);
                    //判断是否到达底部
                    if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                        foot_view.setVisibility(VISIBLE);
                        isLoadMore = true;
                        if (mMyRefreshListener != null) {
                            mMyRefreshListener.loadmore();
                        }
                    } else {
                        foot_view.setVisibility(GONE);
                    }
                }

            }
        });
    }


    public static final int STATE_NORMAL = 1;
    public static final int STATE_READY = 2;
    public static final int STATE_REFRESHING = 3;
    public static final int STATE_END = 4;
    int mState = STATE_NORMAL;
    TextView tv_state;
    View head_view, foot_view;
    int headviewHeight;
    CustomWrapperAdapter mCustomWrapperAdapter;

    @Override
    public void setAdapter(Adapter adapter) {
        ArrayList<View> headviews = new ArrayList<>();
        ArrayList<View> footviews = new ArrayList<>();

        View refresh_view = LayoutInflater.from(getContext()).inflate(R.layout.refresh_layout, null);
        head_view = refresh_view;

        RelativeLayout head_layout = new RelativeLayout(getContext());
        head_layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        head_layout.addView(head_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        tv_state = (TextView) refresh_view.findViewById(R.id.tv_refresh);

        head_view.post(new Runnable() {
            @Override
            public void run() {
                headviewHeight = head_view.getHeight();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) head_view.getLayoutParams();
                layoutParams.setMargins(0, -headviewHeight, 0, 0);
                head_view.requestLayout();
            }
        });
        headviews.add(head_layout);

        LinearLayout foot_layout = new LinearLayout(getContext());
        foot_layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        footviews.add(foot_layout);
        foot_layout.setPadding(0, 15, 0, 15);

        foot_layout.addView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall));
        TextView textView = new TextView(getContext());
        textView.setText("加载更多...");
        foot_layout.addView(textView);
        foot_layout.setGravity(Gravity.CENTER);
        foot_view = foot_layout;
        foot_view.setVisibility(GONE);

        mCustomWrapperAdapter = new CustomWrapperAdapter(headviews, footviews, adapter);
        super.setAdapter(mCustomWrapperAdapter);


    }

    public interface MyRefreshListener {
        void refresh();

        void loadmore();
    }

    public void setRefreshComplete() {
        onsStateChange(STATE_END);
        autoSize();
    }

    public void setLoadMoreComplete() {
        isLoadMore = false;
        foot_view.setVisibility(GONE);
    }

    private void autoSize() {
        int currentHeight = 0;
        currentHeight = head_view.getHeight();

        int targetHeight = headviewHeight;

        if (mState == STATE_READY || mState == STATE_REFRESHING) {
            targetHeight = headviewHeight * 2;
        }
        if (mState == STATE_REFRESHING) {
            if (currentHeight < headviewHeight * 2) {
                return;
            }
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int s = (int) animation.getAnimatedValue();
                setStateByHeight(s, true);

                head_view.getLayoutParams().height = s;
                head_view.requestLayout();
            }
        });
        valueAnimator.start();
    }
}
