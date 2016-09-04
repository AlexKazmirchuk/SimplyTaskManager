package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.R;
import com.alexkaz.simplytaskmanager.uicomp.PieChartView;
import com.alexkaz.simplytaskmanager.uicomp.TaskIndicatorView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;

import java.util.ArrayList;

public class MainTaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TaskObject> taskObjects;
    private LayoutInflater inflater;

    public MainTaskAdapter(Context context, ArrayList<TaskObject> taskObjects) {
        this.context = context;
        this.taskObjects = taskObjects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return taskObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        final ViewHolder holder;
        if(itemView == null){
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.main_item,parent,false);

            holder.mainItemIcon = (ImageView) itemView.findViewById(R.id.mainItemIcon);
            holder.mainItemTitle = (TextView) itemView.findViewById(R.id.mainItemTitle);
            holder.mainItemTaskIndictor = (TaskIndicatorView) itemView.findViewById(R.id.mainItemTaskIndictor);
            holder.mainItemPieChart = (PieChartView) itemView.findViewById(R.id.mainItemPieChart);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder)itemView.getTag();
        }
        holder.ref = position;

        ///////
        switch (taskObjects.get(position).getIcon()){
            case "work_icon":
                holder.mainItemIcon.setImageResource(R.drawable.work_icon);
                break;
            case "home_icon":
                holder.mainItemIcon.setImageResource(R.drawable.home_icon);
                break;
            case "fun_icon":
                holder.mainItemIcon.setImageResource(R.drawable.fun_icon);
                break;
            case "other_icon":
                holder.mainItemIcon.setImageResource(R.drawable.other_icon);
                break;
        }
        holder.mainItemTitle.setText(taskObjects.get(position).getTaskTitle());
        holder.mainItemTaskIndictor.setTaskStatuses(taskObjects.get(position).getStatuses());
        holder.mainItemTaskIndictor.invalidate();
        holder.mainItemPieChart.setTaskStatuses(taskObjects.get(position).getStatuses());
        holder.mainItemPieChart.invalidate();
        ///////
        return itemView;
    }

    private class ViewHolder {
        ImageView mainItemIcon;
        TextView mainItemTitle;
        TaskIndicatorView mainItemTaskIndictor;
        PieChartView mainItemPieChart;
        int ref;
    }

    public void removeItem(int position){
        taskObjects.remove(position);
        notifyDataSetChanged();
    }
}
