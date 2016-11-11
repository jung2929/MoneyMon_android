package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;

import com.example.jungwh.fragmenttest.net.dto.Graph1DTO;

import org.json.JSONArray;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class Graph1Data {
    private String  data;
    private String errMsg;

    private String userId;
    private String inputCategory;
    private Boolean result;

    public Graph1Data(Graph1DTO DTO, String userId, String inputCategory){

//        setData(DTO.getData());
        setErrMsg(DTO.getErrMsg());

        setuserId(userId);
        setInputCategory(inputCategory);

    }

    protected Graph1Data(Parcel in) {
        data = in.readString();
        errMsg = in.readString();
        userId = in.readString();
        inputCategory = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeString(errMsg);
        dest.writeString(userId);
        dest.writeString(inputCategory);
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getuserId() { return userId;    }
    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getInputCategory() {
        return inputCategory;
    }
    public void setInputCategory(String inputCategory) {
        this.inputCategory = inputCategory;
    }


}
