package com.anapa.bucketlist.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.anapa.bucketlist.DataBaseHelper;
import com.anapa.bucketlist.GoalDB;
import com.anapa.bucketlist.R;
import com.anapa.bucketlist.ui.home.HomeFragment;

import java.util.ArrayList;

public class GoalsAdapter extends ArrayAdapter<GoalDB> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<GoalDB> goalDBArrayList;
    public Intent intent;
    public Context context;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    public GoalsAdapter(Context context, int resource, ArrayList<GoalDB> goalDBArrayList, Intent intent, DataBaseHelper databaseHelper, SQLiteDatabase db) {
        super(context, resource, goalDBArrayList);
        this.goalDBArrayList = goalDBArrayList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.intent = intent;
        this.context = context;
        this.databaseHelper = databaseHelper;
        this.db = db;
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
        final GoalDB goalDB = goalDBArrayList.get(position);
        viewHolder.textView_notes.setText(goalDB.getName());

        // КАРТИНКИ УСТАНАВЛИВАТЬ ЗДЕСЬ -->

        if (goalDB.getCategories_id() == 1) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most2));
        }
        if (goalDB.getCategories_id() == 2) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most3));
        }
        if (goalDB.getCategories_id() == 3) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most4));
        }
        if (goalDB.getCategories_id() == 4) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most5));
        }
        if (goalDB.getCategories_id() == 5) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most6));
        }
        if (goalDB.getCategories_id() == 6) {
            viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most7));
        }


        viewHolder.current_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("class", goalDB);
                context.startActivity(intent);
            }
        });
        viewHolder.delete_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Подтвердите выбор");
                alert.setMessage("Вы действительно хотите удалить данную цель?");
                alert.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("goals", "id" + "=" + Integer.toString(goalDB.getId()), null);
                        int index_for_array = 0;
                        for (GoalDB goalDB1 : goalDBArrayList) {
                            if (goalDB1.getId() == goalDB.getId()) {
                                goalDBArrayList.remove(index_for_array);
                                break;
                            }
                            index_for_array += 1;
                        }
                        notifyDataSetChanged();
                        Log.d("TO_DB", "delete from `goals` where `id`=" + Integer.toString(goalDB.getId()));
                    }
                });
                alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        return convertView;
    }
    private class ViewHolder {
        final ImageView imageView_title;
        final TextView textView_notes;
        final LinearLayout current_goal;
        final ImageView delete_goal;
        final CardView bucket_container;
        ViewHolder(View view){
            imageView_title = view.findViewById(R.id.imageView_title);
            textView_notes = view.findViewById(R.id.textView_notes);
            current_goal = view.findViewById(R.id.current_goal);
            delete_goal = view.findViewById(R.id.delete_goal);
            bucket_container = view.findViewById(R.id.bucket_container);
        }
    }
}

