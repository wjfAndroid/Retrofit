package com.wjf.recyclerviewrefresh;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wjf.recyclerviewrefresh.view.SimpleShadowView;

public class ShadeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shade);
        final SimpleShadowView simpleShadowView = (SimpleShadowView) findViewById(R.id.simple_shadowview);
      Button button = (Button) findViewById(R.id.radious);
        button.setShadowLayer(2,6,6, Color.RED);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleShadowView.changeRadious();
            }
        });
        findViewById(R.id.dx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleShadowView.changeDx();
            }
        });
        findViewById(R.id.dy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleShadowView.changeDy();
            }
        });
    }
}
