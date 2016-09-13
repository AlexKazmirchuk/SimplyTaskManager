package com.alexkaz.simplytaskmanager.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

    private static final String INPUT_CHARS_MAX_NUMBER = "/250";
    private ArrayList<String> items;
    private LayoutInflater inflater;
    private boolean taskItemRemoved = false;
    private int itemAddedCount = 0;

    private class ViewHolder {

        private TextView posTxtView;
        private TextView txtCharCounter;
        private EditText editText;
        private ImageButton delTaskItemBtn;
        private int ref;

        ViewHolder(View itemView){
            posTxtView = (TextView)itemView.findViewById(R.id.itemTxt);
            txtCharCounter = (TextView)itemView.findViewById(R.id.txtCharCounter);
            editText = (EditText) itemView.findViewById(R.id.editTextTaskItem);
            delTaskItemBtn = (ImageButton) itemView.findViewById(R.id.deleteBtn);
        }

        void refreshView(int position){
            ref = position;

            String itemPos = (position+1) + ".";
            posTxtView.setText(itemPos);

            editText.setText(items.get(position));

            String charCounterValue = editText.getText().toString().length() + INPUT_CHARS_MAX_NUMBER;
            txtCharCounter.setText(charCounterValue);

            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    items.set(ref,s.toString());
                    String charCounterValue = s.toString().length() + INPUT_CHARS_MAX_NUMBER;
                    txtCharCounter.setText(charCounterValue);
                }
            });

            delTaskItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((items.size() - 1) != ViewHolder.this.ref){
                        taskItemRemoved = true;
                    }
                    items.remove(ref);
                    ItemTaskAdapter.super.notifyDataSetChanged();
                }
            });
        }
    }

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
            itemView = inflater.inflate(R.layout.new_item_task_layout,parent,false);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }
        holder.refreshView(position);
        return itemView;
    }

    public void addNewItem(){
        items.add("");
        itemAddedCount++;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public boolean isTaskItemRemoved() {
        return taskItemRemoved;
    }

    public int getItemAddedCount(){
        return itemAddedCount;
    }

}
