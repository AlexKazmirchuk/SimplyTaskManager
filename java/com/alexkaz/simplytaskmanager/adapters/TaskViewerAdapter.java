package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.FullTaskActivity;
import com.alexkaz.simplytaskmanager.R;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.VerticalTaskIndicatorView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;

import static com.alexkaz.simplytaskmanager.uicomp.TaskObject.STATUS_DONE;
import static com.alexkaz.simplytaskmanager.uicomp.TaskObject.STATUS_IN_PROCESS;
import static com.alexkaz.simplytaskmanager.uicomp.TaskObject.STATUS_NOT_COMPLETED;

public class TaskViewerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemTitles;
    private ArrayList<TaskStatus> statuses;
    private LayoutInflater inflater;
    private TaskStatus selectedStatus;
    private boolean someStatusChanged = false;

    private class ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        VerticalTaskIndicatorView verticalTaskIndicatorView;
        TextView textView;
        int ref;

        ViewHolder(View itemView){
            linearLayout = (LinearLayout)itemView.findViewById(R.id.reviewTaskItemLayout);
            verticalTaskIndicatorView = (VerticalTaskIndicatorView)itemView.findViewById(R.id.horTaskIndicator);
            textView = (TextView)itemView.findViewById(R.id.itemTextView);
        }

        private void refreshIndicator(int position){
            if (position == 0){
                verticalTaskIndicatorView.setStatus(statuses.get(position),null,false);
            } else if (position == (statuses.size()-1)){
                verticalTaskIndicatorView.setStatus(statuses.get(position),statuses.get(position-1),true);
            } else {
                verticalTaskIndicatorView.setStatus(statuses.get(position),statuses.get(position-1),false);
            }
            verticalTaskIndicatorView.invalidate();
        }

        void refreshView(int position){
            ref = position;
            refreshIndicator(position);
            textView.setText((position + 1) + ". " + itemTitles.get(position));
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String[] statusItems = context.getResources().getStringArray(R.array.status_dialog_choosing_titles);
            showChangeStatusDialog(statusItems);
        }

        private void showChangeStatusDialog(String[] statusItems){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setTitle(context.getResources().getString(R.string.status_dialog_title));
            alertBuilder.setSingleChoiceItems(statusItems, 0, initDialogOnClickListener());
            alertBuilder.setNegativeButton(context.getResources().getString(R.string.status_dialog_negative_button_text),null);
            alertBuilder.setPositiveButton(context.getResources().getString(R.string.status_dialog_positive_button_text), initPositiveBtnOnClickListener());
            alertBuilder.create().show();
        }

        private DialogInterface.OnClickListener initDialogOnClickListener(){
            return new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:
                            selectedStatus = TaskStatus.DONE;
                            break;
                        case 1:
                            selectedStatus = TaskStatus.IN_PROCESS;
                            break;
                        case 2:
                            selectedStatus = TaskStatus.NOT_COMPLETED;
                            break;
                    }
                }
            };
        }

        private DialogInterface.OnClickListener initPositiveBtnOnClickListener(){
            return new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(selectedStatus == null){
                        statuses.set(ref,TaskStatus.DONE);
                        someStatusChanged = true;
                        new DBHelper(context).setStatus(itemTitles.get(ref), STATUS_DONE);
                    } else{
                        statuses.set(ref, selectedStatus);
                        switch (selectedStatus){
                            case DONE:
                                someStatusChanged = true;
                                new DBHelper(context).setStatus(itemTitles.get(ref),STATUS_DONE);
                                break;
                            case IN_PROCESS:
                                someStatusChanged = true;
                                new DBHelper(context).setStatus(itemTitles.get(ref), STATUS_IN_PROCESS);
                                break;
                            case NOT_COMPLETED:
                                someStatusChanged = true;
                                new DBHelper(context).setStatus(itemTitles.get(ref), STATUS_NOT_COMPLETED);
                                break;
                        }
                    }
                    selectedStatus = null;
                    ((FullTaskActivity)context).initStatisticPanel();
                    notifyDataSetChanged();
                }
            };
        }
    }

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
            itemView = inflater.inflate(R.layout.item_review,parent,false);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder)itemView.getTag();
        }
        holder.refreshView(position);
        return itemView;
    }

    public boolean isSomeStatusChanged() {
        return someStatusChanged;
    }
}
