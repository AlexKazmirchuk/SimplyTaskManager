package com.alexkaz.simplytaskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComp();
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
