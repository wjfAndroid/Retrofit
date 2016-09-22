package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjf.recyclerviewrefresh.view.SimpleCustomCircle;

public class SimpleCustomCircleActivity extends Activity {
    SimpleCustomCircle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_custom_circle);
        circle = (SimpleCustomCircle) findViewById(R.id.c);
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   System.out.println("SimpleCustomCircleActivity.onResume");
        circle.restart();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // System.out.println("SimpleCustomCircleActivity.onPause");
        circle.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   System.out.println("SimpleCustomCircleActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  System.out.println("SimpleCustomCircleActivity.onDestroy");
    }

}
