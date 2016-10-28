package com.wjf.recyclerviewrefresh.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BaseApplication extends Application {
    public static final String TAG = "wjf";
    private static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();
        if (queues == null) {
            queues = Volley.newRequestQueue(this);
        }
    }

    public static RequestQueue getQueues() {
        if (queues != null) {
            return queues;
        }
        return null;
    }
}
