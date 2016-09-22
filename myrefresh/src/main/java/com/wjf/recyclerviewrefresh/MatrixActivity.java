package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjf.recyclerviewrefresh.view.MatrixViewRectToRect;

public class MatrixActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MatrixViewRectToRect(this));


    }
}
