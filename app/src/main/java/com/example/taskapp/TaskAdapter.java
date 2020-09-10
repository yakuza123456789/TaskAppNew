package com.example.taskapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.models.Task;
import com.example.taskapp.ui.interfaces.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> tasks;
    private OnItemClickListener onItemClickListener;
    public TaskAdapter(ArrayList<Task> list){
    tasks = list;
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
    ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(tasks.get(position),position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView time;
        private TextView updatedTime;
        int position;
        @SuppressLint("ResourceAsColor")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_text);

            time = itemView.findViewById(R.id.text_time);
            updatedTime = itemView.findViewById(R.id.text_updatedTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onItemClickListener.onItem(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }


        @SuppressLint("ResourceAsColor")
        public void bind(Task task, int position) {
            this.position = position;
            textView.setText(task.getTitle());
            if (task.getTitle().length() > 5) {
                itemView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
                time.setTextColor(Color.WHITE);
                updatedTime.setTextColor(Color.WHITE);
            } else {
                itemView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                time.setTextColor(Color.BLACK);
                updatedTime.setTextColor(Color.BLACK);

            }
            time.setText(getDate(task.getCreatedAt()));
            updatedTime.setText(getDate(task.getUpdateAt()));
        }

        private String getDate(long time){
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return dateFormat.format(time);
        }
    }


}
