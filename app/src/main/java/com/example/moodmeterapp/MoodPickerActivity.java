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


}