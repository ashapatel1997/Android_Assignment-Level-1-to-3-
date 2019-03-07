package com.example.asha.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asha.chatapplication.data.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by asha on 06-03-2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>{

SharedPreferences sp;

    ArrayList<User> users;
    private  Context context;

    public RecyclerViewAdapter( ArrayList<User> users) {

        this.users = users;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlistlayout,parent,false);
        viewHolder holder=new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position)
    {

        final User user=users.get(position);
        holder.tv_id.setText(String.valueOf(user.getId()));
        holder.tv_name.setText(user.getName());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Clicked on:",user.getId().toString());


                final Intent i=new Intent(context,ChatMessageActivity.class);
                i.putExtra("chat_id",user.getId().toString());
                i.putExtra("chat_name",user.getName());
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_id;
        TextView tv_name;
        RelativeLayout parent_layout;

        public viewHolder(View itemView)
        {
            super(itemView);
            context = itemView.getContext();
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_name=itemView.findViewById(R.id.tv_name);
            parent_layout=itemView.findViewById(R.id.parent_layout);
        }
    }
}
