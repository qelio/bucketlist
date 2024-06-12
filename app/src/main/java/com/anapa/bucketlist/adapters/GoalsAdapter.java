package com.anapa.bucketlist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anapa.bucketlist.GoalDB;
import com.anapa.bucketlist.R;

import java.util.ArrayList;

public class GoalsAdapter extends ArrayAdapter<GoalDB> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<GoalDB> goalDBArrayList;
    public Intent intent;
    public Context context;

    public GoalsAdapter(Context context, int resource, ArrayList<GoalDB> goalDBArrayList, Intent intent) {
        super(context, resource, goalDBArrayList);
        this.goalDBArrayList = goalDBArrayList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.intent = intent;
        this.context = context;
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
        // Content filling of the graphical interface elements of this block:
        viewHolder.textView_notes.setText(goalDB.getName());
        viewHolder.imageView_title.setImageDrawable(context.getDrawable(R.drawable.most3));
        viewHolder.current_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
    private class ViewHolder {
        final ImageView imageView_title;
        final TextView textView_notes;
        final LinearLayout current_goal;
        ViewHolder(View view){
            imageView_title = view.findViewById(R.id.imageView_title);
            textView_notes = view.findViewById(R.id.textView_notes);
            current_goal = view.findViewById(R.id.current_goal);
        }
    }
}

