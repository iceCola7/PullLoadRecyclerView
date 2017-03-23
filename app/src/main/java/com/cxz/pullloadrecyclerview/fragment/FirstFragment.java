package com.cxz.pullloadrecyclerview.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cxz.pullloadrecyclerview.R;
import com.cxz.pullloadrecyclerview.adapter.RecyclerViewAdapter;
import com.cxz.recyclerlibrary.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxz on 2015/12/27.
 */
public class FirstFragment extends Fragment {

    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mCount = 1;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullLoadMoreRecycleView);
        mPullLoadMoreRecyclerView.setRefreshing(false);

        new DataAsyncTask().execute();
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());

    }

    /**
     * 为RecycleView添加点击事件
     */
    private void setListeners() {
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long Click:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> setList() {
        List<String> dataList = new ArrayList<>();
        int start = 20 * (mCount - 1);
        for (int i = start; i < 20 * mCount; i++) {
            dataList.add("First " + i);
        }
        return dataList;
    }

    class DataAsyncTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return setList();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if (mRecyclerViewAdapter == null) {
                mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), strings);
                mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
                setListeners();
            } else {
                mRecyclerViewAdapter.getDataList().addAll(strings);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }

    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {

        @Override
        public void onRefresh() {
            setRefresh();
            new DataAsyncTask().execute();
        }

        @Override
        public void onLoadMore() {
            mCount++;
            new DataAsyncTask().execute();
        }
    }

    private void setRefresh() {
        mRecyclerViewAdapter.getDataList().clear();
        mCount = 1;
    }
}
