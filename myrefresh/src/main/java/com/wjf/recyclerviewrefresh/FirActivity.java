package com.wjf.recyclerviewrefresh;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.wjf.recyclerviewrefresh.base.BaseApplication;
import com.wjf.recyclerviewrefresh.bean.FirBean;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FirActivity extends AppCompatActivity {

    @InjectView(R.id.button9)
    Button button9;
    @InjectView(R.id.textView5)
    TextView textView5;

    String apkUrl;
    @InjectView(R.id.down)
    Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir);
        ButterKnife.inject(this);

        testPermissions();

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVersion();
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoad();
            }
        });
    }

    public void getVersion() {
        String url = "http://api.fir.im/apps/latest/5805eb5c959d6965de00051d?api_token=1444687b5e16f75a9043b066061e8dc1";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FirActivity", response);
                Gson gson = new Gson();
                FirBean bean = gson.fromJson(response, FirBean.class);
                apkUrl = bean.getInstall_url();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FirActivity", "error:" + error);
            }
        });
        BaseApplication.getQueues().add(request);
    }

    public void DownLoad() {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //String apkUrl = "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalPublicDir("myrefresh", "myrefresh.apk");
        //request.addRequestHeader("User-Agent", "PacificHttpClient");
// request.setTitle("MeiLiShuo");
// request.setDescription("MeiLiShuo desc");
// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
// request.setMimeType("application/cn.trinea.download.file");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        lastDownloadId = downloadManager.enqueue(request);

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadObserver = new DownloadChangeObserver(null);
        getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
    }

    long lastDownloadId;
    DownloadManager downloadManager;
    DownloadChangeObserver downloadObserver;
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");

    public void createFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "myrefresh");
        if (!file.exists()) {
            boolean b = file.mkdirs();
            Log.d("FirActivity", "file.mkdirs():" + b);
        }
    }

    class DownloadChangeObserver extends ContentObserver {


        public DownloadChangeObserver(Handler handler) {
            super(handler);

        }


        @Override
        public void onChange(boolean selfChange) {
            queryDownloadStatus();
        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Log.v("onReceive tag", "" + intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
//            queryDownloadStatus();

            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (completeDownloadId == lastDownloadId) {
                Log.w("tag", "download complete");


            }
        }
    };

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(lastDownloadId);
        Cursor c = downloadManager.query(query);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            String title = c.getString(titleIdx);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = c.getInt(reasonIdx);
            c.close();
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);

            // Display the status
            Log.d("tag", sb.toString());
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");


                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
//                  dowanloadmanager.remove(lastDownloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    downloadManager.remove(lastDownloadId);
                    break;
            }
        }
    }

    public void testPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(FirActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(FirActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                System.out.println("拒绝但是没有选择不再提示");
                ActivityCompat.requestPermissions(FirActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            } else {
                System.out.println(" 第一次   或者    拒绝并且不再提示");
                ActivityCompat.requestPermissions(FirActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            }
        } else {
            System.out.println(" 允许之后  再次调用 ");
            createFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createFile();
                } else {
                    Toast.makeText(this, "PERMISSION_deny", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        unregisterReceiver(receiver);
        getContentResolver().unregisterContentObserver(downloadObserver);
    }
}
