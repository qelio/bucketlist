package com.anapa.bucketlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.anapa.bucketlist.databinding.ActivityMainBinding;

import java.io.Console;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static ArrayList<Bucket> Buckets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_categories, R.id.navigation_inspiration, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Buckets = new ArrayList<Bucket>();
        SaveBuckets();

        Timer logTimer = new Timer();
        /* logTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("BucketsCount:", Integer.toString(Buckets.size()));
            }
        }, 0, 2000); */


        /*
        binding.openBsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBottomSheet myBottomSheet = new MyBottomSheet();
                myBottomSheet.show(getSupportFragmentManager(), "my bottom sheet dialog");
            }
        }); */
    }

    public static void SaveBuckets() {

    }

    public void onAddBucketButtonClick(View view){
        Intent intent = new Intent(this, AddBucketActivity.class);
        startActivity(intent);
    }
}