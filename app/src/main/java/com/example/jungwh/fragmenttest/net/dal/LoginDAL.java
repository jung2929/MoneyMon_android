package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.LoginDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by jungwh on 2016-10-03.
 */

public class LoginDAL {
    private NetworkService networkService;


    public LoginDAL()  { networkService = new NetworkService(); }

    public LoginDTO login(String userId, String userPassword) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("login")
                .addEncodedQueryParameter("requestUserId", userId)
                .addEncodedQueryParameter("requestUserPassword", userPassword)
                .build();

        return new LoginDTO(networkService.doGetRequest(url));
    }
}
