package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.leolin.shortcutbadger.ShortcutBadger;

public class ShortCutBadgerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_cut_badger);
        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int badgeCount = 1;
                ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
            }
        });
        findViewById(R.id.bt_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShortcutBadger.removeCount(getApplicationContext());
            }
        });

    }
}
