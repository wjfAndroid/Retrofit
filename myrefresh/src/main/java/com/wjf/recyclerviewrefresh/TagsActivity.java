package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjf.recyclerviewrefresh.view.TagsLayout;

public class TagsActivity extends Activity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        TagsLayout tl = (TagsLayout) findViewById(R.id.tl);
        String[] s = {"从我写代码那天起，我就没有打算写代码,从我写代码那天起", "从我写代码那天起，我就没有打算写代码", "从我写代码那天起", "我就没有打算写代码", "没打算", "写代码"};
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < s.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(s[i]);
            tv.setTextColor(Color.RED);
            tv.setBackgroundColor(Color.BLUE);
            tl.addView(tv, layoutParams);
        }



    }

}
