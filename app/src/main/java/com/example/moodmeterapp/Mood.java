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
    private int date;

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
        date = parcel.readInt();
    }

    public Mood() {
        color = "none";
        month = 0;
        year = 0;
        date = 0;
    }

    public Mood (String color) {
        this.color = color;
        this.month = setParam("month");
        this.year = setParam("year");
        this.date = setParam("date");
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

    public int getDate() {
        return date;
    }

    public String toString() {
        return date + "/" + month + "/" + year + ": " + color + " (" + key + ")";
    }

    public int setParam(String value) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dates = sdf.format(new Date());
        if (value.equals("month")) {
            return Integer.parseInt(dates.substring(3, 5));
        }
        else if (value.equals("year")) {
            return Integer.parseInt(dates.substring(6));
        }
        else if(value.equals("date")) {
            return Integer.parseInt(dates.substring(0, 2));
        }
        else {
            return -1;
        }
    }

}
