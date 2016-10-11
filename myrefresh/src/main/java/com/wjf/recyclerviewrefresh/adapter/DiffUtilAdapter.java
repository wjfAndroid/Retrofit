package com.wjf.recyclerviewrefresh.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class DiffUtilAdapter extends RecyclerView.Adapter<DiffUtilAdapter.DfViewHolder> {
    Activity mActivity;
    List<Person> mPersons;
    LayoutInflater mInflater;

    public DiffUtilAdapter(Activity activity) {
        mActivity = activity;
        mInflater = mActivity.getLayoutInflater();
    }

    public void setPersons(List<Person> persons) {
        mPersons = new ArrayList<>(persons);
    }

    public List<Person> getPersons() {
        return mPersons;
    }

    @Override
    public DfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_diff, parent, false);
        DfViewHolder holder = new DfViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DfViewHolder holder, int position) {
        System.out.println("onBindViewHolder2  position = [" + position + "]");
        holder.tv_name.setText(mPersons.get(position).getName());
        holder.tv_age.setText(mPersons.get(position).getAge() + "");
    }

    @Override
    public void onBindViewHolder(DfViewHolder holder, int position, List<Object> payloads) {
        if ( payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            DfViewHolder vh = holder;
            Bundle bundle = (Bundle) payloads.get(0);
            if (bundle.getInt("age") != 0) {
                vh.tv_age.setText(bundle.getInt("age") + "");
                vh.tv_age.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    class DfViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tv_name;
        TextView tv_age;

        public DfViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tv_age = (TextView) itemView.findViewById(R.id.textView3);
            tv_name = (TextView) itemView.findViewById(R.id.textView4);
        }
    }

}
