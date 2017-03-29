package com.cxz.pullloadrecyclerview.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cxz.pullloadrecyclerview.R;
import com.cxz.pullloadrecyclerview.adapter.StaggeredRecycleViewAdapter;
import com.cxz.recyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by chenxz on 2015/12/27.
 */
public class ThirdFragment extends Fragment {
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mCount = 1;
    private StaggeredRecycleViewAdapter mRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullLoadMoreRecycleView);
        mPullLoadMoreRecyclerView.setRefreshing(true);
        new DataAsyncTask().execute();
        mPullLoadMoreRecyclerView.setStaggeredGridLayout(2);
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
    }

    /**
     * 为RecycleView添加点击事件
     */
    private void setListeners() {
        mRecyclerViewAdapter.setOnItemClickListener(new StaggeredRecycleViewAdapter.OnItemClickListener() {
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

    private List<Map<String, String>> setList() {
        List<Map<String, String>> dataList = new ArrayList<>();
        int start = 20 * (mCount - 1);
        Map<String, String> map;
        for (int i = start; i < 20 * mCount; i++) {
            map = new HashMap<>();
            map.put("text", "Third" + i);
            map.put("height", (100 + 2 * i) + "");
            dataList.add(map);
        }
        return dataList;

    }

    class DataAsyncTask extends AsyncTask<Void, Void, List<Map<String, String>>> {
        @Override
        protected List<Map<String, String>> doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return setList();
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> strings) {
            super.onPostExecute(strings);
            if (mRecyclerViewAdapter == null) {
                mRecyclerViewAdapter = new StaggeredRecycleViewAdapter(getActivity(), strings);
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
            mCount = mCount + 1;
            new DataAsyncTask().execute();
        }
    }

    private void setRefresh() {

        mRecyclerViewAdapter.getDataList().clear();

        mCount = 1;

    }

}
