package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jungwh on 2016-11-12.
 */

public class BarChartData implements Parcelable {
    private int currentYearPrice, lastYearPrice;
    private String currentYearMonth, lastYearMonth, graphInfo;
    private boolean isSuccess = false;

    public BarChartData(){}

    protected BarChartData(Parcel in) {
        currentYearMonth = in.readString();
        lastYearMonth = in.readString();
        graphInfo = in.readString();
        isSuccess = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currentYearMonth);
        dest.writeString(lastYearMonth);
        dest.writeString(graphInfo);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BarChartData> CREATOR = new Creator<BarChartData>() {
        @Override
        public BarChartData createFromParcel(Parcel in) {
            return new BarChartData(in);
        }

        @Override
        public BarChartData[] newArray(int size) {
            return new BarChartData[size];
        }
    };

    public Integer getCurrentYearPrice() {
        return currentYearPrice;
    }

    public void setCurrentYearPrice(Integer currentYearPrice) {
        this.currentYearPrice = currentYearPrice;
    }

    public Integer getLastYearPrice() {
        return lastYearPrice;
    }

    public void setLastYearPrice(Integer lastYearPrice) {
        this.lastYearPrice = lastYearPrice;
    }

    public String getGraphInfo() {
        return graphInfo;
    }

    public void setGraphInfo(String graphInfo) {
        this.graphInfo = graphInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getLastYearMonth() {
        return lastYearMonth;
    }

    public void setLastYearMonth(String lastYearMonth) {
        this.lastYearMonth = lastYearMonth;
    }

    public String getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(String currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }
}
