package com.rammelbalagtas.finalproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class House implements Parcelable {

    // Class Attributes
    private int price;
    private String location;
    private boolean nearSchool;
    private ArrayList<String> previousOwners;

    public House(int price, String location, boolean nearSchool, ArrayList<String> previousOwners) {
        this.price = price;
        this.location = location;
        this.nearSchool = nearSchool;
        this.previousOwners = previousOwners;
    }

    protected House(Parcel in) {
        price = in.readInt();
        location = in.readString();
        nearSchool = in.readInt() == 1;
        previousOwners = in.createStringArrayList();
    }

    public static final Creator<House> CREATOR = new Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(price);
        parcel.writeString(location);
        parcel.writeInt(nearSchool ? 1 : 0);
        parcel.writeStringList(previousOwners);
    }

    public int getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsNearSchool() {
        return nearSchool;
    }

    public ArrayList<String> getPreviousOwners() {
        return previousOwners;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNearSchool(boolean nearSchool) {
        this.nearSchool = nearSchool;
    }

    public void setPreviousOwners(ArrayList<String> previousOwners) {
        this.previousOwners = previousOwners;
    }
}
