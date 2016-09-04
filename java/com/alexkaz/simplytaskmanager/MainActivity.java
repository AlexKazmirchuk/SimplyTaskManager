package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_developer_board_white_24dp);
        getSupportActionBar().setTitle("  All tasks");
        listOftasks = new DBHelper(this).getListOfTasks();
        initList();
        initStatisticPanel();
        initActionButton();
    }

    private void initActionButton() {
        actionButton = (FloatingActionButton) findViewById(R.id.actionBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewTaskActivity.class);
                startActivityForResult(intent,3);
            }
        });
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
        registerForContextMenu(mainListView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        listOftasks = new DBHelper(this).getListOfTasks();
        initList();
        initStatisticPanel();
    }

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_item_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.editItem:
                Log.d("listPositionItem",info.position + " edited");
                ////////////////
                //TODO реалізувати виклик актівіті редагування
                Intent intent = new Intent(this,AddNewTaskActivity.class);
                intent.putExtra(DBHelper.TASK_TITLE,((TaskObject) adapter.getItem(info.position)).getTaskTitle());
                startActivityForResult(intent,2);
                ////////////////
                return true;
            case R.id.deleteItem:
                Log.d("listPositionItem",info.position + " deleted");
                ////////////////
                //TODO реалізувати видалення елемента з бази і з списку
                String taskTitle = ((TaskObject) adapter.getItem(info.position)).getTaskTitle();
                new DBHelper(this).removeTask(taskTitle);
                adapter.removeItem(info.position);
                adapter.notifyDataSetChanged();
                ////////////////
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        return super.onOptionsItemSelected(item);
    }
}
