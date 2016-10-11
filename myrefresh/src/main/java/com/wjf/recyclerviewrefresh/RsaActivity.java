package com.wjf.recyclerviewrefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.wjf.recyclerviewrefresh.bean.Person;
import com.wjf.recyclerviewrefresh.util.Base64Decoder;
import com.wjf.recyclerviewrefresh.util.Base64Encoder;
import com.wjf.recyclerviewrefresh.util.RSAUtil;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;



public class RsaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        getdata();
        getKeyPairs();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    erydey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        //      Log.e("RsaActivity", "加密前数据:" + json);
        Log.e("RsaActivity", "加密前数据长度:" + json.length());

    }

    RSAPublicKey publicKey;
    RSAPrivateKey privateKey;

    public void getKeyPairs() {
        KeyPair keyPair = RSAUtil.generateRSAKeyPair(RSAUtil.DEFAULT_KEY_SIZE);
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    public void erydey() throws Exception {
        long start = System.currentTimeMillis();
        byte[] encryptbytes = RSAUtil.encryptByPublicKeyForSpilt(json.getBytes(), publicKey.getEncoded());
        long end = System.currentTimeMillis();
        Log.e("RsaActivity", "公钥加密时长：" + (end - start));
        String encrystr = Base64Encoder.encode(encryptbytes);
        //      Log.e("RsaActivity", "加密后数据：" + encrystr);
        Log.e("RsaActivity", "加密后数据长度:" + encrystr.length());


        start = System.currentTimeMillis();
        byte[] decrybytes = RSAUtil.decryptByPrivateKeyForSpilt(Base64Decoder.decodeToBytes(encrystr), privateKey.getEncoded());
        String decrystr = new String(decrybytes);
        end = System.currentTimeMillis();
        Log.e("RsaActivity", "私钥解密时长：" + (end - start));
        Log.d("RsaActivity", "解密后数据：" + decrystr);

        start = System.currentTimeMillis();
        encryptbytes = RSAUtil.encryptByPrivateKeyForSpilt(json.getBytes(), privateKey.getEncoded());
        end = System.currentTimeMillis();
        Log.e("RsaActivity", "私钥加密时长：" + (end - start));
        encrystr =Base64Encoder.encode(encryptbytes);
        //  Log.e("RsaActivity", "私钥加密后数据：" + decrystr);
        Log.e("RsaActivity", "私钥加密后数据长度:" + encrystr.length());


        start = System.currentTimeMillis();
        decrybytes = RSAUtil.decryptByPublicKeyForSpilt(Base64Decoder.decodeToBytes(encrystr), publicKey.getEncoded());
        decrystr = new String(decrybytes);
        end = System.currentTimeMillis();
        Log.e("RsaActivity", "公钥解密时长：" + (end - start));
        Log.d("RsaActivity", "公钥解密后数据：" + decrystr);

    }

}
