package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDetailDTO;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveDetailData implements Parcelable{
    private String inputDate;
    private String inputPc;
    private String inputIem;
    private String inputCatagory;
    private String inputMemo;

    public ListRetrieveDetailData (ListRetrieveDetailDTO DTO) {
        setInputDate(DTO.getInputDate());
        setInputPc(DTO.getInputPc());
        setInputIem(DTO.getInputIem());
        setInputCatagory(DTO.getInputCatagory());
        setInputMemo(DTO.getInputMemo());
    }

    protected ListRetrieveDetailData(Parcel in) {
        inputDate = in.readString();
        inputPc = in.readString();
        inputIem = in.readString();
        inputCatagory = in.readString();
        inputMemo = in.readString();
    }

    public static final Creator<ListRetrieveDetailData> CREATOR = new Creator<ListRetrieveDetailData>() {
        @Override
        public ListRetrieveDetailData createFromParcel(Parcel in) {
            return new ListRetrieveDetailData(in);
        }

        @Override
        public ListRetrieveDetailData[] newArray(int size) {
            return new ListRetrieveDetailData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inputDate);
        dest.writeString(inputPc);
        dest.writeString(inputIem);
        dest.writeString(inputCatagory);
        dest.writeString(inputMemo);
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getInputPc() {
        return inputPc;
    }

    public void setInputPc(String inputPc) {
        this.inputPc = inputPc;
    }

    public String getInputIem() {
        return inputIem;
    }

    public void setInputIem(String inputIem) {
        this.inputIem = inputIem;
    }

    public String getInputCatagory() {
        return inputCatagory;
    }

    public void setInputCatagory(String inputCatagory) {
        this.inputCatagory = inputCatagory;
    }

    public String getInputMemo() {
        return inputMemo;
    }

    public void setInputMemo(String inputMemo) {
        this.inputMemo = inputMemo;
    }
}
