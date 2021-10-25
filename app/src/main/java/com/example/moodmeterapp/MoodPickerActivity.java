package com.example.moodmeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoodPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_picker);

    }

    public void blueButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalenderActivity.class);
        startActivity(intent);


    }

    public void greenButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalenderActivity.class);
        startActivity(intent);

    }

    public void yellowButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalenderActivity.class);
        startActivity(intent);

    }

    public void redButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, DisplayCalenderActivity.class);
        startActivity(intent);

    }

    public void resourceButton(View view) {
        Intent intent = new Intent(MoodPickerActivity.this, ResourcesActivity.class);
        startActivity(intent);

    }



}