package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.DatabaseEntity;
import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.BarChartDTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by jungwh on 2016-11-12.
 */

public class BarChartDAL {
    private static NetworkService networkService;

    public BarChartDAL()  { networkService = new NetworkService(); }

    public BarChartDTO retrieve(String userId, Integer month, String category) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("bar-chart-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .addEncodedQueryParameter("requestMonth", month.toString())
                .addEncodedQueryParameter("requestCategory", category)
                .build();
        return new BarChartDTO(networkService.doGetRequest(url));
    }
}
