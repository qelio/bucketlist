package com.anapa.bucketlist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.anapa.bucketlist.AddBucketActivity;
import com.anapa.bucketlist.DataBaseHelper;
import com.anapa.bucketlist.GoalDB;
import com.anapa.bucketlist.R;
import com.anapa.bucketlist.adapters.GoalsAdapter;
import com.anapa.bucketlist.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ImageButton imageButton8;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    GoalsAdapter adapter;
    ListView listview_contests;
    Button btn_bucket;
    TabLayout tab_layout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listview_contests = (ListView) root.findViewById(R.id.listview_contests);
        ConstraintLayout bottom_sheet = (ConstraintLayout) root.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setHideable(true);
        imageButton8 = (ImageButton) root.findViewById(R.id.imageButton8);
        btn_bucket = (Button) root.findViewById(R.id.btn_bucket);
        tab_layout = (TabLayout) root.findViewById(R.id.tab_layout);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        databaseHelper = new DataBaseHelper(root.getContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from `goals` where `achieved` = 0", null);
        ArrayList<GoalDB> goals = new ArrayList<GoalDB>();
        while(userCursor.moveToNext()) {
            GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6));
            goals.add(goalDB_cursor);
        }
        userCursor.close();
        Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
        adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent);
        listview_contests.setAdapter(adapter);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_sheet.setVisibility(View.VISIBLE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Log.d("BTNCLk", "1");
            }
        });
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    goals.clear();
                    listview_contests.setAdapter(null);
                    userCursor = db.rawQuery("select * from `goals` where `achieved` = 0", null);
                    while(userCursor.moveToNext()) {
                        GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6));
                        goals.add(goalDB_cursor);
                    }
                    userCursor.close();
                    Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                    adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent);
                    listview_contests.setAdapter(adapter);
                }
                if (tab.getPosition() == 1) {
                    goals.clear();
                    listview_contests.setAdapter(null);
                    userCursor = db.rawQuery("select * from `goals` where `achieved` = 1", null);
                    while(userCursor.moveToNext()) {
                        GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6));
                        goals.add(goalDB_cursor);
                    }
                    userCursor.close();
                    Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                    adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent);
                    listview_contests.setAdapter(adapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // В данном случае, действие не требуется
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // В данном случае, действие не требуется
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}