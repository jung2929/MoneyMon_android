package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jungwh.fragmenttest.net.dto.InputDTO;

/**
 * Created by NEXOL2-PC on 2016-10-26.
 */

public class InputData implements Parcelable {
    private Integer totalPrice;

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
        in.writeInt(totalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalPrice);
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
