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

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Season on 2017/5/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;

    private List<Message> mMessageList;

    private Message message;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView messageImage;
        TextView messageName;
        TextView messageTime;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            //messageImage = (ImageView) view.findViewById(R.id.message_image);
            messageName = (TextView) view.findViewById(R.id.message_name);
            messageTime = (TextView) view.findViewById(R.id.message_time);
        }
    }

    public MessageAdapter(List<Message> messageList) {
        mMessageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                message = mMessageList.get(position);
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra(MessageActivity.MESSAGE_NAME, message.getName());
                intent.putExtra(MessageActivity.MESSAGE_IMAGE_ID, message.getImageId());
                intent.putExtra(MessageActivity.MESSAGE_CONTENT, message.getContent());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessageList.get(position);
        holder.messageName.setText(message.getName());
        holder.messageTime.setText(message.getTime());
        //Glide.with(mContext).load(message.getImageId()).into(holder.messageImage);
        //Glide.with(mContext).load("http://192.168.123.76/" + message.getImageId()).into(holder.messageImage);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
