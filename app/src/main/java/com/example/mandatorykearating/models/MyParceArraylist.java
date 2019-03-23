package com.example.mandatorykearating.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyParceArraylist implements Parcelable
{
    private ArrayList<ClassModel> arrayListOfClass = new ArrayList<>();

    public MyParceArraylist()
    {
    }

    protected MyParceArraylist(Parcel in) {
        arrayListOfClass = in.createTypedArrayList(ClassModel.CREATOR);
    }

    public static final Creator<MyParceArraylist> CREATOR = new Creator<MyParceArraylist>() {
        @Override
        public MyParceArraylist createFromParcel(Parcel in) {
            return new MyParceArraylist(in);
        }

        @Override
        public MyParceArraylist[] newArray(int size) {
            return new MyParceArraylist[size];
        }
    };

    public ArrayList<ClassModel> getArrayListOfClass() {
        return arrayListOfClass;
    }

    public void setArrayListOfClass(ArrayList arrayListOfClass) {
        this.arrayListOfClass = arrayListOfClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(arrayListOfClass);
    }
}
