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

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


// here is the tutorial I used for the gridview and custom adapter:
// https://youtu.be/RtitGGmvvTI


public class DisplayCalendarActivity extends AppCompatActivity {

    // Arraylist of moods to receive from mood picker activity

    private ArrayList<Mood> myMoods;

    // array of dates to fill text views in calendar

    String[] dates = new String[32];

    // array of images to fill colors in calendar

    int[] colors = new int[32];

    // gridview variable (calendar)

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

        // receive and instantiate array list from mood picker activity

        Intent intent = getIntent();
        myMoods = intent.getParcelableArrayListExtra(("keyMood"));

        // fill the empty colors array with drawable files (each file corresponds to the color zone of a mood)
        // this array will be used to put colors in the calendar

        fillColorsArray(colors);

        // fill the empty dates array with "1", "2", "3", ... ,"31" for each day of the month

        fillDatesArray(dates);

        // instantiate gridview (calendar) variable

        gridView = (GridView) findViewById(R.id.calendar);

        // create a custom adapter variable (see custom adapter class below)

        CustomAdapter customAdapter = new CustomAdapter(colors, dates, this);

        // fill the calendar with dates and colors using custom adapter variable

        gridView.setAdapter(customAdapter);
    }

    // this method uses a for loop to fill date array with "1", "2", "3", ... , "31"

    public void fillDatesArray(String[] array) {
        for (int i = 1; i < 32; i++) {
            array[i - 1] = String.valueOf(i);
        }
    }

    // this method fills colors array with drawable files

    public void fillColorsArray(int[] array) {

        // this arraylist will contain all of the moods recorded in the current month

        ArrayList<Mood> month = new ArrayList<Mood>();

        // traverse ALL recorded moods

        for (int i = 0; i < myMoods.size(); i++) {
            Mood mood = myMoods.get(i);

            // if a mood is recorded in the current month by the current user, add the mood to "month" arraylist

            if (mood.getMonth() == currentMonth && mood.getYear() == currentYear && mood.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                month.add(mood);
            }

        }

        // for each day in the month

        for (int i = 1; i < 32; i++) {

            // create new mood object
            // this mood object calls default constructor, so it has no color zone

            Mood m = new Mood();

            // traverse array list of moods recorded in current month

            for (int j = 0; j < month.size(); j++) {

                // if there is a mood recorded on day i

                if (month.get(j).getDate() == i) {

                    // set that mood equal to "m"
                    // previously, "m" did not have a color zone because it was created using the default constructor
                    // now, if a mood has been recorded on day i, "m" will have a color zone

                    m = month.get(j);

                    // break out of for loop once a mood has been found for day i (for efficiency reasons)

                    break;

                }

            }

            // if "m" still doesn't have a color zone, that means that no mood has been recorded on day i

            if (m.getColor().equals("none")) {

                // if no mood has been recorded, add a white colored image to the array of drawable files

                array[i - 1] = R.drawable.empty_mood;

            }

            // if "m" has a color zone, a mood has been recorded on day i

            else {

                // use "i - 1" to fill array because i starts at 1 while arrays start at index 0
                // if the recorded mood is in the blue zone, add a blue image to the array of drawable files

                if (m.getColor().equals("blue")) {
                    array[i - 1] = R.drawable.blue_mood;
                }

                // if the recorded mood is in the red zone, add a red image to the array of drawable files

                else if (m.getColor().equals("red")) {
                    array[i - 1] = R.drawable.red_mood;
                }

                // if the recorded mood is in the yellow zone, add a yellow image to the array of drawable files

                else if (m.getColor().equals("yellow")) {
                    array[i - 1] = R.drawable.yellow;
                }

                // if the recorded mood is in the green zone, add a green image to the array of drawable files

                else if (m.getColor().equals("green")) {
                    array[i - 1] = R.drawable.green_mood;
                }

            }

        }

    }

    // returns current month OR year

    public int getCurrentDate(String key) {

        // get current date using simple date format

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
    // this is used to fill the gridview (calendar) with data
    // a custom adapter is like a "bridge" that connects the calendar and the data in firebase

    public class CustomAdapter extends BaseAdapter {

        // variables

        private String[] dates;
        private int[] colors;
        private Context context;
        private LayoutInflater layoutInflater;

        // constructor
        // this constructor is called in the onCreate()
        // the actual parameters for this constructor are the arrays of dates and drawable files

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

                // R.layout.day_in_calendar is a layout resource file
                // it contains the design elements for a single day in the calendar
                // each day consists of a textview (for the date) underneath an imageview (for the color)

                view = layoutInflater.inflate(R.layout.day_in_calendar, viewGroup, false);

            }

            // textview and imageview from R.layout.day_in_calendar

            TextView date = view.findViewById(R.id.date);
            ImageView color = view.findViewById(R.id.imageView);

            // set text for textview with a number 1-31 from array of dates

            date.setText(dates[i]);

            // set image resource for imageview with drawable file from array of drawable files

            color.setImageResource(colors[i]);

            return view;
        }
    }
}


