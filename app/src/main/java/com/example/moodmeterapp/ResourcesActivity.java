package com.example.moodmeterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
    }

    public void imageCal2(View view) {
        Intent intent = new Intent(ResourcesActivity.this, DisplayCalendarActivity.class);
        startActivity(intent);


    }
}