package com.anapa.bucketlist.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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
    RadioGroup radio_group;
    int filter_var = 0;
    CheckBox sort_type;
    ArrayList<GoalDB> goals = new ArrayList<GoalDB>();
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
        radio_group = (RadioGroup) root.findViewById(R.id.radio_group);
        sort_type = (CheckBox) root.findViewById(R.id.sort_type);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        databaseHelper = new DataBaseHelper(root.getContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from `goals` where `achieved` = 0", null);
        while(userCursor.moveToNext()) {
            GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6), userCursor.getString(7));
            goals.add(goalDB_cursor);
        }
        userCursor.close();
        Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
        adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
        listview_contests.setAdapter(adapter);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // radio_group.clearCheck();
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
                        GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6), userCursor.getString(7));
                        goals.add(goalDB_cursor);
                    }
                    userCursor.close();
                    if (filter_var != 0) {
                        if (filter_var == 1) {
                            goals.sort(GoalDB.getCompByDate());
                        }
                        if (filter_var == 2) {
                            goals.sort(GoalDB.getCompByName());
                        }
                        if (filter_var == 3) {
                            goals.sort(GoalDB.getCompByCategory(getContext()));
                        }
                        if (filter_var == 4) {
                            goals.sort(GoalDB.getCompByDeadline());
                        }
                    }
                    if (!sort_type.isChecked()) {
                        Collections.reverse(goals);
                    }
                    Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                    adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                    listview_contests.setAdapter(adapter);
                }
                if (tab.getPosition() == 1) {
                    goals.clear();
                    listview_contests.setAdapter(null);
                    userCursor = db.rawQuery("select * from `goals` where `achieved` = 1", null);
                    while(userCursor.moveToNext()) {
                        GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6), userCursor.getString(7));
                        goals.add(goalDB_cursor);
                    }
                    userCursor.close();
                    if (filter_var != 0) {
                        if (filter_var == 1) {
                            goals.sort(GoalDB.getCompByDate());
                        }
                        if (filter_var == 2) {
                            goals.sort(GoalDB.getCompByName());
                        }
                        if (filter_var == 3) {
                            goals.sort(GoalDB.getCompByCategory(getContext()));
                        }
                        if (filter_var == 4) {
                            goals.sort(GoalDB.getCompByDeadline());
                        }
                    }
                    if (!sort_type.isChecked()) {
                        Collections.reverse(goals);
                    }
                    Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                    adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
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


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    filter_var = 1;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goals.sort(GoalDB.getCompByDate());
                            if (!sort_type.isChecked()) {
                                Collections.reverse(goals);
                            }
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }
                if (checkedId == R.id.radioButton2) {
                    filter_var = 2;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goals.sort(GoalDB.getCompByName());
                            if (!sort_type.isChecked()) {
                                Collections.reverse(goals);
                            }
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }
                if (checkedId == R.id.radioButton3) {
                    filter_var = 3;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goals.sort(GoalDB.getCompByCategory(getContext()));
                            if (!sort_type.isChecked()) {
                                Collections.reverse(goals);
                            }
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }
                if (checkedId == R.id.radioButton4) {
                    filter_var = 4;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goals.sort(GoalDB.getCompByDeadline());
                            if (!sort_type.isChecked()) {
                                Collections.reverse(goals);
                            }
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }

            }
        });
        sort_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Collections.reverse(goals);
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }
                else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Collections.reverse(goals);
                            listview_contests.setAdapter(null);
                            Intent intent = new Intent(root.getContext(), AddBucketActivity.class);
                            adapter = new GoalsAdapter(root.getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
                            listview_contests.setAdapter(adapter);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    }, 300);
                }
            }
        });

        listview_contests.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    btn_bucket.setVisibility(View.VISIBLE);
                }
                else {
                    btn_bucket.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tab_layout.getTabAt(0);
        tab.select();
        goals.clear();
        databaseHelper = new DataBaseHelper(getContext());
        databaseHelper.create_db();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from `goals` where `achieved` = 0", null);
        while(userCursor.moveToNext()) {
            GoalDB goalDB_cursor = new GoalDB(userCursor.getInt(0), userCursor.getInt(3), userCursor.getInt(4), userCursor.getString(1), userCursor.getString(2), userCursor.getString(5), userCursor.getString(6), userCursor.getString(7));
            goals.add(goalDB_cursor);
        }
        userCursor.close();
        if (filter_var != 0) {
            if (filter_var == 1) {
                goals.sort(GoalDB.getCompByDate());
            }
            if (filter_var == 2) {
                goals.sort(GoalDB.getCompByName());
            }
            if (filter_var == 3) {
                goals.sort(GoalDB.getCompByCategory(getContext()));
            }
            if (filter_var == 4) {
                goals.sort(GoalDB.getCompByDeadline());
            }
        }
        if (!sort_type.isChecked()) {
            Collections.reverse(goals);
        }
        Intent intent = new Intent(getContext(), AddBucketActivity.class);
        adapter = new GoalsAdapter(getContext(), R.layout.target_list, goals, intent, databaseHelper, db);
        listview_contests.setAdapter(adapter);
    }
}