package com.anapa.bucketlist.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anapa.bucketlist.CategoryDB;
import com.anapa.bucketlist.DataBaseHelper;
import com.anapa.bucketlist.GoalDB;
import com.anapa.bucketlist.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<CategoryDB> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<CategoryDB> categoryDBArrayList;
    public Context context;
    private Button state_button;
    private BottomSheetBehavior bottomSheetBehavior;
    public CategoriesAdapter(Context context, int resource, ArrayList<CategoryDB> categoryDBArrayList, Button state_button, BottomSheetBehavior bottomSheetBehavior ) {
        super(context, resource, categoryDBArrayList);
        this.categoryDBArrayList = categoryDBArrayList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.state_button = state_button;
        this.bottomSheetBehavior = bottomSheetBehavior;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CategoryDB categoryDB = categoryDBArrayList.get(position);
        viewHolder.category_name.setText(categoryDB.getName());
        viewHolder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state_button.setText(categoryDB.getName());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        return convertView;
    }
    private class ViewHolder {
        final Button category_name;
        ViewHolder(View view){
            category_name = view.findViewById(R.id.category_name);
        }
    }
}

