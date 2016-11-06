package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jungwh on 2016-11-06.
 */

public class CateRetrieveData implements Parcelable {
    private ArrayList<String> cateList;

    public CateRetrieveData(){}

    protected CateRetrieveData(Parcel in) {
        cateList = in.createStringArrayList();
    }

    public static final Creator<CateRetrieveData> CREATOR = new Creator<CateRetrieveData>() {
        @Override
        public CateRetrieveData createFromParcel(Parcel in) {
            return new CateRetrieveData(in);
        }

        @Override
        public CateRetrieveData[] newArray(int size) {
            return new CateRetrieveData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(cateList);
    }

    public ArrayList<String> getCateList() {
        return cateList;
    }

    public void setCateList(ArrayList<String> cateList) {
        this.cateList = cateList;
    }
}