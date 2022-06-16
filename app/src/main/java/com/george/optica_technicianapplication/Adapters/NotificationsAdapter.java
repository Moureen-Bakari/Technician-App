package com.george.optica_technicianapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.george.optica_technicianapplication.Models.Notification;
import com.george.optica_technicianapplication.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.myViewHolder> {
    private Context mContext;
    private ArrayList<Notification> notifications;

    public NotificationsAdapter(Context mContext, ArrayList<Notification> notifications) {
        this.mContext = mContext;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orders_design, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Notification notificationsData = notifications.get(position);

        holder.orderNumber.setText(notificationsData.getOrderNumber());
        holder.startTime.setText(notificationsData.getTimeStarted());
        holder.stopTime.setText(notificationsData.getTimeStopped());
        holder.timeOrderSent.setText(notificationsData.getTimeOrderSent());
        holder.date.setText(notificationsData.getDate());
    }

    @Override
    public int getItemCount() {
        if (notifications != null) {
            return notifications.size();
        } else {
            return 0;
        }
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView orderNumber, startTime, stopTime, timeOrderSent, date, clearedOrNot;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.orderNumber);
             startTime = itemView.findViewById(R.id.timeStarted);
            stopTime = itemView.findViewById(R.id.timeStopped);
            date = itemView.findViewById(R.id.date);
        }
    }
}











