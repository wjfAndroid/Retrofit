package com.wjf.test.net.Progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ProgressHandler extends Handler {
    public static final int SHOW_PROGRESS = 0x1;
    public static final int DISMISS_PORGRESS = 0x2;
    Context mContext;
    boolean cancelable;
    ProgressCancelListener mProgressCancelListener;
    ProgressDialog mProgressDialog;

    public ProgressHandler(Context context, boolean cancelable, ProgressCancelListener progressCancelListener) {
        mContext = context;
        this.cancelable = cancelable;
        mProgressCancelListener = progressCancelListener;
    }

    private void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);

            mProgressDialog.setCancelable(cancelable);
            if (cancelable) {
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.cancelProgress();
                    }
                });
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }


    }

    private void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS:
                showProgress();
                break;
            case DISMISS_PORGRESS:
                dismissProgress();
                break;
        }
    }


}
