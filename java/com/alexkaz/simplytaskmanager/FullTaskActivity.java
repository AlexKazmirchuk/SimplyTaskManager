package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.adapters.RotateAnimAdapter;
import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.PieChartView;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.adapters.TaskViewerAdapter;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

public class FullTaskActivity extends AppCompatActivity {

    public static final String SAVED_TASK_TITLE = "savedTaskTitle";
    public static final int ADD_NEW_TASK_ACTIVITY_REQUEST_CODE = 1;
    private ImageView reviewIcon;
    private TextView txtViewTaskTitle;
    private ListView reviewTaskListView;
    private TaskObject taskObject;
    String taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent != null){
            taskTitle = intent.getStringExtra(DBHelper.TASK_TITLE);
        }
        if (savedInstanceState!=null){
            taskTitle = savedInstanceState.getString(SAVED_TASK_TITLE);
        }
        DBHelper helper = new DBHelper(this);
        taskObject = helper.getTask(taskTitle);

        initComp();
        initListView();
        initStatisticPanel();
    }

    private void initComp() {
        reviewIcon = (ImageView)findViewById(R.id.reviewIcon);
        txtViewTaskTitle = (TextView)findViewById(R.id.txtViewTaskTitle);
        switch (taskObject.getIcon()){
            case TaskObject.WORK_ICON:
                reviewIcon.setImageResource(R.drawable.work_icon);
                break;
            case TaskObject.HOME_ICON:
                reviewIcon.setImageResource(R.drawable.home_icon);
                break;
            case TaskObject.FUN_ICON:
                reviewIcon.setImageResource(R.drawable.fun_icon);
                break;
            case TaskObject.OTHER_ICON:
                reviewIcon.setImageResource(R.drawable.other_icon);
                break;
        }
        txtViewTaskTitle.setText(taskObject.getTaskTitle());
    }

    private void initListView() {
        reviewTaskListView = (ListView) findViewById(R.id.reviewTaskListView);
        reviewTaskListView.setDivider(null);

        TaskViewerAdapter adapter = new TaskViewerAdapter(this,taskObject);
        RotateAnimAdapter rotateAnimAdapter = new RotateAnimAdapter(adapter);
        rotateAnimAdapter.setAbsListView(reviewTaskListView);
        reviewTaskListView.setAdapter(rotateAnimAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        if (item.getItemId() == R.id.editMenuItem){
            Intent intent = new Intent(this,AddNewTaskActivity.class);
            intent.putExtra(DBHelper.TASK_TITLE,taskObject.getTaskTitle());
            startActivityForResult(intent, ADD_NEW_TASK_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_icon,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){
            return;
        }
        String newTaskTitle = data.getStringExtra(DBHelper.TASK_TITLE);
        DBHelper helper = new DBHelper(this);
        taskObject = helper.getTask(newTaskTitle);
        initComp();
        initListView();
        initStatisticPanel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_TASK_TITLE,taskObject.getTaskTitle());
    }

    public void initStatisticPanel() {
        int notCompletedTaskItemCount = 0, inProcessTaskItemCount = 0, doneTaskItemCount = 0;
        int notCompletedTaskItemInterest = 0,  inProcessTaskItemInterest = 0, doneTaskItemInterest = 0;
        int amountOfTaskItems = 0;
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
//        notCompletedTaskItemCount = 300;
//        inProcessTaskItemCount = 23;
//        doneTaskItemCount = 150;

        amountOfTaskItems = notCompletedTaskItemCount + inProcessTaskItemCount + doneTaskItemCount;
        notCompletedTaskItemInterest =  Math.round((((float) notCompletedTaskItemCount)/((float) amountOfTaskItems))*100);
        inProcessTaskItemInterest = Math.round(((float)inProcessTaskItemCount/(float)amountOfTaskItems)*100);
        doneTaskItemInterest = Math.round(((float)doneTaskItemCount/(float)amountOfTaskItems)*100);

        String done = String.format(getString(R.string.done_count),doneTaskItemInterest,doneTaskItemCount);
        String inProcess = String.format(getString(R.string.in_process_count),inProcessTaskItemInterest,inProcessTaskItemCount);
        String notCompleted = String.format(getString(R.string.not_completed_count),notCompletedTaskItemInterest,notCompletedTaskItemCount);

        ((TextView)findViewById(R.id.doneTxtViewFullTask)).setText(done);
        ((TextView)findViewById(R.id.inProcessTxtViewFullTask)).setText(inProcess);
        ((TextView)findViewById(R.id.notCompletedTxtViewFullTask)).setText(notCompleted);
        ((PieChartView)findViewById(R.id.pieChartViewFullTask)).setValues(notCompletedTaskItemCount,inProcessTaskItemCount,doneTaskItemCount);
        ((PieChartView)findViewById(R.id.pieChartViewFullTask)).invalidate();
    }
}
