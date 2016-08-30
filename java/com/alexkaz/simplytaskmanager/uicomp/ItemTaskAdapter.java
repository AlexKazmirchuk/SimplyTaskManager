package com.alexkaz.simplytaskmanager.uicomp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alexkaz.simplytaskmanager.R;

import java.util.ArrayList;

public class ItemTaskAdapter extends BaseAdapter {

    public ArrayList<String> items;

    private Context context;
    private LayoutInflater inflater;

    public ItemTaskAdapter(Context context, ArrayList<String> items) {
        this.items = items;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        View view = itemView;
        view = inflater.inflate(R.layout.new_item_task_layout,parent,false);
        EditText editText = (EditText) view.findViewById(R.id.editTextTaskItem);
        editText.setText(items.get(position));
        ((TextView)view.findViewById(R.id.itemTxt)).setText((position+1) + ".");
        ImageButton button = (ImageButton) view.findViewById(R.id.deleteBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                Log.d("position", position + "");
                ItemTaskAdapter.super.notifyDataSetChanged();
            }
        });



        return view;
    }

    public void addNewItem(String itemName){
        items.add(itemName);
    }
}
