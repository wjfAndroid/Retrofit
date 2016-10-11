package com.wjf.recyclerviewrefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wjf.recyclerviewrefresh.bean.Person;
import com.wjf.recyclerviewrefresh.util.AesUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);
        getdata();
        getSecretKey();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dothings();
            }
        }).start();
    }

    String json;

    private void getdata() {
        ArrayList<Person> persons = new ArrayList<>();
        int count = 100;
        for (int i = 0; i < count; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        Gson gson = new Gson();
        json = gson.toJson(persons);
        Log.e("RsaActivity", "加密前数据:" + json);
        Log.e("RsaActivity", "加密前数据长度:" + json.length());

    }

    String secretKey;
    String secretKey2;

    private void getSecretKey() {
        secretKey = AesUtils.generateKey();
        Log.e("AesActivity", secretKey);
    }

    public void dothings() {
        long start = System.currentTimeMillis();
        String encode = AesUtils.encrypt(secretKey, json);
        long end = System.currentTimeMillis();
        Log.e("AesActivity", "加密后数据:" + encode);
        Log.e("AesActivity", "加密数据所用时间:" + (end - start));

        start = System.currentTimeMillis();
        String decode = AesUtils.decrypt(secretKey, encode);
        end = System.currentTimeMillis();
        Log.e("AesActivity", "解密数据:" + decode);
        Log.e("AesActivity", "解密数据所用时间:" + (end - start));
    }


}
