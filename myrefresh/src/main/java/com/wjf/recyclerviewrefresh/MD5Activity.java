package com.wjf.recyclerviewrefresh;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.wjf.recyclerviewrefresh.util.MD5;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
      //  testPermissions();
        dosha1();
    }
    public  void domd5(){
        String path = Environment.getExternalStorageDirectory() + File.separator + "deviceid.txt";

        File file = new File(path);

        Log.e("MD5Activity", "md5file1:" + MD5.md5File(file));

        Log.e("MD5Activity", "md5file2:" + MD5.md5File2(file));
    }

    public void dosha1(){
        String s1 = "aabbcc";
        Log.e("MD5Activity", "sha1:" + sha(s1));
        Log.e("MD5Activity", "getSha1:" + getSha1(s1));
    }

    public static String sha(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("sha-256");
            byte[] bytes = md5.digest((string ).getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA256");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    public void testPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MD5Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MD5Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                System.out.println("拒绝但是没有选择不再提示");
                ActivityCompat.requestPermissions(MD5Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            } else {
                System.out.println(" 第一次   或者    拒绝并且不再提示");
                ActivityCompat.requestPermissions(MD5Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            }
        } else {
            System.out.println(" 允许之后  再次调用 ");
            domd5();
        }
    }
}
