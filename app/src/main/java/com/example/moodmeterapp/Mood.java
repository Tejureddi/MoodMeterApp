package com.example.moodmeterapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mood implements Parcelable, Comparable<Mood> {

    private String color;
    private int month;
    private String key;
    private int year;

    public static final Parcelable.Creator<Mood> CREATOR = new Parcelable.Creator<Mood>() {

        @Override
        public Mood createFromParcel(Parcel parcel) {
            return new Mood(parcel);
        }

        @Override
        public Mood[] newArray(int size) {
            return new Mood[size];
        }
    };

    public Mood(Parcel parcel) {
        color = parcel.readString();
        month = parcel.readInt();
        key = parcel.readString();
        year = parcel.readInt();
    }

    public Mood() {
        color = "none";
        month = 0;
        year = 0;
    }

    public Mood (String color) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        key = "no key yet";
    }

    public Mood(String color, String key) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        this.key = key;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeInt(month);
        dest.writeString(key);
        dest.writeInt(year);
    }

    public int compareTo(Mood other) {
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public String getColor() {
        return color;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String toString() {
        return month + "/" + year + ": " + color + " (" + key + ")";
    }

    public int setParam(String value) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        if (value.equals("month")) {
            return Integer.parseInt(date.substring(3, 5));
        }
        else if (value.equals("year")) {
            return Integer.parseInt(date.substring(6));
        }
        else {
            return -1;
        }
    }

}
