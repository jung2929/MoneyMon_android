package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.Graph2DTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;

/**
 * Created by seo on 2016-11-09.
 */
public class Graph2DAL {

    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public Graph2DAL()  { networkService = new NetworkService(); }

    public Graph2DTO selectInfo1(String inputMonth , String userId, String listExpense, String spendCategory) throws IOException, JSONException { //graph1DAL처럼 selectInfo로 해도되는지..아니면 selectInfo1으로 바꿔야하는지..
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("graph2-retrieve")
                .addEncodedQueryParameter("requestUserId", userId) //사용자id
                .addEncodedQueryParameter("requestInputMonth", inputMonth) //월
                .addEncodedQueryParameter("requestListExpense",listExpense ) //지출금액리스트
                .addEncodedQueryParameter("requestSpendCategory", spendCategory) //지출 카테고리
                .build();
        return new Graph2DTO(networkService.doGetRequest(url));


    }


}
