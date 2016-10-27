package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.InputDTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by jungwh on 2016-10-26.
 */

public class InputDAL {
    private static NetworkService networkService;

    public InputDAL()  { networkService = new NetworkService(); }

    public InputDTO retrieve(String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("total-price-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .build();

        return new InputDTO(networkService.doGetRequest(url));
    }
}
