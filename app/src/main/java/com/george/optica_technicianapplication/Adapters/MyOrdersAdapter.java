package com.george.optica_technicianapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.george.optica_technicianapplication.Models.WorkTimer;
import com.george.optica_technicianapplication.R;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {
Context context;
ArrayList<WorkTimer>list;

    public MyOrdersAdapter(Context context, ArrayList<WorkTimer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v = LayoutInflater.from(context).inflate(R.layout.layout_orders_design,parent,false);
     return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     WorkTimer workTimer = list.get(position);
     holder.date.setText(workTimer.getDate());
     holder.orderNumber.setText(workTimer.getOrderNo());
     holder.timeStarted.setText(workTimer.getStartTime());
     holder.timeStopped .setText(workTimer.getStopTime());

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
    TextView date,orderNumber, timeStarted,timeStopped;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            orderNumber= itemView.findViewById(R.id.orderNumber);
            timeStarted = itemView.findViewById(R.id.timeStarted);
            timeStopped = itemView .findViewById(R.id.timeStopped);
        }
    }
}












