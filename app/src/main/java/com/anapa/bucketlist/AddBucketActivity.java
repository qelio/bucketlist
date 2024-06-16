package com.anapa.bucketlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;

import com.anapa.bucketlist.adapters.CategoriesAdapter;
import com.anapa.bucketlist.adapters.GoalsAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddBucketActivity extends AppCompatActivity {
    TextInputEditText new_bucket_name, new_bucket_description;
    Switch switch1;
    Button button2, button3, button4, btn_save;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    ListView listview_contests;
    Calendar dateAndTime=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        getSupportActionBar().hide();
        databaseHelper = new DataBaseHelper(getApplicationContext());
        databaseHelper.create_db();
        setContentView(R.layout.activity_add_bucket);
        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        new_bucket_name = (TextInputEditText) findViewById(R.id.new_bucket_name);
        new_bucket_description = (TextInputEditText) findViewById(R.id.new_bucket_description);
        switch1 = (Switch) findViewById(R.id.switch1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        btn_save = (Button) findViewById(R.id.btn_save);
        listview_contests = (ListView) findViewById(R.id.listview_contests);
        if (arguments != null) {
            GoalDB goalDBExtra = (GoalDB) arguments.getSerializable("class");
            button2.setText(goalDBExtra.getCategory_name(getApplicationContext()));
            button3.setText(goalDBExtra.getDeadline());
            new_bucket_name.setText(goalDBExtra.getName());
            new_bucket_description.setText(goalDBExtra.getDescription());
            if (goalDBExtra.getAchieved() == 0) {
                switch1.setChecked(false);
            }
            else {
                switch1.setChecked(true);
            }
        }
        ConstraintLayout categories_bottom_sheet = (ConstraintLayout) findViewById(R.id.categories_bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(categories_bottom_sheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from `categories`", null);
        ArrayList<CategoryDB> items = new ArrayList<CategoryDB>();
        while(userCursor.moveToNext()) {
            CategoryDB categoryDB = new CategoryDB(userCursor.getInt(0), userCursor.getString(1));
            items.add(categoryDB);
        }
        userCursor.close();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arguments != null) {
                    int category_id = 0;
                    for (CategoryDB categoryDB : items) {
                        if (button2.getText().toString().equals(categoryDB.getName())) {
                            category_id = categoryDB.getId();
                        }
                    }
                    String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    GoalDB goalDBExtra = (GoalDB) arguments.getSerializable("class");
                    GoalDB goalDB = new GoalDB(goalDBExtra.getId(), (switch1.isChecked() ? 1 : 0), category_id, new_bucket_name.getText().toString(), new_bucket_description.getText().toString(), button3.getText().toString(), button4.getText().toString(), date);
                    ContentValues values = new ContentValues();
                    values.put("name", goalDB.getName());
                    values.put("description", goalDB.getDescription());
                    values.put("achieved", goalDB.getAchieved());
                    values.put("categories_id", goalDB.getCategories_id());
                    values.put("deadline", goalDB.getDeadline());
                    values.put("list_cases", goalDB.getList_cases());
                    values.put("date", goalDB.getDate());
                    db.update("goals", values, "id" + "=" + goalDB.getId(), null);
                    finish();
                }
                else {
                    int category_id = 0;
                    for (CategoryDB categoryDB : items) {
                        if (button2.getText().toString().equals(categoryDB.getName())) {
                            category_id = categoryDB.getId();
                        }
                    }
                    String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
                    GoalDB goalDB = new GoalDB(0, (switch1.isChecked() ? 1 : 0), category_id, new_bucket_name.getText().toString(), new_bucket_description.getText().toString(), button3.getText().toString(), button4.getText().toString(), date);
                    ContentValues values = new ContentValues();
                    values.put("name", goalDB.getName());
                    values.put("description", goalDB.getDescription());
                    values.put("achieved", goalDB.getAchieved());
                    values.put("categories_id", goalDB.getCategories_id());
                    values.put("deadline", goalDB.getDeadline());
                    values.put("list_cases", goalDB.getList_cases());
                    values.put("date", goalDB.getDate());
                    long newRowId = db.insert("goals", null, values);
                    userCursor = db.rawQuery("select * from `goals`", null);
                    ArrayList<GoalDB> goals = new ArrayList<GoalDB>();
                    while (userCursor.moveToNext()) {
                        GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6), userCursor.getString(7));
                        goals.add(goalDB_cursor);
                    }
                    userCursor.close();
                    for (GoalDB goalDB_cur : goals) {
                        String current_goal = Integer.toString(goalDB_cur.getId()) + " " + goalDB_cur.getName() + " " + goalDB_cur.getDescription() + " " + Integer.toString(goalDB_cur.getAchieved()) + " " + Integer.toString(goalDB_cur.getCategories_id()) + " " + goalDB_cur.getDeadline() + " " + goalDB_cur.getList_cases() + " " + goalDB_cur.getCategory_name(getApplicationContext());
                        Log.d("CURRENT_GOAL", current_goal);
                    }
                    finish();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                userCursor = db.rawQuery("select * from `categories`", null);
                ArrayList<CategoryDB> items = new ArrayList<CategoryDB>();
                while(userCursor.moveToNext()) {
                    CategoryDB categoryDB = new CategoryDB(userCursor.getInt(0), userCursor.getString(1));
                    items.add(categoryDB);
                }
                userCursor.close();
                CategoriesAdapter adapter = new CategoriesAdapter(getApplicationContext(), R.layout.category_bottom_sheet, items, button2, bottomSheetBehavior);
                listview_contests.setAdapter(adapter);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
    }
    public void setDate(View v) {
        new DatePickerDialog(AddBucketActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
    private void setInitialDateTime() {
        String day = Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH)) , month = Integer.toString(dateAndTime.get(Calendar.MONTH) + 1);
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        button3.setText(day + "." + month + "." + dateAndTime.get(Calendar.YEAR));
    }
    // Что это? Зачем это..?
    public void onBucketSaveButtonClick (View view) {
        MainActivity.Buckets.add(new Bucket("Test Bucket", "Test description"));
        MainActivity.SaveBuckets();
        this.finish();
    }

}