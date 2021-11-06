package com.example.moodmeterapp;

// The Mood Class saves a user's mood and data about that mood as an object.
// The Mood Class implements Parcelable to send and receive objects between activities.


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mood implements Parcelable, Comparable<Mood> {

    private String color; // color zone of mood (red, green, yellow, or blue)
    private int month; // month in which mood is recorded
    private String key; // key for storage in firebase
    private int date; // date on which mood is recorded (23, 1, 15, etc)
    private int year; // year in which mood is recorded

    //Parcelable.CREATOR interface

    public static final Parcelable.Creator<Mood> CREATOR = new Parcelable.Creator<Mood>() {

        @Override
        public Mood createFromParcel(Parcel parcel) {
            return new Mood(parcel);
        }

        @Override
        public Mood[] newArray(int size) {
            return new Mood[0];
        }
    };

    // Constructor for Parcelable

    public Mood(Parcel parcel) {
        color = parcel.readString();
        month = parcel.readInt();
        year = parcel.readInt();
        key = parcel.readString();
        date = parcel.readInt();
    }

    // default constructor with no parameters

    public Mood() {
        color = "none";
        month = 0;
        year = 0;
        date = 0;
    }

    // constructor for mood objects without that don't have a key

    public Mood (String color) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        key = "no key yet";
        this.date = setParam("date");
    }

    // constructor for mood objects that do have a key

    public Mood(String color, String key) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        this.key = key;
        this.date = setParam("date");
    }

    // The following methods are needed for Parcelable

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeString(key);
        dest.writeInt(date);
    }

    public int compareTo(Mood other) {
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    // returns color zone of mood

    public String getColor() {
        return color;
    }

    // returns month in which mood was recorded

    public int getMonth() {
        return month;
    }

    // returns date on which mood was recorded

    public int getDate() {
        return month;
    }

    // returns year in which mood was recorded

    public int getYear() {
        return year;
    }

    // returns String representation of mood objects

    public String toString() {
        return month + "/" + date + "/" + year + ": " + color + " (" + key + ")";
    }

    // sets month, year, and date attributes for Mood objects
    // value parameter signifies which unit of time (month, date, or year) is needed

    public int setParam(String value) {

        // get current date

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        // if parameter is "month"

        if (value.equals("month")) {

            // return month in which mood was recorded

            return Integer.parseInt(date.substring(3, 5));
        }

        // if parameter is "year"

        else if (value.equals("year")) {

            // return year in which mood was recorded

            return Integer.parseInt(date.substring(6));
        }

        // if parameter is "date"

        else if (value.equals("date")) {

            // return date on which mood was recorded

            return Integer.parseInt(date.substring(0, 2));

        }

        // if parameter doesn't equal month, date, or year, return -1

        else {
            return -1;
        }

    }

    // Mood objects are equal to one another if they are recorded on the same day
    public boolean equals (Mood other) {
        return this.year == other.year && this.month == other.month && this.date == other.date;
    }

}
