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
 * Created by jungwh on 2016-10-06.
 */

public class RegisterDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public RegisterDAL()  { networkService = new NetworkService(); }

    public Boolean register(String userId, String userPassword, String userNm, String moblphon, String email) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("register")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("USER_ID", userId);
        jsonObject.put("USER_PASSWORD", userPassword);
        jsonObject.put("USER_NM", userNm);
        jsonObject.put("MOBLPHON", moblphon);
        jsonObject.put("EMAIL", email);

        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }
}
