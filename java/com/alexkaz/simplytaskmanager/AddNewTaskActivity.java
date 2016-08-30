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

import com.alexkaz.simplytaskmanager.uicomp.ItemTaskAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComp();



        ArrayList<String> data = new ArrayList<>();
        data.add("one");
        data.add("two");
//        data.add("three");
//        data.add("four");
//        data.add("five");
//        data.add("six");
        itemTaskAdapter = new ItemTaskAdapter(this, data);
        itemTaskList = (ListView) findViewById(R.id.itemTaskList);
        itemTaskList.setDivider(null);
        View view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_new_task_handler_layout, null, false);
        addNewTaskButton = (LinearLayout) view.findViewById(R.id.addNewTask);
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemTaskAdapter.addNewItem("seven");

                String buf ="";
                for (String item : itemTaskAdapter.items) {
                    buf = buf + " " + item;
                }
                Log.d("items",buf);


                itemTaskAdapter.notifyDataSetChanged();
            }
        });
        itemTaskList.addFooterView(view);
        itemTaskList.setAdapter(itemTaskAdapter);



        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void initComp(){
        images = new int[] {R.drawable.fun_icon,R.drawable.home_icon,R.drawable.other_icon,R.drawable.work_icon};
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
    }
}
