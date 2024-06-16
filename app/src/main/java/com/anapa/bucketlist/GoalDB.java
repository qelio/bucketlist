package com.anapa.bucketlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class GoalDB implements Serializable {
    private static final long serialVersionUID = 1L;
    int id, achieved, categories_id;
    String name, description, deadline, list_cases, date;

    public GoalDB(int id, int achieved, int categories_id, String name, String description, String deadline, String list_cases, String date) {
        this.id = id;
        this.achieved = achieved;
        this.categories_id = categories_id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.list_cases = list_cases;
        this.date = date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public static Comparator<GoalDB> getCompByName() {
        Comparator comp = new Comparator<GoalDB>(){
            @Override
            public int compare(GoalDB g1, GoalDB g2)
            {
                return g1.getName().compareTo(g2.getName());
            }
        };
        return comp;
    }

    public static Comparator<GoalDB> getCompByCategory(Context context) {
        Comparator comp = new Comparator<GoalDB>(){
            @Override
            public int compare(GoalDB g1, GoalDB g2)
            {
                return g1.getCategory_name(context).compareTo(g2.getCategory_name(context));
            }
        };
        return comp;
    }

    public static Comparator<GoalDB> getCompByDate() {
        Comparator comp = new Comparator<GoalDB>(){
            @Override
            public int compare(GoalDB g1, GoalDB g2)
            {
                Date date1, date2;
                try {
                    date1 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(g1.getDate());
                    date2 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(g2.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (date1.after(date2)) {
                    Log.d("DATES", date1 + " " + date2 + "1");
                    return 1;
                }
                else if (date2.after(date1)) {
                    Log.d("DATES", date1 + " " + date2 + "-1");
                    return -1;
                }
                Log.d("DATES", date1 + " " + date2 + "0");
                return 0;
            }
        };
        return comp;
    }

    public static Comparator<GoalDB> getCompByDeadline() {
        Comparator comp = new Comparator<GoalDB>(){
            @Override
            public int compare(GoalDB g1, GoalDB g2)
            {
                Date date1, date2;
                try {
                    date1 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(g1.getDeadline());
                    date2 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(g2.getDeadline());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (date1.after(date2)) {
                    Log.d("DATES", date1 + " " + date2 + "1");
                    return 1;
                }
                else if (date2.after(date1)) {
                    Log.d("DATES", date1 + " " + date2 + "-1");
                    return -1;
                }
                Log.d("DATES", date1 + " " + date2 + "0");
                return 0;
            }
        };
        return comp;
    }
}
