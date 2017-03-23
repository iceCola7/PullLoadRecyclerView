package com.cxz.pullloadrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxz.pullloadrecyclerview.R;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxz on 2015/12/27.
 */
public class StaggeredRecycleViewAdapter extends RecyclerView.Adapter<StaggeredRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Map<String, String>> dataList;

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置RecycleView点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 点击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public StaggeredRecycleViewAdapter(Context mContext, List<Map<String, String>> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).get("text"));
        holder.title.setHeight(Integer.parseInt(dataList.get(position).get("height")));//高度随机，下拉刷新高度会变

        //为每个Item添加点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
