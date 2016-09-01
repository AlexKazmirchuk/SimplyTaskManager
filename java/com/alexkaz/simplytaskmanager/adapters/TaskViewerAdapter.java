package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.R;
import com.alexkaz.simplytaskmanager.uicomp.HorizontalTaskIndicatorView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;

public class TaskViewerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemTitles;
    private ArrayList<TaskStatus> statuses;
    private LayoutInflater inflater;

    public TaskViewerAdapter(Context context, TaskObject taskObject){
        this.context = context;
        this.itemTitles = taskObject.getItemTitles();
        this.statuses = taskObject.getStatuses();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return itemTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        final ViewHolder holder;
        if (itemView == null){
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.item_review,parent,false);
            holder.horizontalTaskIndicatorView = (HorizontalTaskIndicatorView)itemView.findViewById(R.id.horTaskIndicator);
            holder.textView = (TextView)itemView.findViewById(R.id.itemTextView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder)itemView.getTag();
        }
        holder.ref = position;
        if (position == 0){
            holder.horizontalTaskIndicatorView.setStatus(statuses.get(position),null,false);
        } else if (position == (statuses.size()-1)){
            holder.horizontalTaskIndicatorView.setStatus(statuses.get(position),statuses.get(position-1),true);
        } else {
            holder.horizontalTaskIndicatorView.setStatus(statuses.get(position),statuses.get(position-1),false);
        }
        holder.horizontalTaskIndicatorView.invalidate();
        holder.textView.setText((position+1) + ". " + itemTitles.get(position));
        return itemView;
    }

    private class ViewHolder {
        HorizontalTaskIndicatorView horizontalTaskIndicatorView;
        TextView textView;
        int ref;
    }
}
