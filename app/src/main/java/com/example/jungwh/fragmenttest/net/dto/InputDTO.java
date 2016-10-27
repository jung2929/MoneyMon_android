package com.example.jungwh.fragmenttest.net.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jungwh on 2016-10-26.
 */

public class InputDTO {
    private String data;
    private String errMsg;
    private Integer totalPrice;

    public InputDTO() {}

    public InputDTO(JSONObject responseJson)
            throws JSONException {
        setErrMsg(responseJson.getString("errorMsg"));
        setData(responseJson.getString("data"));

        JSONArray jsonArray = responseJson.getJSONArray("data");
        setTotalPrice(Integer.parseInt(jsonArray.getJSONObject(0).getString("TOTAL_PRICE").toString()));
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
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
}
