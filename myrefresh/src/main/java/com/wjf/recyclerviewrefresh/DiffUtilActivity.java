package com.wjf.recyclerviewrefresh;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.wjf.recyclerviewrefresh.adapter.DiffUtilAdapter;
import com.wjf.recyclerviewrefresh.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class DiffUtilActivity extends AppCompatActivity {
    DiffUtilAdapter adapter;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj instanceof DiffUtil.DiffResult) {
                DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;

             diffResult.dispatchUpdatesTo(adapter);
                adapter.setPersons(mNewDates);
                diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                    @Override
                    public void onInserted(int position, int count) {
                        System.out.println("onInserted   position = [" + position + "], count = [" + count + "]");
                        adapter.notifyItemInserted(position);
                    }

                    @Override
                    public void onRemoved(int position, int count) {
                        System.out.println("onRemoved   position = [" + position + "], count = [" + count + "]");
                        adapter.notifyItemRangeRemoved(position, count);
                    }

                    @Override
                    public void onMoved(int fromPosition, int toPosition) {
                        System.out.println("onMoved   fromPosition = [" + fromPosition + "], toPosition = [" + toPosition + "]");
                        adapter.notifyItemMoved(fromPosition, toPosition);
                    }

                    @Override
                    public void onChanged(int position, int count, Object payload) {
                        System.out.println("onChanged   position = [" + position + "], count = [" + count + "], payload = [" + payload + "]");
                        adapter.notifyItemRangeChanged(position, count, payload);
                    }
                });

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_util);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy);
        Button button = (Button) findViewById(R.id.bt_change);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDate();

        adapter = new DiffUtilAdapter(this);
        adapter.setPersons(mOldDates);
        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewDate();
                 getDiff();
                adapter.setPersons(mNewDates);
                adapter.notifyItemRangeChanged(9, 10);
            }
        });
    }

    List<Person> mOldDates = new ArrayList<>();
    List<Person> mNewDates = new ArrayList<>();

    public void getDate() {
        for (int i = 0; i < 10; i++) {
            mOldDates.add(new Person(i, "a" + i));
        }
    }

    public void getNewDate() {
        for (int i = 0; i < 20; i++) {
            if (i == 1 || i == 3) {
                mNewDates.add(new Person(i + 1, "a" + i));
            } else {
                mNewDates.add(new Person(i, "a" + i));
            }
        }
    }

    public void getDiff() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffCallback(mNewDates, mOldDates), true);
                Message msg = mHandler.obtainMessage();
                msg.obj = diffResult;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public class MyDiffCallback extends DiffUtil.Callback {

        private List<Person> mOldlist;
        private List<Person> mNewlist;

        public MyDiffCallback(List<Person> newlist, List<Person> oldlist) {
            mNewlist = newlist;
            mOldlist = oldlist;
        }

        @Override
        public int getOldListSize() {
            return mOldlist.size();
        }

        @Override
        public int getNewListSize() {
            return mNewlist.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            System.out.println("areItemsTheSame   mOldlist.get(oldItemPosition).getName() = [" + mOldlist.get(oldItemPosition).getName() + "], mNewlist.get(newItemPosition).getName() = [" +mNewlist.get(newItemPosition).getName() + "]");
            return mOldlist.get(oldItemPosition).getName().equals(mNewlist.get(newItemPosition).getName());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            System.out.println("areContentsTheSame  mOldlist.get(oldItemPosition).toString() = [" + mOldlist.get(oldItemPosition).toString() + "], mNewlist.get(newItemPosition).toString() = [" + mNewlist.get(newItemPosition).toString() + "]");
            return mOldlist.get(oldItemPosition).toString().equals(mNewlist.get(newItemPosition).toString());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            Person oldPerson = mOldlist.get(oldItemPosition);
            Person newPerson = mNewlist.get(newItemPosition);
            Bundle diffBundle = new Bundle();
            if (oldPerson.getAge() != newPerson.getAge()) {
                diffBundle.putInt("age", newPerson.getAge());
            }
            if (diffBundle.size() == 0) {
                return null;
            }
            return diffBundle;
        }
    }
}
