package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexkaz.simplytaskmanager.adapters.MainTaskAdapter;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private MainTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_developer_board_white_24dp);

        initList();
    }

    private void initList() {
        mainListView = (ListView) findViewById(R.id.mainListView);
        mainListView.setDivider(null);
        adapter = new MainTaskAdapter(this,new DBHelper(this).getListOfTasks());
        mainListView.setAdapter(adapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskTitle = ((TaskObject)adapter.getItem(position)).getTaskTitle();
                Intent intent = new Intent(MainActivity.this,FullTaskActivity.class);
                intent.putExtra(DBHelper.TASK_TITLE,taskTitle);
                startActivity(intent);
                Log.d("titleLog",taskTitle);
            }
        });
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
}
