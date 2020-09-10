package com.example.taskapp.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;
import com.example.taskapp.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<User> listUser;
    Context mContext;

    public UserAdapter(ArrayList<User> listUser, Context mContext) {
        this.listUser = listUser;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textNik.setText(listUser.get(position).getTextNikname());
        Glide.with(mContext).load(listUser.get(position).getImageAvatar()).circleCrop().into(holder.imageAvatar);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textNik;
        ImageView imageAvatar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNik = itemView.findViewById(R.id.textNik);
            imageAvatar = itemView.findViewById(R.id.imageUserAvatar);
        }

    }
}
