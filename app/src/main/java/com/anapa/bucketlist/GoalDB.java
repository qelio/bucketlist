package com.anapa.bucketlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class GoalDB {
    int id, achieved, categories_id;
    String deadline, list_cases;

    public GoalDB(int id, int achieved, int categories_id, String deadline, String list_cases) {
        this.id = id;
        this.achieved = achieved;
        this.categories_id = categories_id;
        this.deadline = deadline;
        this.list_cases = list_cases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getList_cases() {
        return list_cases;
    }

    public void setList_cases(String list_cases) {
        this.list_cases = list_cases;
    }
    public String getCategory_name (Context context) {
        DataBaseHelper databaseHelper;
        SQLiteDatabase db;
        databaseHelper = new DataBaseHelper(context);
        databaseHelper.create_db();
        Cursor userCursor;
        SimpleCursorAdapter userAdapter;
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from `categories`", null);
        ArrayList<CategoryDB> items = new ArrayList<CategoryDB>();
        while(userCursor.moveToNext()) {
            if (categories_id == userCursor.getInt(0)) {
                String str = userCursor.getString(1);
                userCursor.close();
                return str;
            }
        }
        userCursor.close();
        return "Категория не выбрана";
    }
}
