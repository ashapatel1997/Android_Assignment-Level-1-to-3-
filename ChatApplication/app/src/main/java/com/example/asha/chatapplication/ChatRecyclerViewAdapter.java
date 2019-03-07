package com.example.asha.chatapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asha.chatapplication.data.model.Message;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by asha on 07-03-2019.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.viewHolder>{

    private Context context;
    ArrayList<Message> msgs;

    public ChatRecyclerViewAdapter(ArrayList<Message> msgs) {
        this.msgs = msgs;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatmessagelayout,parent,false);
        viewHolder holder=new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        final Message msg=msgs.get(position);
        holder.tv_from.setText("From: "+String.valueOf(msg.getFromUserId()));
        holder.tv_message.setText("Message: "+msg.getMessage());
        holder.tv_to.setText("To: "+String.valueOf(msg.getToUserId()));
        holder.tv_datetime.setText(msg.getCreatedDateTime());

        Log.e("Message",msg.getMessage());
        holder.chat_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked on:",msg.getMessage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_from;
        TextView tv_message;
        TextView tv_to;
        TextView tv_datetime;
        RelativeLayout chat_parent_layout;

        public viewHolder(View itemView)
        {
            super(itemView);
            context=itemView.getContext();
            tv_from=itemView.findViewById(R.id.tv_from);
            tv_message=itemView.findViewById(R.id.tv_message);
            tv_to=itemView.findViewById(R.id.tv_to);
            tv_datetime=itemView.findViewById(R.id.tv_datetime);
            chat_parent_layout=itemView.findViewById(R.id.chat_parent_layout);


        }
    }
}
