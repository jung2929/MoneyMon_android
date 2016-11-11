package com.example.jungwh.fragmenttest.net.dal;

import android.util.Log;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.Graph1DTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class Graph1DAL {
    private  static NetworkService networkService;

    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public Graph1DAL() { networkService = new NetworkService(); }

    public Graph1DTO graph1(String userId, String inputCatgory ) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("gragh1-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .addEncodedQueryParameter("requestInputCategory", inputCatgory)
                .build();

        Log.i("HIM","Are you sucess_DAL");

        return new Graph1DTO( networkService.doGetRequest(url));
    }
}
