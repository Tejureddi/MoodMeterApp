package com.example.moodmeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MoodPickerActivity extends AppCompatActivity {

    private FirestoreHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_picker);
        dbHelper = new FirestoreHelper();
    }

    public void blueButton(View view) {
        goToCalendar("blue");
    }

    public void greenButton(View view) {
        goToCalendar("green");

    }

    public void yellowButton(View view) {
        goToCalendar("yellow");

    }

    public void redButton(View view) {
        goToCalendar("red");

    }

    public void resourceButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, ResourcesActivity.class);
        startActivity(intent);

    }

    public void goToCalendar(String color) {

        Mood mood = new Mood(color);

        dbHelper.addMood(mood);

        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalenderActivity.class);
        ArrayList<Mood> moodToShow = dbHelper.getMoodArrayList();
        intent.putParcelableArrayListExtra("keyMood", moodToShow);
        startActivity(intent);

    }



}