package com.wjf.recyclerviewrefresh.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjf.recyclerviewrefresh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondDialogFragment extends DialogFragment {


    public SecondDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_dialog, container, false);
        view.findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondDialogFragment.this.dismiss();
            }
        });
        return view;
    }

}
