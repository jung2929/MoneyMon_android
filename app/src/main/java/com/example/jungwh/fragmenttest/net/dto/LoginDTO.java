package com.example.jungwh.fragmenttest.net.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jungwh on 2016-10-07.
 */

public class LoginDTO {
    private String data;
    private String errMsg;
    private Boolean result;

    public LoginDTO() {}

    public LoginDTO(JSONObject responseJson)
            throws JSONException {
        setErrMsg(responseJson.getString("errorMsg"));
        setData(responseJson.getString("data"));

        JSONObject jsonObject = responseJson.getJSONObject("data");
        if(jsonObject.get("result").equals(true)){
            setResult(true);
        }else{
            setResult(false);
        }
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

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
}
