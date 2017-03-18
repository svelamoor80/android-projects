package com.example.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by velamoors on 3/14/17.
 */

public class UserAdpater extends ArrayAdapter<TodoDataModel>{
    Context context;

    public UserAdpater(Context context, ArrayList<TodoDataModel> listItems) {
        super(context, 0, listItems);
        this.context = getContext();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TodoDataModel todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_view, parent, false);
        }
        final TextView todoTitle = (TextView) convertView.findViewById(R.id.textView);
        TextView dueDate =(TextView) convertView.findViewById(R.id.dueDateView);
        TextView priority = (TextView) convertView.findViewById(R.id.priorityValue);
        final CheckBox checkBox =(CheckBox) convertView.findViewById(R.id.completedCheck);
        todoTitle.setText(todo.getTodoItemTitle());
        dueDate.setText(todo.getDueDate());
        priority.setText(todo.getPriority());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //todo.setIsCompleted(isChecked);
                Log.i("<MyPosition>", "Checkbox Pos == " + position);
                UserAdpater.super.getItem(position).setIsCompleted(isChecked);
                if (isChecked) {
                    todoTitle.setPaintFlags(todoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    todoTitle.setPaintFlags(todoTitle.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
                UserAdpater.super.notifyDataSetChanged();
            }
        });
        ImageButton editButton = (ImageButton) convertView.findViewById(R.id.EditButton);
        editButton.setTag(position);
        editButton.setOnClickListener(new View.OnClickListener() {
            String priorityList[] = {"None","Low", "Medium","High"};
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_textview, R.id.textView1, priorityList );
            ArrayList<TodoDataModel> todoItems = new ArrayList<>();
            private int getIndex(Spinner spinner, String myString)
            {
                int index = 0;

                for (int i=0;i<spinner.getCount();i++){
                    if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                        index = i;
                        break;
                    }
                }
                return index;
            }
            @Override
            public void onClick(View v) {

                final int position = (Integer) v.getTag();
                 TodoDataModel item = getItem(position);
                Log.i("position","position"+position+item.getPriority());
                Log.i("Context:", "Context now == " + context.toString());
                final AlertDialog dialog = new AlertDialog.Builder(v.getRootView().getContext())
                        .setTitle("Edit a todo item")
                        .setView(R.layout.add_item_dialog)
                        .create();
                dialog.show();
                final EditText todoTitle = (EditText) dialog.findViewById(R.id.addItemText);
                todoTitle.setText(item.getTodoItemTitle(), EditText.BufferType.EDITABLE);
                final Spinner prioritySpinner = (Spinner) dialog.findViewById(R.id.prioritySpinner);
                prioritySpinner.setAdapter(adapter);
                int index = getIndex(prioritySpinner, item.getPriority());
                prioritySpinner.setSelection(index);
                final TextView dateDue = (TextView) dialog.findViewById(R.id.editText2);
                dateDue.setText(item.getDueDate(), TextView.BufferType.NORMAL);
                ImageButton calendar = (ImageButton) dialog.findViewById(R.id.imageButton2);
                Button dialogOkButton = (Button) dialog.findViewById(R.id.okButton);
                calendar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int mYear, mMonth, mDay;
                        final Calendar c = java.util.Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        final DatePickerDialog datePickerDialog = new DatePickerDialog(v.getRootView().getContext(), new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){

                            dateDue.setText("0"+(month+1)+"-"+dayOfMonth+"-"+year);
                        }
                    }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                }
                });
                dialogOkButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        TodoDataModel model = UserAdpater.super.getItem(position);
                        model.setTodoItemTitle(todoTitle.getText().toString());
                        model.setDueDate(dateDue.getText().toString());
                        model.setPriority(prioritySpinner.getSelectedItem().toString());
                        //model.setIsCompleted(model.getIsCompleted());
                        UserAdpater.super.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button dialogDeleteButton = (Button) dialog.findViewById(R.id.deleteButton);
                dialogDeleteButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        TodoDataModel model = UserAdpater.super.getItem(position);
                        UserAdpater.super.remove(model);
                        UserAdpater.super.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
            }
        });
        return convertView;
    }


}
