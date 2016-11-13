package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.LineChartDTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;

/**
 * Created by seo on 2016-11-09.
 */
public class LineChartDAL {
    private  static NetworkService networkService;

    public LineChartDAL() { networkService = new NetworkService(); }

    public LineChartDTO retrieve(String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("line-chart-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .build();
        return new LineChartDTO(networkService.doGetRequest(url));
    }
}