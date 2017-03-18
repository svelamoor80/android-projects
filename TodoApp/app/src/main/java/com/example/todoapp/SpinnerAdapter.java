package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by velamoors on 3/14/17.
 */

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    String priority[] = {"none","Low","Medium","High"};
    public SpinnerAdapter(Context context) {
     this.context = context;

    }
    @Override
    public int getCount() {
        return priority.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListContent holder;
        View v = convertView;
        if (v == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.spinner_textview, null);
            holder = new ListContent();
            holder.text = (TextView) v.findViewById(R.id.textView1);
            v.setTag(holder);
        } else {
            holder = (ListContent) v.getTag();
        }
        holder.text.setText(priority[position]);
        return v;

    }

    static class ListContent {
        TextView text;
    }

}

