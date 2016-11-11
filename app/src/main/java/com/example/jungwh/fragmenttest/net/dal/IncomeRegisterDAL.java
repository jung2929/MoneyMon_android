package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jungwh on 2016-10-29.
 */

public class IncomeRegisterDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public IncomeRegisterDAL()  { networkService = new NetworkService(); }

    public Boolean register(String incomeDate, String incomePrice, String incomeContents, String incomeMemo, String incomeCategory, String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("income-register")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("INPUT_DATE", incomeDate);
        jsonObject.put("INPUT_DIVISION", "001");
        jsonObject.put("INPUT_PC", Integer.parseInt(incomePrice));
        jsonObject.put("INPUT_IEM", incomeContents);
        jsonObject.put("INPUT_CATEGORY", incomeCategory);
        jsonObject.put("INPUT_MEMO", incomeMemo);
        jsonObject.put("USER_ID", userId);

        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }
}
