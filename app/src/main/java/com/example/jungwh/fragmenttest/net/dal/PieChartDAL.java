package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.DatabaseEntity;
import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.PieChartDTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class PieChartDAL {
    private  static NetworkService networkService;

    public PieChartDAL() { networkService = new NetworkService(); }

    public PieChartDTO retrieve(String userId, Integer month) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("pie-chart-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .addEncodedQueryParameter("requestMonth", month.toString())
                .build();
        return new PieChartDTO(networkService.doGetRequest(url));
    }
}
