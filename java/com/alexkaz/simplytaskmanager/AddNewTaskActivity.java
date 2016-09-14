package com.alexkaz.simplytaskmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.uicomp.DBHelper;
import com.alexkaz.simplytaskmanager.adapters.ItemTaskAdapter;
import com.alexkaz.simplytaskmanager.uicomp.TaskObject;
import com.alexkaz.simplytaskmanager.uicomp.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewTaskActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 1324;
    private static final String TITLE_TEXT_CHAR_LIMIT = "/100";
    private Spinner spinner;

    private ItemTaskAdapter itemTaskAdapter;
    private ListView itemTaskList;

    private Button addButton;
    private EditText editTextTitle;
    private TextView titleCharCounter;
    private TaskObject intentTaskObject;
    private String intentTaskTitle;
    private boolean taskChanged = false;

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
        initComp();

        ////////////////////
        initListView();


        ///////////////////////////
        ///////////////////////////
        if(intentTaskTitle != null){
            getSupportActionBar().setTitle(R.string.edit_task_activity_label);
            intentTaskObject = new DBHelper(this).getTask(intentTaskTitle);
            switch (intentTaskObject.getIcon()){
                case TaskObject.WORK_ICON:
                    spinner.setSelection(0);
                    break;
                case TaskObject.HOME_ICON:
                    spinner.setSelection(1);
                    break;
                case TaskObject.FUN_ICON:
                    spinner.setSelection(2);
                    break;
                case TaskObject.OTHER_ICON:
                    spinner.setSelection(3);
                    break;
            }
            editTextTitle.setText(intentTaskObject.getTaskTitle());
            String charCounterValue = intentTaskObject.getTaskTitle().length() + TITLE_TEXT_CHAR_LIMIT;
            titleCharCounter.setText(charCounterValue);
            itemTaskAdapter = new ItemTaskAdapter(this, (ArrayList<String>) intentTaskObject.getItemTitles().clone());
            addButton.setText(getString(R.string.edit_button_title));
        } else {
            spinner.setSelection(0);
            itemTaskAdapter = new ItemTaskAdapter(this);
        }
        itemTaskList.setAdapter(itemTaskAdapter);
        initAddButton();
    }

    private void initSpinner(){
        int[] images = new int[]{R.drawable.work_icon, R.drawable.home_icon, R.drawable.fun_icon, R.drawable.other_icon};
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        String[] from = new String[]{"image_bg"};
        int[] to = new int[]{R.id.spinnerIcon};
        Map<String, Object> m;
        for (int i = 0; i < 4; i++) {
            m = new HashMap<>();
            m.put("image_bg", images[i]);
            data.add(m);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.spinner_row, from, to);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(simpleAdapter);
    }

    private void initComp(){
        addButton = (Button) findViewById(R.id.addButton);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        titleCharCounter = (TextView) findViewById(R.id.titleCharCounter);

        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String charCounterValue = s.length() + TITLE_TEXT_CHAR_LIMIT;
                titleCharCounter.setText(charCounterValue);
            }
        });
    }

    private void initListView() {
        itemTaskList = (ListView) findViewById(R.id.itemTaskList);
        itemTaskList.setDivider(null);
        View view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_new_task_handler_layout, null, false);
        LinearLayout addNewTaskButton = (LinearLayout) view.findViewById(R.id.addNewTask);
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
                String icon = "";
                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        icon = TaskObject.WORK_ICON;
                        break;
                    case 1:
                        icon = TaskObject.HOME_ICON;
                        break;
                    case 2:
                        icon = TaskObject.FUN_ICON;
                        break;
                    case 3:
                        icon = TaskObject.OTHER_ICON;
                        break;
                }
                String taskTitle = editTextTitle.getText().toString();
                ArrayList<String> itemTitles = itemTaskAdapter.getItems();
                /////////////
                if (taskTitle.equals("")){
                    showAlertMassage(getString(R.string.alert_massage_when_task_title_empty));
                    return;
                }
                for (String item : itemTitles) {
                    if (item.equals("")){
                        showAlertMassage(getString(R.string.alert_massage_when_task_title_item_empty));
                        return;
                    }
                }

                ArrayList<TaskStatus> statuses = new ArrayList<>();
                if (itemTaskAdapter.isTaskItemRemoved()){
                    for (int i = 0; i < itemTitles.size(); i++) {
                        statuses.add(TaskStatus.NOT_COMPLETED);
                    }
                } else{
                    if (intentTaskTitle != null){
//                        statuses = intentTaskObject.getStatuses();

                        ///////////////////////////////////
                        if (itemTaskAdapter.getItemAddedCount() == 0){
                            statuses = intentTaskObject.getStatuses();
                        } else {
                            statuses = intentTaskObject.getStatuses();
                            for (int i = 0; i < itemTaskAdapter.getItemAddedCount(); i++) {
                                statuses.add(TaskStatus.NOT_COMPLETED);
                            }
                        }
                        ///////////////////////////////

                    } else {
                        statuses = new ArrayList<>();
                        for (int i = 0; i < itemTitles.size(); i++) {
                            statuses.add(TaskStatus.NOT_COMPLETED);
                        }
                    }
                }

                TaskObject taskObject = new TaskObject(icon,taskTitle,itemTitles,statuses);
                //тут записуємо в базу ...

                DBHelper helper = new DBHelper(AddNewTaskActivity.this);
                /////////////////////////////
                if (intentTaskTitle != null){
                    helper.setTask(intentTaskObject.getTaskTitle(),taskObject);
                    Intent callbackIntent = new Intent();
                    callbackIntent.putExtra(DBHelper.TASK_TITLE, taskTitle);
                    setResult(RESULT_OK,callbackIntent);
                } else {
                    setResult(RESULT_OK);
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
        dialogBuilder.setTitle(R.string.alert_massage_warning);
        dialogBuilder.setMessage(massage);
        dialogBuilder.setPositiveButton(getResources().getString(R.string.status_dialog_positive_button_text),null);
        dialogBuilder.create().show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(intentTaskObject != null){
            if(!(editTextTitle.getText().toString().equals(intentTaskObject.getTaskTitle()))){
                taskChanged = true;
            }
            if (!(itemTaskAdapter.getItems().equals(intentTaskObject.getItemTitles()))){
                taskChanged = true;
            }
        } else {
            if(!(editTextTitle.getText().toString().equals(""))){
                taskChanged = true;
            }
            if (itemTaskAdapter.getItems().size() != 0){
                taskChanged = true;
            }
        }

        if (taskChanged){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddNewTaskActivity.this);
            dialogBuilder.setTitle(R.string.alert_massage_warning);
            dialogBuilder.setMessage(getString(R.string.modified_data_alarm_massage));
            dialogBuilder.setPositiveButton(getString(R.string.positive_answer), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AddNewTaskActivity.this.finish();
                }
            });
            dialogBuilder.setNegativeButton(getString(R.string.negative_answer),null);
            dialogBuilder.create().show();
        } else {
            this.finish();
        }

        Log.d("textChanged",taskChanged + "");
    }
}
