package com.alexkaz.simplytaskmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.adapters.ItemTaskAdapter;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewTaskActivity extends AppCompatActivity {

    private int[] images;
    private ArrayList<Map<String, Object>> data;
    private String[] from;
    private int[] to;
    private Spinner spinner;
    private SimpleAdapter simpleAdapter;

    private ItemTaskAdapter itemTaskAdapter;
    private ListView itemTaskList;

    private LinearLayout addNewTaskButton;

    private Button addButton;
    private EditText editTextTitle;
    private TaskObject intentTaskObject;
    private String intentTaskTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        intentTaskTitle = intent.getStringExtra(DBHelper.TASK_TITLE);

        initSpinner();
        ////////////////////
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        addButton = (Button) findViewById(R.id.addButton);
        ////////////////////
        initListView();


        ///////////////////////////
        ///////////////////////////
        if(intentTaskTitle != null){
            intentTaskObject = new DBHelper(this).getTask(intentTaskTitle);
            switch (intentTaskObject.getIcon()){
                case "work_icon":
                    spinner.setSelection(0);
                    break;
                case "home_icon":
                    spinner.setSelection(1);
                    break;
                case "fun_icon":
                    spinner.setSelection(2);
                    break;
                case "other_icon":
                    spinner.setSelection(3);
                    break;
            }
            editTextTitle.setText(intentTaskObject.getTaskTitle());
            itemTaskAdapter = new ItemTaskAdapter(this,intentTaskObject.getItemTitles());
            addButton.setText("SAVE");
        } else {
            spinner.setSelection(0);
            itemTaskAdapter = new ItemTaskAdapter(this);
        }
        itemTaskList.setAdapter(itemTaskAdapter);
        initAddButton();
    }

    private void initSpinner(){
        images = new int[] {R.drawable.work_icon,R.drawable.home_icon,R.drawable.fun_icon,R.drawable.other_icon};
        data = new ArrayList<Map<String, Object>>();
        from = new String[] {"image_bg"};
        to = new int[] {R.id.icon3};
        Map<String, Object> m;
        for (int i = 0; i < 4; i++) {
            m = new HashMap<String, Object>();
            m.put("image_bg", images[i]);
            data.add(m);
        }
        simpleAdapter =  new SimpleAdapter(this,data,R.layout.spinner_row,from,to);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(simpleAdapter);

        //////////////////




        //////////////////
    }

    private void initListView() {
        /////////////// пізніше замінити на дані з бази
        ArrayList<String> data = new ArrayList<>();


        //////////////
//        itemTaskAdapter = new ItemTaskAdapter(this, data); //  замінити data на об'єкт сформований з бази
         //  замінити data на об'єкт сформований з бази

        itemTaskList = (ListView) findViewById(R.id.itemTaskList);
        itemTaskList.setDivider(null);
        View view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_new_task_handler_layout, null, false);
        addNewTaskButton = (LinearLayout) view.findViewById(R.id.addNewTask);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemTaskAdapter.addNewItem(); // переписати
                itemTaskAdapter.notifyDataSetChanged();
            }
        });
        itemTaskList.addFooterView(view);



    }

    private void initAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // в цьому блоці реалізувати занесення даних з текстових полів в базу даних, з спінера, поля заголовку
                String buf ="";
                for (String item : itemTaskAdapter.getItems()) {
                    buf = buf + " " + item;
                }
                Log.d("items",buf);

                //////////////////
                String icon = "";
                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        icon = "work_icon";
                        break;
                    case 1:
                        icon = "home_icon";
                        break;
                    case 2:
                        icon = "fun_icon";
                        break;
                    case 3:
                        icon = "other_icon";
                        break;
                }
                String taskTitle = editTextTitle.getText().toString();
                ArrayList<String> itemTitles = itemTaskAdapter.getItems();
                /////////////
                if (taskTitle.equals("")){
                    showAlertMassage("Please set task name");
                    return;
                }
                for (String item : itemTitles) {
                    if (item.equals("")){
                        showAlertMassage("Please set or delete all empty fields");
                        return;
                    }
                }

                /////////////
                ArrayList<TaskStatus> statuses = new ArrayList<TaskStatus>();
                for (int i = 0; i < itemTitles.size(); i++) {
                    statuses.add(TaskStatus.NOT_COMPLITED);
                }
                TaskObject taskObject = new TaskObject(icon,taskTitle,itemTitles,statuses);
                //тут записуємо в базу ...

                DBHelper helper = new DBHelper(AddNewTaskActivity.this);
                /////////////////////////////
                if (intentTaskTitle != null){
                    helper.setTask(intentTaskObject.getTaskTitle(),taskObject);
                    Intent callbackIntent = new Intent();
                    callbackIntent.putExtra(DBHelper.TASK_TITLE, taskTitle);
                    setResult(1,callbackIntent);
                } else {
                    helper.addTask(taskObject);
                }

                /////////////////////////////
                finish();
                /////////////////
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertMassage(String massage){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddNewTaskActivity.this);
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage(massage);
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.create().show();
    }
}
