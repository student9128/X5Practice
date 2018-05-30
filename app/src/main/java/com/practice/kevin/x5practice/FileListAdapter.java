package com.practice.kevin.x5practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/29.
 * <h3>
 * Describe:
 * <h3/>
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder> {

    private Context context;
    private List<FileInfoBean> data;

    public FileListAdapter(Context context, List<FileInfoBean> data) {
        this.context = context;
        this.data = data;
    }

    public void updateData(List<FileInfoBean> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }
    public void addData(FileInfoBean d){
        data.add(0,d);
       notifyItemInserted(0);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_file_list, parent,
                false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvFileName.setText(data.get(position).getFileName());
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRecyclerItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_file_name)
        TextView tvFileName;
        @BindView(R.id.ll_container)
        LinearLayout llContainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnRecyclerItemClickListener{
        void onRecyclerItemClick(int position);
    }

    private OnRecyclerItemClickListener listener;
    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener l){
        this.listener = l;
    }
}
