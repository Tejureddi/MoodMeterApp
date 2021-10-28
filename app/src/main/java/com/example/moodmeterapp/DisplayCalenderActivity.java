package com.example.moodmeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class DisplayCalenderActivity extends AppCompatActivity {

    private ArrayList<Mood> myMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_calender);
        Intent intent = getIntent();
        myMoods = intent.getParcelableArrayListExtra(("keyMood"));
        // TESTING
        System.out.println("1: " + myMoods.size());
        for (int i = 0; i < myMoods.size(); i++) {
            System.out.println("1: " + myMoods.get(i));
        }
    }


}