package com.alexkaz.simplytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_developer_board_white_24dp);
    }

    public void startSecondActivity(View view) {
        Intent intent = new Intent(this,FullTaskActivity.class);
        startActivity(intent);
    }

    public void startThirdActivity(View view) {
        Intent intent = new Intent(this,AddNewTaskActivity.class);
        startActivity(intent);
    }
}
