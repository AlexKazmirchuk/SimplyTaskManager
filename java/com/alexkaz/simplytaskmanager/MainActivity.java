package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.adapters.MainTaskAdapter;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.PieChartView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private MainTaskAdapter adapter;
    private ArrayList<TaskObject> listOftasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_developer_board_white_24dp);
        listOftasks = new DBHelper(this).getListOfTasks();
        initList();
        initStatisticPanel();
    }

    private void initList() {
        mainListView = (ListView) findViewById(R.id.mainListView);
        mainListView.setDivider(null);
        adapter = new MainTaskAdapter(this,listOftasks);
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskTitle = ((TaskObject)adapter.getItem(position)).getTaskTitle();
                Intent intent = new Intent(MainActivity.this,FullTaskActivity.class);
                intent.putExtra(DBHelper.TASK_TITLE,taskTitle);
//                startActivity(intent);
                startActivityForResult(intent,1);
                Log.d("titleLog",taskTitle);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        listOftasks = new DBHelper(this).getListOfTasks();
        initList();
        initStatisticPanel();
    }

    //    public void startSecondActivity(View view) {
//        Intent intent = new Intent(this,FullTaskActivity.class);
//        startActivity(intent);
//    }
//
//    public void startThirdActivity(View view) {
//        Intent intent = new Intent(this,AddNewTaskActivity.class);
//        startActivity(intent);
//    }
//
//    public void startFourthActivity(View view) {
//        Intent intent = new Intent(this,EditTaskActivity.class);
//        startActivity(intent);
//    }

    private void initStatisticPanel() {
        int notCompletedTaskItemCount = 0, inProcessTaskItemCount = 0, doneTaskItemCount = 0;
        int notCompletedTaskItemInterest = 0,  inProcessTaskItemInterest = 0, doneTaskItemInterest = 0;
        int amountOfTaskItems = 0;
        for (TaskObject taskObject : listOftasks) {
            for (TaskStatus taskStatus : taskObject.getStatuses()) {
                switch (taskStatus){
                    case NOT_COMPLITED:
                        notCompletedTaskItemCount++;
                        break;
                    case IN_PROCESS:
                        inProcessTaskItemCount++;
                        break;
                    case DONE:
                        doneTaskItemCount++;
                        break;
                }
            }
        }
//        notCompletedTaskItemCount = 300;
//        inProcessTaskItemCount = 23;
//        doneTaskItemCount = 150;

        amountOfTaskItems = notCompletedTaskItemCount + inProcessTaskItemCount + doneTaskItemCount;
        notCompletedTaskItemInterest =  Math.round((((float) notCompletedTaskItemCount)/((float) amountOfTaskItems))*100);
        inProcessTaskItemInterest = Math.round(((float)inProcessTaskItemCount/(float)amountOfTaskItems)*100);
        doneTaskItemInterest = Math.round(((float)doneTaskItemCount/(float)amountOfTaskItems)*100);

        ((TextView)findViewById(R.id.doneTxtView)).setText("Виконано - " + doneTaskItemInterest + "%(" + doneTaskItemCount + ")");
        ((TextView)findViewById(R.id.inProcessTxtView)).setText("В процесі - " + inProcessTaskItemInterest + "%(" + inProcessTaskItemCount + ")");
        ((TextView)findViewById(R.id.notCompletedTxtView)).setText("Не виконано - " + notCompletedTaskItemInterest + "%(" + notCompletedTaskItemCount + ")");
        ((PieChartView)findViewById(R.id.pieChart)).setValues(notCompletedTaskItemCount,inProcessTaskItemCount,doneTaskItemCount);
        ((PieChartView)findViewById(R.id.pieChart)).invalidate();

    }
}
