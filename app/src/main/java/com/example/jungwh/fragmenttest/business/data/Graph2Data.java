package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;

import com.example.jungwh.fragmenttest.net.dto.Graph1DTO;
import com.example.jungwh.fragmenttest.net.dto.Graph2DTO;
/**
 * Created by seo on 2016-11-09.
 */
public class Graph2Data {

    private String data;
    private String errMsg;

    private String userId;
    private String listExpense;
    private String inputMonth;
    private String spendCategory;


    private Boolean result;



    public Graph2Data(Graph2DTO DTO, String inputMonth , String userId, String listExpense, String spendCategory){
        //setData(DTO.getData());
        setErrMsg(DTO.getErrMsg());

        setUserId(userId);
        setInputMonth(inputMonth);
        setListExpense(listExpense);
        setSpendCategory(spendCategory);



    }

    protected Graph2Data(Parcel in) {
        data = in.readString();
        errMsg = in.readString();

        userId = in.readString();
        inputMonth = in.readString();
        listExpense = in.readString();
        spendCategory = in.readString();
    }

    public void writeToParcel (Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeString(errMsg);

        dest.writeString(userId);
        dest.writeString(inputMonth);
        dest.writeString(listExpense);
        dest.writeString(spendCategory);

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



    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInputMonth() {
        return inputMonth;
    }
    public void setInputMonth(String inputMonth) {
        this.inputMonth = inputMonth;
    }

    public String getListExpense() {
        return listExpense;
    }
    public void setListExpense(String listExpense) {
        this.listExpense = listExpense;
    }

    public String getSpendCategory() {
        return spendCategory;
    }
    public void setSpendCategory(String spendCategory) {
        this.spendCategory = spendCategory;
    }
}
