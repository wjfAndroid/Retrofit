package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.wjf.recyclerviewrefresh.adapter.ExlvAdaptr;
import com.wjf.recyclerviewrefresh.view.CustomExpandedableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExLvActivity extends AppCompatActivity {

    private String[] parentSource = {"分类1", "分类2", "分类3", "分类4", "分类5"};
    private ArrayList<String> parent = new ArrayList<>();
    private Map<String, ArrayList<String>> datas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_lv);
        CustomExpandedableListView exlv = (CustomExpandedableListView) findViewById(R.id.exlv);

        for (int i = 0; i < parentSource.length; i++) {
            parent.add(parentSource[i]);
            ArrayList<String> strings = new ArrayList<>();
            for (int j = 0; j < 25; j++) {
                strings.add("item" + j);
            }
            datas.put(parentSource[i], strings);
        }


        ExlvAdaptr adaptr = new ExlvAdaptr(parent,datas,exlv,this);
        exlv.setAdapter(adaptr);
        View headView = this.getLayoutInflater().inflate(R.layout.item_indictor,exlv,false);
        exlv.setHeadView(headView);
        exlv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ExLvActivity.this, "点击了第" + (groupPosition + 1) + " 类的第" + childPosition + "项", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        for (int i=0;i<parent.size();i++){
            exlv.expandGroup(i);
        }
    }
}
