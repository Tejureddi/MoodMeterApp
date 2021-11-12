package com.example.moodmeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MoodPickerActivity extends AppCompatActivity {

    // FirestoreHelper Variable

    private FirestoreHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_picker);

        // instantiate FirestoreHelper variable

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

        // create a new mood object with color zone

        Mood mood = new Mood(color);

        if (dbHelper.entryExists(mood)) {
            Toast.makeText(MoodPickerActivity.this, "You've Already Recorded Your Mood Today", Toast.LENGTH_SHORT).show();
        }

        // add mood object to database

        else {
            dbHelper.addMood(mood);
        }

        // pass object to calendar activity using Parcelable and an intent

        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalendarActivity.class);
        ArrayList<Mood> moodToShow = dbHelper.getMoodArrayList();
        intent.putParcelableArrayListExtra("keyMood", moodToShow);

        // start calendar activity

        startActivity(intent);

    }



}