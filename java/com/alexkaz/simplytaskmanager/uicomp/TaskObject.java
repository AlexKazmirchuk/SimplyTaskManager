package com.alexkaz.simplytaskmanager.uicomp;

import java.util.ArrayList;

public class TaskObject {

    public static final String WORK_ICON = "work_icon";
    public static final String HOME_ICON = "home_icon";
    public static final String FUN_ICON = "fun_icon";
    public static final String OTHER_ICON = "other_icon";

    public static final int STATUS_DONE = 2;
    public static final int STATUS_IN_PROCESS = 1;
    public static final int STATUS_NOT_COMPLETED = 0;

    private int taskID;
    private String icon;
    private String taskTitle;
    private ArrayList<String> itemTitles;
    private ArrayList<TaskStatus> statuses;

    public TaskObject(int taskID, String icon, String taskTitle, ArrayList<String> itemTitles, ArrayList<TaskStatus> statuses) {
        this.taskID = taskID;
        this.icon = icon;
        this.taskTitle = taskTitle;
        this.itemTitles = itemTitles;
        this.statuses = statuses;
    }

    public TaskObject(String icon, String taskTitle, ArrayList<String> itemTitles, ArrayList<TaskStatus> statuses) {
        this.icon = icon;
        this.taskTitle = taskTitle;
        this.itemTitles = itemTitles;
        this.statuses = statuses;
    }

    public int getTaskID() {
        return taskID;
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
