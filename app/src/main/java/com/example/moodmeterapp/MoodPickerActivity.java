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

    // if blue zone is clicked, pass "blue" parameter to goToCalendar()

    public void blueButton(View view) {
        goToCalendar("blue");
    }

    // if green zone is clicked, pass "green" parameter to goToCalendar()

    public void greenButton(View view) {
        goToCalendar("green");

    }

    // if yellow zone is clicked, pass "yellow" parameter to goToCalendar()

    public void yellowButton(View view) {
        goToCalendar("yellow");

    }

    // if red zone is clicked, pass "red" parameter to goToCalendar()

    public void redButton(View view) {
        goToCalendar("red");
    }

    // if resources button is clicked, take user to resources page

    public void resourceButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, ResourcesActivity.class);
        startActivity(intent);

    }

    // this method saves user's mood and takes user to their calendar

    public void goToCalendar(String color) {

        // create a new mood object with color zone

        Mood mood = new Mood(color);

        // check if user has already recorded a mood on that same day

        if (dbHelper.entryExists(mood)) {

            // if user has already recorded a mood for that day, do not save the most recently recorded mood
            // instead, make a Toast that notifies the user that they've already recorded their mood

            Toast.makeText(MoodPickerActivity.this, "You've Already Recorded Your Mood Today", Toast.LENGTH_SHORT).show();
        }

        // if the user hasn't recorded a mood on that day, save mood in firebase

        else {
            dbHelper.addMood(mood);
        }

        // pass mood object to calendar activity using Parcelable and an intent

        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalendarActivity.class);
        ArrayList<Mood> moodToShow = dbHelper.getMoodArrayList();
        intent.putParcelableArrayListExtra("keyMood", moodToShow);

        // start calendar activity

        startActivity(intent);

    }

}