package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.alexkaz.simplytaskmanager.adapters.MainTaskAdapter;
import com.alexkaz.simplytaskmanager.adapters.RotateAnimAdapter;
import com.alexkaz.simplytaskmanager.receivers.NotificationReceiver;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.PieChartView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;
import java.util.ArrayList;

import static android.support.v4.app.ActivityOptionsCompat.*;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private MainTaskAdapter adapter;
    private ArrayList<TaskObject> listOftasks;
    private TextView hintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComp();
        initList();
        showHint();
        initStatisticPanel();
        initActionButton();
    }

    private void initComp(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_developer_board_white_36dp);
            getSupportActionBar().setTitle(R.string.main_activity_label);
        }
        hintTextView = (TextView) findViewById(R.id.hintTextView);
        listOftasks = new DBHelper(this).getListOfTasks();
    }

    private void showHint() {
        if (adapter.getCount() == 0){
            hintTextView.setVisibility(View.VISIBLE);
        }
    }

    private void initActionButton() {
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.actionBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewTaskActivity.class);
                startActivityForResult(intent,AddNewTaskActivity.REQUEST_CODE);
            }
        });
    }

    private void initList() {
        mainListView = (ListView) findViewById(R.id.mainListView);
        mainListView.setDivider(null);

        adapter = new MainTaskAdapter(this,listOftasks);
        RotateAnimAdapter rotateAnimAdapter = new RotateAnimAdapter(adapter);
        rotateAnimAdapter.setAbsListView(mainListView);
        mainListView.setAdapter(rotateAnimAdapter);

        mainListView.setOnItemClickListener(initOnItemClickListenerForMainList());
        registerForContextMenu(mainListView);
    }

    private AdapterView.OnItemClickListener initOnItemClickListenerForMainList(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int taskID = ((TaskObject)adapter.getItem(position)).getTaskID();
                Intent intent = new Intent(MainActivity.this,FullTaskActivity.class);
                intent.putExtra(DBHelper.TASK_ID,taskID);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptionsCompat options = makeSceneTransitionAnimation(MainActivity.this,
                            new Pair<>(view.findViewById(R.id.mainItemIcon),getString(R.string.transition_icon_image)),
                            new Pair<>(view.findViewById(R.id.mainItemTitle),getString(R.string.transition_title_name)));
                    ActivityCompat.startActivityForResult(MainActivity.this, intent, FullTaskActivity.REQUEST_CODE, options.toBundle());
                } else {
                    startActivityForResult(intent,FullTaskActivity.REQUEST_CODE);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AddNewTaskActivity.REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    hintTextView.setVisibility(View.INVISIBLE);
                    listOftasks = new DBHelper(this).getListOfTasks();
                    initList();
                    showHint();
                    initStatisticPanel();
                }
                break;
            case FullTaskActivity.REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    hintTextView.setVisibility(View.INVISIBLE);
                    listOftasks = new DBHelper(this).getListOfTasks();
                    initList();
                    showHint();
                    initStatisticPanel();
                }
                break;
        }
    }

    private void initStatisticPanel() {
        int notCompletedTaskItemCount = 0, inProcessTaskItemCount = 0, doneTaskItemCount = 0;
        int notCompletedTaskItemInterest,  inProcessTaskItemInterest, doneTaskItemInterest;
        int amountOfTaskItems;
        for (TaskObject taskObject : listOftasks) {
            for (TaskStatus taskStatus : taskObject.getStatuses()) {
                switch (taskStatus){
                    case NOT_COMPLETED:
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

        amountOfTaskItems = notCompletedTaskItemCount + inProcessTaskItemCount + doneTaskItemCount;
        notCompletedTaskItemInterest =  Math.round((((float) notCompletedTaskItemCount)/((float) amountOfTaskItems))*100);
        inProcessTaskItemInterest = Math.round(((float)inProcessTaskItemCount/(float)amountOfTaskItems)*100);
        doneTaskItemInterest = Math.round(((float)doneTaskItemCount/(float)amountOfTaskItems)*100);

        String done = String.format(getString(R.string.done_count),doneTaskItemInterest,doneTaskItemCount);
        String inProcess = String.format(getString(R.string.in_process_count),inProcessTaskItemInterest,inProcessTaskItemCount);
        String notCompleted = String.format(getString(R.string.not_completed_count),notCompletedTaskItemInterest,notCompletedTaskItemCount);

        ((TextView)findViewById(R.id.doneTxtView)).setText(done);
        ((TextView)findViewById(R.id.inProcessTxtView)).setText(inProcess);
        ((TextView)findViewById(R.id.notCompletedTxtView)).setText(notCompleted);

        ((PieChartView)findViewById(R.id.pieChart)).setValues(notCompletedTaskItemCount,inProcessTaskItemCount,doneTaskItemCount);
        findViewById(R.id.pieChart).invalidate();
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
                Intent intent = new Intent(this,AddNewTaskActivity.class);
                intent.putExtra(DBHelper.TASK_ID,((TaskObject) adapter.getItem(info.position)).getTaskID());
                startActivityForResult(intent,AddNewTaskActivity.REQUEST_CODE);
                return true;
            case R.id.deleteItem:
                int taskID = ((TaskObject) adapter.getItem(info.position)).getTaskID();
                new DBHelper(this).removeTaskFromID(taskID);
                adapter.removeItem(info.position);
                adapter.notifyDataSetChanged();
                hintTextView.setVisibility(View.INVISIBLE);
                showHint();
                initStatisticPanel();
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
        if(item.getItemId() == R.id.aboutItem){
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mainListView.invalidate();
    }
}
