package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by seo on 2016-11-09.
 */
public class LineChartData implements Parcelable{
    private ArrayList<Entry> sumPriceEntry, budgetEntry;
    private ArrayList<String> dateYYYYMM;
    private String graphInfo;
    private boolean isSuccess = false;

    public LineChartData(){}

    protected LineChartData(Parcel in) {
        sumPriceEntry = in.createTypedArrayList(Entry.CREATOR);
        budgetEntry = in.createTypedArrayList(Entry.CREATOR);
        dateYYYYMM = in.createStringArrayList();
        graphInfo = in.readString();
        isSuccess = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(sumPriceEntry);
        dest.writeTypedList(budgetEntry);
        dest.writeStringList(dateYYYYMM);
        dest.writeString(graphInfo);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LineChartData> CREATOR = new Creator<LineChartData>() {
        @Override
        public LineChartData createFromParcel(Parcel in) {
            return new LineChartData(in);
        }

        @Override
        public LineChartData[] newArray(int size) {
            return new LineChartData[size];
        }
    };

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getGraphInfo() {
        return graphInfo;
    }

    public void setGraphInfo(String graphInfo) {
        this.graphInfo = graphInfo;
    }

    public ArrayList<Entry> getsumPriceEntry() {
        return sumPriceEntry;
    }

    public void setsumPriceEntry(ArrayList<Entry> sumPriceEntry) {
        this.sumPriceEntry = sumPriceEntry;
    }

    public ArrayList<Entry> getbudgetEntry() {
        return budgetEntry;
    }

    public void setbudgetEntry(ArrayList<Entry> budgetEntry) {
        this.budgetEntry = budgetEntry;
    }

    public ArrayList<String> getDateYYYYMM() {
        return dateYYYYMM;
    }

    public void setDateYYYYMM(ArrayList<String> dateYYYYMM) {
        this.dateYYYYMM = dateYYYYMM;
    }
}
