package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjf.recyclerviewrefresh.view.CustomRecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    CustomRecyclerView recy;
    Handler mHandler = new Handler();
    ArrayList<String> strs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recy = (CustomRecyclerView) findViewById(R.id.recy);

        recy.setLayoutManager(new LinearLayoutManager(this));
        for (int i=0;i<30;i++){
            strs.add("textview "+i);
        }
        MyAdapter adapter = new MyAdapter(strs);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });

        recy.setAdapter(adapter);
        recy.setMyRefreshListener(new CustomRecyclerView.MyRefreshListener() {
            @Override
            public void refresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      recy.setRefreshComplete();
                    }
                },1500);
            }

            @Override
            public void loadmore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<10;i++){
                            strs.add("new datas "+i);
                        }
                      recy.setLoadMoreComplete();
                    }
                },1500);
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        ArrayList<String> mDatas = new ArrayList<>();
        OnClickListener mOnClickListener;

        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        public MyAdapter(ArrayList<String> datas) {
            mDatas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = MainActivity.this.getLayoutInflater().inflate(R.layout.item_recy, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv.setText(mDatas.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(holder.tv,position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv = (TextView) mView.findViewById(R.id.tv_item_recy);
        }
    }
    interface OnClickListener{
       void onClick(View v,int position);
    }

}
