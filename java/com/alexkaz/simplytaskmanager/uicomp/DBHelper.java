package com.alexkaz.simplytaskmanager.uicomp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "task_db";

    private static final String TABLE_TASKS = "tasks";
    private static final String TASK_ID = "task_id";
    public static final String TASK_TITLE = "task_title";
    private static final String ICON = "icon";

    private static final String TABLE_TASK_ITEMS = "task_items";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_TITLE = "item_title";
    private static final String STATUS = "status";

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " +  TABLE_TASKS + "(" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TASK_TITLE +" TEXT NOT NULL," +
            ICON + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_TASK_ITEMS = "CREATE TABLE " + TABLE_TASK_ITEMS + "(" +
            ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ITEM_TITLE + " TEXT NOT NULL," +
            TASK_ID + " INTEGER NOT NULL," +
            STATUS + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + TASK_ID + ") " +
            "REFERENCES " + TABLE_TASKS + "(" + TASK_ID + "));";

    private static final String DELETE_TASK_TITLE_FROM_TASK_ID = "DELETE FROM " + TABLE_TASKS + " WHERE " + TASK_ID + "=";
    private static final String QUERY_ALL_TASK_TITLES = "SELECT " + TASK_TITLE + " FROM " + TABLE_TASKS + ";";
    private static final String QUERY_TASK_ID_FROM_TITLE = "SELECT " + TASK_ID + " FROM " + TABLE_TASKS + " WHERE " + TASK_TITLE + "=?;";
    private static final String QUERY_TASK_ID_AND_ICON_FROM_TITLE = "SELECT " + TASK_ID + ", " + ICON
            + " FROM " + TABLE_TASKS
            + " WHERE " + TASK_TITLE + "=?;";

    private static final String DELETE_TASK_ITEMS_FROM_TASK_ID = "DELETE FROM " + TABLE_TASK_ITEMS + " WHERE " + TASK_ID + "=";
    private static final String QUERY_ITEM_TITLE_AND_STATUS_FROM_ID = "SELECT " + ITEM_TITLE + ", " + STATUS
            + " FROM " + TABLE_TASK_ITEMS
            + " WHERE " + TASK_ID + "=?;";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_TASK_ITEMS);
        db.execSQL("PRAGMA foreign_keys=on;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public TaskObject getTask(String taskTitle){
        Cursor taskTitleCursor = this.getReadableDatabase().rawQuery(QUERY_TASK_ID_AND_ICON_FROM_TITLE,new String[]{taskTitle});
        taskTitleCursor.moveToNext();
        int taskID = taskTitleCursor.getInt(taskTitleCursor.getColumnIndex(DBHelper.TASK_ID));
        String icon = taskTitleCursor.getString(taskTitleCursor.getColumnIndex(DBHelper.ICON));

        taskTitleCursor = this.getReadableDatabase().rawQuery(QUERY_ITEM_TITLE_AND_STATUS_FROM_ID,new String[]{taskID + ""});
        ArrayList<String> itemTitles = new ArrayList<>();
        ArrayList<TaskStatus> statuses = new ArrayList<>();
        int statusBuff;
        while(taskTitleCursor.moveToNext()){
            itemTitles.add(taskTitleCursor.getString(taskTitleCursor.getColumnIndex(DBHelper.ITEM_TITLE)));
            statusBuff = taskTitleCursor.getInt(taskTitleCursor.getColumnIndex(DBHelper.STATUS));
            switch (statusBuff){
                case 0:
                    statuses.add(TaskStatus.NOT_COMPLETED);
                    break;
                case 1:
                    statuses.add(TaskStatus.IN_PROCESS);
                    break;
                case 2:
                    statuses.add(TaskStatus.DONE);
                    break;
            }
        }
        taskTitleCursor.close();
        return new TaskObject(icon,taskTitle,itemTitles,statuses);
    }

    public void addTask(TaskObject taskObject){
        ContentValues taskValues = new ContentValues();
        taskValues.put(TASK_TITLE,taskObject.getTaskTitle());
        taskValues.put(ICON,taskObject.getIcon());

        int taskID = (int) this.getWritableDatabase().insert(TABLE_TASKS,null,taskValues);

        ContentValues taskItemValues = new ContentValues();
        ArrayList<String> itemTitles = taskObject.getItemTitles();
        ArrayList<TaskStatus> statuses = taskObject.getStatuses();

        for (int i = 0; i < itemTitles.size(); i++) {
            taskItemValues.put(ITEM_TITLE,itemTitles.get(i));
            taskItemValues.put(TASK_ID,taskID);
            switch (statuses.get(i)){
                case NOT_COMPLETED:
                    taskItemValues.put(STATUS,TaskObject.STATUS_NOT_COMPLETED);
                    break;
                case IN_PROCESS:
                    taskItemValues.put(STATUS,TaskObject.STATUS_IN_PROCESS);
                    break;
                case DONE:
                    taskItemValues.put(STATUS,TaskObject.STATUS_DONE);
                    break;
            }
            this.getWritableDatabase().insert(TABLE_TASK_ITEMS,null,taskItemValues);
        }
    }

    public void removeTask(String taskTitle){
        String selectSQL =  QUERY_TASK_ID_FROM_TITLE;
        Cursor cursor = this.getReadableDatabase().rawQuery(selectSQL,new String[]{taskTitle});

        if (cursor.moveToNext()){
            int taskID = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_ID));
            this.getWritableDatabase().execSQL(DELETE_TASK_ITEMS_FROM_TASK_ID + taskID);
            this.getWritableDatabase().execSQL(DELETE_TASK_TITLE_FROM_TASK_ID + taskID);
        }
        cursor.close();
    }

    public void setTask(String oldTaskTitle, TaskObject taskObject){
        removeTask(oldTaskTitle);
        addTask(taskObject);
    }

    public ArrayList<TaskObject> getListOfTasks(){

        ArrayList<TaskObject> taskObjects = new ArrayList<>();
        String selectSQL = QUERY_ALL_TASK_TITLES;
        Cursor cursor = this.getReadableDatabase().rawQuery(selectSQL,null);
        while(cursor.moveToNext()){
            String taskTitle = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE));
            taskObjects.add(getTask(taskTitle));
        }
        cursor.close();
        return taskObjects;
    }

    public void setStatus(String taskItem, int newStatus){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS,newStatus);
        getWritableDatabase().update(TABLE_TASK_ITEMS,contentValues,ITEM_TITLE + "=?",new String[]{taskItem});
    }
}
