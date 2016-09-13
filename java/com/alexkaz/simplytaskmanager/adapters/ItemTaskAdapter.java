package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

    private static final String COUNT_OF_RIGHT_INPUTED_CHARACTERS = "/250";
    private ArrayList<String> items;
    private LayoutInflater inflater;
    private boolean itemCountChanged = false;
    private int itemAddedCount = 0;

    public ItemTaskAdapter(Context context, ArrayList<String> items) {
        this.items = items;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ItemTaskAdapter(Context context){
        items = new ArrayList<>();
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

        final ViewHolder holder;

        if (itemView == null){
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.new_item_task_layout,parent,false);

            holder.textView = (TextView)itemView.findViewById(R.id.itemTxt);
            holder.txtCharCounter = (TextView)itemView.findViewById(R.id.txtCharCounter);
            holder.editText = (EditText) itemView.findViewById(R.id.editTextTaskItem);
            holder.imageButton = (ImageButton) itemView.findViewById(R.id.deleteBtn);

            itemView.setTag(holder);

        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        String itemPos = (position+1) + ".";
        holder.ref = position;
        holder.textView.setText(itemPos);
        holder.editText.setText(items.get(position));
        String charCounterValue = holder.editText.getText().toString().length() + COUNT_OF_RIGHT_INPUTED_CHARACTERS;
        holder.txtCharCounter.setText(charCounterValue);
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                items.set(holder.ref,s.toString());
                String charCounterValue = s.toString().length() + COUNT_OF_RIGHT_INPUTED_CHARACTERS;
                holder.txtCharCounter.setText(charCounterValue);
                Log.d("afterTextChangedLog",s.toString().length()+"");
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((items.size()-1) != position){
                    itemCountChanged = true;
                }
                items.remove(position);
                Log.d("position", position + "");
                ItemTaskAdapter.super.notifyDataSetChanged();
            }
        });
        return itemView;
    }

    private class ViewHolder {
        TextView textView;
        TextView txtCharCounter;
        EditText editText;
        ImageButton imageButton;
        int ref;
    }

    public void addNewItem(){
        items.add("");
        itemAddedCount++;
    }

    public ArrayList<String> getItems() {
        return items;
    }


    public boolean isItemCountChanged() {
        return itemCountChanged;
    }

    public int getItemAddedCount(){
        return itemAddedCount;
    }

}
