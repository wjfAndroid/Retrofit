package com.wjf.recyclerviewrefresh;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SvgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        ImageView iv = (ImageView) findViewById(R.id.iv_lines);
       Drawable drawable = iv.getDrawable();
        if (drawable instanceof Animatable){
            System.out.println("SvgActivity.onCreate");
            ((Animatable) drawable).start();
        }
    }
}
