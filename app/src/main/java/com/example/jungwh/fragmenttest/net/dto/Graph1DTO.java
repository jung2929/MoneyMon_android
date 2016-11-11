package com.example.jungwh.fragmenttest.net.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class Graph1DTO {
    private JSONArray data;
    private String errMsg;

    public Graph1DTO(JSONObject responseJson)
            throws JSONException {
        setErrMsg(responseJson.getString("errorMsg"));
        setData((JSONArray ) responseJson.get("data"));
    }


    public JSONArray getData() {
        return data;
    }

    public void  setData(JSONArray data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
