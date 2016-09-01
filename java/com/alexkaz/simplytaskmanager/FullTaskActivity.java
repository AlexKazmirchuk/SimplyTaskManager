package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.adapters.TaskViewerAdapter;

public class FullTaskActivity extends AppCompatActivity {

    private ImageView reviewIcon;
    private TextView txtViewTaskTitle;
    private ListView reviewTaskListView;
    private TaskObject taskObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComp();
        initListView();
    }

    private void initComp() {
        reviewIcon = (ImageView)findViewById(R.id.reviewIcon);
        txtViewTaskTitle = (TextView)findViewById(R.id.txtViewTaskTitle);
        Intent intent = getIntent();
        String taskTitle = "щось там зробити";
        if (intent != null){
            taskTitle = intent.getStringExtra(DBHelper.TASK_TITLE);
        }
        DBHelper helper = new DBHelper(this);
        taskObject = helper.getTask(taskTitle);

        switch (taskObject.getIcon()){
            case "work_icon":
                reviewIcon.setImageResource(R.drawable.work_icon);
                break;
            case "home_icon":
                reviewIcon.setImageResource(R.drawable.home_icon);
                break;
            case "fun_icon":
                reviewIcon.setImageResource(R.drawable.fun_icon);
                break;
            case "other_icon":
                reviewIcon.setImageResource(R.drawable.other_icon);
                break;
        }
        txtViewTaskTitle.setText(taskObject.getTaskTitle());
    }

    private void initListView() {
        reviewTaskListView = (ListView) findViewById(R.id.reviewTaskListView);
        reviewTaskListView.setDivider(null);

        TaskViewerAdapter adapter = new TaskViewerAdapter(this,taskObject);
        reviewTaskListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
