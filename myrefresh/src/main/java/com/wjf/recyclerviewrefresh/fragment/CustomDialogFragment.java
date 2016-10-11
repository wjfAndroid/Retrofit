package com.wjf.recyclerviewrefresh.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.wjf.recyclerviewrefresh.R;

/**
 * Created by Administrator on 2016/10/8.
 * <p/>
 * 0.优点;生命周期管理方便
 * 1.DialogFragment继承与fragment，可以实现dialog样式的弹窗效果，也可以实现fragment显示在activity指定的container里面
 * 2.dialog样式的：复写onCreateView或者onCreateDialog方法
 * 3.启动和消失:
 * CustomDialogFragment fragment = new CustomDialogFragment();
 * fragment.show(getSupportFragmentManager(), "CustomDialogFragment");
 * <p/>
 * fragment.dismiss();
 * 4.数据返回到activity，采用自定义接口回调的方式
 * 4.实现再次弹窗：
 * FragmentTransaction transaction = mFragmentManager.beginTransaction();
 * transaction.remove(CustomDialogFragment.this);
 * transaction.addToBackStack("CustomDialogFragment");
 * new SecondDialogFragment().show(CustomDialogFragment.this.getFragmentManager(), "SecondDialogFragment");
 */
public class CustomDialogFragment extends android.support.v4.app.DialogFragment {
    UserInfoListener mUserInfoListener;
    FragmentManager mFragmentManager;

    public void setUserInfoListener(UserInfoListener userInfoListener) {
        mUserInfoListener = userInfoListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        mFragmentManager = getFragmentManager();

        view.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.remove(CustomDialogFragment.this);
                transaction.addToBackStack("CustomDialogFragment");
                new SecondDialogFragment().show(CustomDialogFragment.this.getFragmentManager(), "SecondDialogFragment");

            }
        });
        return view;
    }


    public interface UserInfoListener {
        void getuserInfo(String id, String psw);
    }

}
