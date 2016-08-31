package com.alexkaz.simplytaskmanager.uicomp;

import java.util.ArrayList;

public class TaskObject {
    private String icon;
    private String taskTitle;
    private ArrayList<String> itemTitles;
    private ArrayList<TaskStatus> statuses;

    public TaskObject(String icon, String taskTitle, ArrayList<String> itemTitles, ArrayList<TaskStatus> statuses) {
        this.icon = icon;
        this.taskTitle = taskTitle;
        this.itemTitles = itemTitles;
        this.statuses = statuses;
    }

    public String getIcon() {
        return icon;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public ArrayList<String> getItemTitles() {
        return itemTitles;
    }

    public ArrayList<TaskStatus> getStatuses() {
        return statuses;
    }
}
