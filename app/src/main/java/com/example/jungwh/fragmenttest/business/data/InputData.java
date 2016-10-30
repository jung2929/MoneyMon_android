package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jungwh.fragmenttest.net.dto.InputDTO;

/**
 * Created by NEXOL2-PC on 2016-10-26.
 */

public class InputData implements Parcelable {
    private String totalPrice;

    public InputData() {}

    public InputData(InputDTO DTO) {
        setTotalPrice(DTO.getTotalPrice());
    }

    public static final Creator<InputData> CREATOR = new Creator<InputData>() {
        @Override
        public InputData createFromParcel(Parcel in) {
            return new InputData(in);
        }

        @Override
        public InputData[] newArray(int size) {
            return new InputData[size];
        }
    };

    protected InputData(Parcel in) {
        in.writeString(totalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalPrice);
    }

    public String getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
