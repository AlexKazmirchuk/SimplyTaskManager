package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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
    String taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
//        taskTitle = "щось там зробити";
        if (intent != null){
            taskTitle = intent.getStringExtra(DBHelper.TASK_TITLE);
        }
        if (savedInstanceState!=null){
            taskTitle = savedInstanceState.getString("savedTaskTitle");
        }
        DBHelper helper = new DBHelper(this);
        taskObject = helper.getTask(taskTitle);

        initComp();
        initListView();
    }

    private void initComp() {
        reviewIcon = (ImageView)findViewById(R.id.reviewIcon);
        txtViewTaskTitle = (TextView)findViewById(R.id.txtViewTaskTitle);




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
        if (item.getItemId() == R.id.editMenuItem){
            Intent intent = new Intent(this,AddNewTaskActivity.class);
            intent.putExtra(DBHelper.TASK_TITLE,taskObject.getTaskTitle());
//            startActivity(intent);
            startActivityForResult(intent,1);
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

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("savedTaskTitle",taskObject.getTaskTitle());
    }
}
