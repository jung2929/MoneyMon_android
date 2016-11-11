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

public class SpendRegisterDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public SpendRegisterDAL()  { networkService = new NetworkService(); }

    public Boolean register(String spendDate, String spendPrice, String spendContents, String spendCategory, String spendMemo, String spendMethodContents, String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("spend-register")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("INPUT_DATE", spendDate);
        jsonObject.put("INPUT_DIVISION", "002");
        jsonObject.put("INPUT_PC", Integer.parseInt(spendPrice));
        jsonObject.put("INPUT_IEM", spendContents);
        jsonObject.put("INPUT_CATEGORY", spendCategory);
        jsonObject.put("INPUT_MEMO", spendMemo);
        jsonObject.put("INPUT_METHOD_CONTENTS", spendMethodContents);
        jsonObject.put("USER_ID", userId);

        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }
}
