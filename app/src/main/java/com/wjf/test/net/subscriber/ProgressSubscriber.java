package com.wjf.test.net.subscriber;

import android.content.Context;

import com.wjf.test.net.Progress.ProgressCancelListener;
import com.wjf.test.net.Progress.ProgressHandler;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    SubscriberOnNextListener mSubscriberOnNextListener;
    Context mContext;
    ProgressHandler mProgressHandler;

    public ProgressSubscriber(SubscriberOnNextListener subscriberOnNextListener, Context context) {
        mSubscriberOnNextListener = subscriberOnNextListener;
        mContext = context;
        mProgressHandler = new ProgressHandler(context, true, this);
    }

    public void showProgress() {
        if (mProgressHandler != null) {
            mProgressHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget();
        }

    }

    public void dismissProgress() {
        if (mProgressHandler != null) {
            mProgressHandler.obtainMessage(ProgressHandler.DISMISS_PORGRESS).sendToTarget();
            mProgressHandler = null;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();

    }

    @Override
    public void onError(Throwable e) {
        System.out.println("e = " + e);

    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    @Override
    public void cancelProgress() {
        if (!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
