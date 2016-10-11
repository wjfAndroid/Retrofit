package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.wjf.recyclerviewrefresh.util.Base64Utils;

public class Rsa2Activity extends AppCompatActivity {
    String s = "Rsa2Activity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa2);

        String encode = Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
        Log.e("Rsa2Activity", encode);
        String decode = new String(Base64.decode(encode, Base64.DEFAULT));
        Log.e("Rsa2Activity", decode);

        try {
            String e = Base64Utils.encode(s.getBytes());
            Log.e("Rsa2Activity","e:"+ e);

            String d = new String(Base64Utils.decode(e));
            Log.e("Rsa2Activity","d:"+ d);
        } catch (Exception e1) {
            e1.printStackTrace();
        }




    }
}
