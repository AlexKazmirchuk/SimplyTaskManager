package com.alexkaz.simplytaskmanager.uicomp;

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

    private ArrayList<String> items;
//    public ArrayList<String> texts;

    private Context context;
    private LayoutInflater inflater;

    public ItemTaskAdapter(Context context, ArrayList<String> items) {
        this.items = items;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        texts = new ArrayList<>();
//        for (String item : items) {
//            texts.add("");
//        }
    }

    public ItemTaskAdapter(Context context){
        this.context = context;
        items = new ArrayList<>();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        texts = new ArrayList<>();
//        for (String item : items) {
//            texts.add("");
//        }
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

        if (itemView ==null){
            holder = new ViewHolder();
            itemView = inflater.inflate(R.layout.new_item_task_layout,parent,false);

            holder.editText = (EditText) itemView.findViewById(R.id.editTextTaskItem);
            holder.textView = (TextView)itemView.findViewById(R.id.itemTxt);
            holder.imageButton = (ImageButton) itemView.findViewById(R.id.deleteBtn);

            itemView.setTag(holder);

        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.ref = position;
        holder.textView.setText((position+1) + ".");
        holder.editText.setText(items.get(position));
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
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                Log.d("position", position + "");
                ItemTaskAdapter.super.notifyDataSetChanged();
            }
        });

//        View view = itemView;
//        view = inflater.inflate(R.layout.new_item_task_layout,parent,false);
//
//        EditText editText = (EditText) view.findViewById(R.id.editTextTaskItem);
//        editText.setText(items.get(position));
//        ((TextView)view.findViewById(R.id.itemTxt)).setText((position+1) + ".");
//        ImageButton button = (ImageButton) view.findViewById(R.id.deleteBtn);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                items.remove(position);
//                Log.d("position", position + "");
//                ItemTaskAdapter.super.notifyDataSetChanged();
//            }
//        });
        return itemView;
    }

    private class ViewHolder {
        TextView textView;
        EditText editText;
        ImageButton imageButton;
        int ref;
    }

    public void addNewItem(){
        items.add("");
    }

    public ArrayList<String> getItems() {
        return items;
    }
}
