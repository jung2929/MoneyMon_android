package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class PieChartData implements Parcelable {
    private Integer[] yValues;
    private String[] xValues;
    private boolean isSuccess;

    public PieChartData(){}


    protected PieChartData(Parcel in) {
        xValues = in.createStringArray();
        isSuccess = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(xValues);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PieChartData> CREATOR = new Creator<PieChartData>() {
        @Override
        public PieChartData createFromParcel(Parcel in) {
            return new PieChartData(in);
        }

        @Override
        public PieChartData[] newArray(int size) {
            return new PieChartData[size];
        }
    };

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Integer[] getyValues() {
        return yValues;
    }

    public void setyValues(Integer[] yValues) {
        this.yValues = yValues;
    }

    public String[] getxValues() {
        return xValues;
    }

    public void setxValues(String[] xValues) {
        this.xValues = xValues;
    }
}
