package com.example.jungwh.fragmenttest.net.dto;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by jungwh on 2016-10-20.
 */

public class ListRetrieveDetailDTO {
    private String inputDate;
    private Integer inputPc;
    private String inputIem;
    private String inputCategory;
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

    public Integer getInputPc() {
        return inputPc;
    }

    public void setInputPc(Integer inputPc) {
        this.inputPc = inputPc;
    }

    public String getInputIem() {
        return inputIem;
    }

    public void setInputIem(String inputIem) {
        this.inputIem = inputIem;
    }

    public String getInputCategory() {
        return inputCategory;
    }

    public void setInputCategory(String inputCategory) {
        this.inputCategory = inputCategory;
    }

    public String getInputMemo() {
        return inputMemo;
    }

    public void setInputMemo(String inputMemo) {
        this.inputMemo = inputMemo;
    }
}
