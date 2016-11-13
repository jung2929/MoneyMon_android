package com.example.jungwh.fragmenttest.net.dal;

import android.util.Log;

import com.example.jungwh.fragmenttest.net.DatabaseEntity;
import com.example.jungwh.fragmenttest.net.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by seo on 2016-11-07.
 */
public class InsertSMSInfoDAL {
    private static NetworkService networkService;
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public InsertSMSInfoDAL()  { networkService = new NetworkService(); }

    public Boolean insertSMSInfo(Map<String, Object> getSMSInfo) throws IOException, JSONException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(DatabaseEntity.getInstance())
                .port(9000)
                .addPathSegment("insertSMSInfo")
                .build();

        JSONObject jsonObject = new JSONObject();
        Iterator<String> iterator = getSMSInfo.keySet().iterator();
        while(iterator.hasNext()){
            String key = (String) iterator.next();
            jsonObject.put(key, getSMSInfo.get(key));
        }
        Log.i("rosa", "DB param : "+jsonObject.toString());
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        return networkService.doPostRequest(url, requestBody);
    }
}