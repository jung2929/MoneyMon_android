package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.CateRetrieveDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jungwh on 2016-11-06.
 */

public class CateRetrieveDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public CateRetrieveDAL()  { networkService = new NetworkService(); }

    public CateRetrieveDTO retrieve(String userId, String inputDivision) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("category-retrieve")
                .addEncodedQueryParameter("requestUserId", userId)
                .addEncodedQueryParameter("requestInputDivision", inputDivision)
                .build();

        return new CateRetrieveDTO(networkService.doGetRequest(url));
    }

    public Boolean register(String userId, String inputDivision, String data) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("category-register")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("USER_ID", userId);
        jsonObject.put("INPUT_DIVISION", inputDivision);
        jsonObject.put("CL_CODE_NM", data);


        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }

    public Boolean delete(String userId, String inputDivision, String data) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(9000)
                .addPathSegment("category-delete")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("USER_ID", userId);
        jsonObject.put("INPUT_DIVISION", inputDivision);
        jsonObject.put("CL_CODE_NM", data);


        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doDeleteRequest(url, requestBody);
    }
}
