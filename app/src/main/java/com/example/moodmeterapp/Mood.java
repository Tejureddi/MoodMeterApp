package com.example.moodmeterapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

// The Mood Class saves a user's mood and data about that mood as an object.
// The Mood Class implements Parcelable to send and receive objects between activities.


public class Mood implements Parcelable, Comparable<Mood> {

    private String color; // color zone of mood (red, green, yellow, or blue)
    private int month; // month in which mood is recorded
    private String key; // key for storage in firebase
    private int year; // year in which mood is recorded
    private int date; // day on which mood is recorded
    private String user; // the id of the user who records the mood

    // Parcelable.CREATOR interface
    // this is used to send and receive mood objects between activities

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
        user = parcel.readString();
    }

    // default constructor with no parameters

    public Mood() {
        color = "none";
        month = 0;
        year = 0;
        date = 0;
        user = "none";
    }

    // constructor for mood objects that don't have a key

    public Mood (String color) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        key = "no key yet";
        date = setParam("date");
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // constructor for mood objects that do have a key

    public Mood(String color, String key) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        this.key = key;
        this.date = setParam("date");
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // The following methods are needed for Parcelable

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeString(key);
        dest.writeInt(date);
        dest.writeString(user);
    }

    // compareTo(Mood other) returns zero because we didn't have to use this method while running our program

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

    // returns year in which mood was recorded

    public int getYear() {
        return year;
    }

    // returns day in which mood was recorded

    public int getDate() {
        return date;
    }

    // returns id of user who recorded the mood

    public String getUser() {
        return user;
    }

    // returns String representation of mood objects

    public String toString() {
        return date + "/" + month + "/" + year + ": " + color + " (" + key + ")";
    }

    // sets month, year, and date attributes for Mood objects
    // value parameter signifies which unit of time (month, date, or year) is needed

    public int setParam(String value) {

        // get current date using simple date format

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

        else if (value.equals("date")) {
            return Integer.parseInt(date.substring(0,2));
        }

        // if parameter doesn't equal mood, date, or year, return -1

        else {
            return -1;
        }

    }

    // Mood objects are equal to one another if they are recorded on the same day by the same user

    public boolean equals (Mood other) {
        System.out.println(this.getMonth());
        System.out.println(other.getMonth());
        return this.getYear() == other.getYear() && this.getMonth() == other.getMonth() && this.getDate() == other.getDate() && this.getUser().equals(other.getUser());
    }

}


