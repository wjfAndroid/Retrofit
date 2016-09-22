package com.wjf.recyclerviewrefresh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.wjf.recyclerviewrefresh.R;
import com.wjf.recyclerviewrefresh.bean.Point;
import com.wjf.recyclerviewrefresh.view.CustomExpandedableListView;

import java.security.PolicySpi;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ExlvAdaptr extends BaseExpandableListAdapter implements CustomExpandedableListView.HeadAdapter {
    ArrayList<String> groupData;
    Map<String, ArrayList<String>> childData;
    CustomExpandedableListView mExpandedableListView;
    Activity mActivity;

    public ExlvAdaptr(ArrayList<String> groupData, Map<String, ArrayList<String>> childData, CustomExpandedableListView expandedableListView, Activity activity) {
        this.groupData = groupData;
        this.childData = childData;
        mExpandedableListView = expandedableListView;
        mActivity = activity;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String s = groupData.get(groupPosition);
        return childData.get(s).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String s = groupData.get(groupPosition);
        return childData.get(s).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_gp);
        tv.setText(groupData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new viewHolder();
            LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_child, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_child);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ExlvAdaptr.viewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(childData.get(groupData.get(groupPosition)).get(childPosition));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public int getHeadState(int groupPosition, int childPosition) {
        int childCount = getChildrenCount(groupPosition);
        if (childCount - 1 == childPosition) {
            return HEAD_STATE_PUSHED_UP;
        } else if (childPosition == -1 && !mExpandedableListView.isGroupExpanded(groupPosition)) {
            return HEAD_STATE_GONE;
        } else {
            return HEAD_STATE_VISIABLE;
        }
    }

    @Override
    public void configHeadView(View headView, int groupPosition, int childPosition, int alpha) {
        if (groupPosition > -1) {
            TextView tv = (TextView) headView.findViewById(R.id.tv_indictor);
            tv.setText(groupData.get(groupPosition));
        }


    }

    class viewHolder {
        TextView tv;
    }
}
