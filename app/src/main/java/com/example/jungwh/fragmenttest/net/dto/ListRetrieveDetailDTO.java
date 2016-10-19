package com.example.jungwh.fragmenttest.net.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jungwh on 2016-10-20.
 */

public class ListRetrieveDetailDTO {
    private String inputDate;
    private String inputPc;
    private String inputIem;
    private String inputCatagory;
    private String inputMemo;

    public ListRetrieveDetailDTO() {}

    public ListRetrieveDetailDTO(JSONArray responseJson)
            throws JSONException {

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
