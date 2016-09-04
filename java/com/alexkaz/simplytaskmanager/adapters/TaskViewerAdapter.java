package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.FullTaskActivity;
import com.alexkaz.simplytaskmanager.R;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.HorizontalTaskIndicatorView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;

public class TaskViewerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemTitles;
    private ArrayList<TaskStatus> statuses;
    private LayoutInflater inflater;
    private TaskStatus choosedStatus;

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
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder holder;
        if (itemView == null){
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.item_review,parent,false);
            holder.linearLayout = (LinearLayout)itemView.findViewById(R.id.reviewTaskItemLayout);
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
        ////////////////////
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] statusItems = new String[]{"Done","In process","Not completed"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("Please choose task status!");
                alertBuilder.setSingleChoiceItems(statusItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                choosedStatus = TaskStatus.DONE;
                                break;
                            case 1:
                                choosedStatus = TaskStatus.IN_PROCESS;
                                break;
                            case 2:
                                choosedStatus = TaskStatus.NOT_COMPLITED;
                                break;
                        }
                    }
                });
                alertBuilder.setNegativeButton("Cancel",null);
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(choosedStatus == null){
                            statuses.set(position,TaskStatus.DONE);
                            new DBHelper(context).setStatus(itemTitles.get(position),2);
                        } else{
                            statuses.set(position,choosedStatus);
                            switch (choosedStatus){
                                case DONE:
                                    new DBHelper(context).setStatus(itemTitles.get(position),2);
                                    break;
                                case IN_PROCESS:
                                    new DBHelper(context).setStatus(itemTitles.get(position),1);
                                    break;
                                case NOT_COMPLITED:
                                    new DBHelper(context).setStatus(itemTitles.get(position),0);
                                    break;
                            }
                        }
                        choosedStatus = null;
                        ((FullTaskActivity)context).initStatisticPanel();
                        notifyDataSetChanged();
                    }
                });
                alertBuilder.create().show();
            }
        });


        ////////////////////
        return itemView;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        HorizontalTaskIndicatorView horizontalTaskIndicatorView;
        TextView textView;
        int ref;
    }
}
