package com.example.jungwh.fragmenttest.net.dal;

import com.example.jungwh.fragmenttest.net.DatabaseEntity;
import com.example.jungwh.fragmenttest.net.NetworkService;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;
import com.example.jungwh.fragmenttest.net.dto.LoginDTO;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveDAL {
    private static NetworkService networkService;

    public ListRetrieveDAL()  { networkService = new NetworkService(); }

    public ListRetrieveDTO listRetrieve(String inputDateFrom, String inputDateTo, String userId) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("list-retrieve")
                .addEncodedQueryParameter("requestInputDateFrom", inputDateFrom)
                .addEncodedQueryParameter("requestInputDateTo", inputDateTo)
                .addEncodedQueryParameter("requestUserId", userId)
                .build();
        return new ListRetrieveDTO(networkService.doGetRequest(url));
    }
}
