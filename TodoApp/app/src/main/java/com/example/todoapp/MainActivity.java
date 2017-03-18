package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    private int mYear, mMonth, mDay;
    String simpleList[] = {"Grocery", "Laundry", "Dr. Apptmt", "Make dinner"};
    String priorityList[] = {"None","Low", "Medium","High"};
    List list = new ArrayList();

    ArrayList<TodoDataModel> todoItems;
    SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Collections.addAll(list, simpleList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext());
        ListView v = (ListView) findViewById(R.id.todoList);
        todoItems = new ArrayList<>();

        final UserAdpater arrayAdapter= new UserAdpater(getApplicationContext(), todoItems);
        v.setAdapter(arrayAdapter);

        ImageButton addItem = (ImageButton) findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Add a todo item")
                        .setView(R.layout.add_item_dialog)
                        .create();

                dialog.show();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_textview, R.id.textView1, priorityList );
                final Spinner spinner = (Spinner) dialog.findViewById(R.id.prioritySpinner);
                spinner.setAdapter(adapter);
                Button dialogOkButton = (Button) dialog.findViewById(R.id.okButton);
                ImageButton calendar = (ImageButton) dialog.findViewById(R.id.imageButton2);
                final TextView dateText = (TextView) dialog.findViewById(R.id.editText2);
                final TextView priorityValue = (TextView) spinner.findViewById(R.id.textView1);

                calendar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        final DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                                dateText.setText("0"+(month+1)+"-"+dayOfMonth+"-"+year);
                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                dialogOkButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i(this.toString(), "Save Button Click Listener Called");
                        EditText addTodo = (EditText) dialog.findViewById(R.id.addItemText);
                        todoItems.add(new TodoDataModel(addTodo.getText().toString(),
                                dateText.getText().toString(),
                                spinner.getSelectedItem().toString(),false));
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                dialogCancelButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }
}
