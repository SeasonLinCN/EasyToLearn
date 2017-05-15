package com.example.season.easytolearn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Season on 2017/5/4.
 */

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    private Context mContext;

    private List<Work> mWorkList;

    private Work work;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView workImage;
        TextView workName;
        TextView workDeadLine;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            workImage = (ImageView) view.findViewById(R.id.work_image);
            workName = (TextView) view.findViewById(R.id.work_name);
            workDeadLine = (TextView) view.findViewById(R.id.work_deadline);
        }
    }

    public WorkAdapter(List<Work> workList) {
        mWorkList = workList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.work_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                work = mWorkList.get(position);
                Intent intent = new Intent(mContext, WorkActivity.class);
                intent.putExtra(WorkActivity.WORK_NAME, work.getName());
                intent.putExtra(WorkActivity.WORK_IMAGE_ID, work.getImageId());
                intent.putExtra(WorkActivity.WORK_CONTENT, work.getContent());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Work work = mWorkList.get(position);
        holder.workName.setText(work.getName());
        holder.workDeadLine.setText(work.getDeadLine());
        Glide.with(mContext).load("http://192.168.123.76/" + work.getImageId()).into(holder.workImage);
    }

    @Override
    public int getItemCount() {
        return mWorkList.size();
    }
}
