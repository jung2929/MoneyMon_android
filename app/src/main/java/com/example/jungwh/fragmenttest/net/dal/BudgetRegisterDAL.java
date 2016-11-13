package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.DatabaseEntity;
import com.example.jungwh.fragmenttest.net.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jungwh on 2016-11-13.
 */

public class BudgetRegisterDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public BudgetRegisterDAL()  { networkService = new NetworkService(); }

    public Boolean register(String budgetDate, String budgetPrice, String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("budget-register")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DATE_YYYYMM", budgetDate);
        jsonObject.put("BUDGET", Integer.parseInt(budgetPrice));
        jsonObject.put("USER_ID", userId);

        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }
}
