package com.alexkaz.simplytaskmanager;

import android.content.Context;
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
import com.alexkaz.simplytaskmanager.uicomp.ItemTaskAdapter;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initSpinner();
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        initListView();
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
        spinner.setSelection(0);
    }

    private void initListView() {
        /////////////// пізніше замінити на дані з бази
        ArrayList<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        //////////////
//        itemTaskAdapter = new ItemTaskAdapter(this, data); //  замінити data на об'єкт сформований з бази
        itemTaskAdapter = new ItemTaskAdapter(this); //  замінити data на об'єкт сформований з бази

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
        itemTaskList.setAdapter(itemTaskAdapter);

        addButton = (Button) findViewById(R.id.addButton);
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
                ArrayList<TaskStatus> statuses = new ArrayList<TaskStatus>();
                for (int i = 0; i < itemTitles.size(); i++) {
                    statuses.add(TaskStatus.NOT_COMPLITED);
                }
                TaskObject taskObject = new TaskObject(icon,taskTitle,itemTitles,statuses);
                //тут записуємо в базу ...
                DBHelper helper = new DBHelper(AddNewTaskActivity.this);
                helper.addTask(taskObject);
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
}
