package com.wjf.recyclerviewrefresh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CustomWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<View> headViews = new ArrayList<>();
    ArrayList<View> footViews = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    private int HEAD_TAG = 0x1;
    private int FOOT_TAG = 0x2;

    public CustomWrapperAdapter(ArrayList<View> headViews, ArrayList<View> footViews, RecyclerView.Adapter adapter) {
        this.headViews = headViews;
        this.footViews = footViews;
        mAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (RecyclerView.INVALID_TYPE == viewType) {
            return new RecyclerView.ViewHolder(headViews.get(0)) {
            };
        } else if ((RecyclerView.INVALID_TYPE-1) == viewType) {
            return new RecyclerView.ViewHolder(footViews.get(0)) {
            };
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < headViews.size()) {
            return;
        }
        if (mAdapter != null) {
            int p = position - headViews.size();
            if (p < mAdapter.getItemCount()) {
                mAdapter.onBindViewHolder(holder, p);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < headViews.size()) {
            return RecyclerView.INVALID_TYPE;
        }
        if (mAdapter != null) {
            int p = position - headViews.size();
            if (p < mAdapter.getItemCount()) {
                return mAdapter.getItemViewType(p);
            }
        }
        return RecyclerView.INVALID_TYPE-1;
    }

    @Override
    public int getItemCount() {
        int count = headViews.size() + footViews.size();
        if (mAdapter != null) {
            count = count + mAdapter.getItemCount();
        }
        return count;
    }
}
