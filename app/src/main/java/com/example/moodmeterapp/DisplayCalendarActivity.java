package com.example.moodmeterapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayCalendarActivity extends AppCompatActivity {

    // Arraylist of moods to receive from main activity

    private ArrayList<Mood> myMoods;

    // array of dates to fill text views in calendar

    String[] dates = new String[32];

    // array of images to fill colors in calendar

    int[] colors = new int[32];

    // calendar

    GridView gridView;

    // saves current month

    int currentMonth = getCurrentDate("month");

    // saves current year

    int currentYear = getCurrentDate("year");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_calender);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // receive array list from intent

        Intent intent = getIntent();
        myMoods = intent.getParcelableArrayListExtra(("keyMood"));


        // fill calendar with colors

        fillColorsArray(colors);

        // fill calendar with dates

        fillDatesArray(dates);

        // instantiate calendar

        gridView = (GridView) findViewById(R.id.calendar);

        // create custom adapter variable

        CustomAdapter customAdapter = new CustomAdapter(colors, dates, this);

        // put custom adapter data in calendar

        gridView.setAdapter(customAdapter);
    }

    // fills date array with 1-31 (WHAT ABOUT 30?)
    public void fillDatesArray(String[] array) {
        for (int i = 1; i < 32; i++) {
            array[i - 1] = String.valueOf(i);
        }
    }

    // fills image array with images corresponding to color zone

    public void fillColorsArray(int[] array) {

        // new arraylist to hold moods for individual months

        ArrayList<Mood> month = new ArrayList<Mood>();

        // fill arraylist with moods for each month only

        for (int i = 0; i < myMoods.size(); i++) {
            Mood mood = myMoods.get(i);
            if (mood.getMonth() == currentMonth) {
                month.add(mood);
            }
        }

        // first for loop: loop through each day in month

        for (int i = 1; i < 32; i++) {

            // second for loop: loop through each mood in month

            for (int j = 0; j < month.size(); j++) {

                Mood m = month.get(j);

                // if there is no mood recorded for that day

                if (m.getDate() != i) {

                    // fill day with white square (no color)

                    array[i - 1] = R.drawable.empty_mood;
                }

                // if there is a mood recorded for that day

                else {

                    // fill day with corresponding color

                    if (m.getColor().equals("red")) {
                        array[i - 1] = R.drawable.red_mood;
                    }

                    // if the mood is in the orange zone, add red image to array of images

                    else if (m.getColor().equals("yellow")) {
                        array[i - 1] = R.drawable.yellow_mood;
                    }

                    // if the mood is in the green zone, add red image to array of images

                    else if (m.getColor().equals("green")) {
                        array[i - 1] = R.drawable.green_mood;
                    }
                }
            }
        }

    }


    // returns current date

    public int getCurrentDate(String key) {

        // get current date

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        // if parameter is "month"

        if (key.equals("month")) {

            // return month

            return Integer.parseInt(date.substring(3, 5));

        }

        // if parameter is "year"

        else if (key.equals("year")) {

            // return year

            return Integer.parseInt(date.substring(6));
        }

        // if parameter isn't a date, month, or year, return -1

        else {
            return -1;
        }
    }

    // custom adapter class

    public class CustomAdapter extends BaseAdapter {

        private String[] dates;
        private int[] colors;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(int[] colors, String[] dates, Context context) {
            this.dates = dates;
            this.colors = colors;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = layoutInflater.inflate(R.layout.day_in_calendar, viewGroup, false);
            }

            TextView date = view.findViewById(R.id.date);
            ImageView color = view.findViewById(R.id.imageView);

            date.setText(dates[i]);
            color.setImageResource(colors[i]);

            return view;
        }
    }

}