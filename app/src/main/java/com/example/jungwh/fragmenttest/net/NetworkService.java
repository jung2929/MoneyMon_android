package com.example.jungwh.fragmenttest.net;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by jungwh on 2016-10-03.
 */

public class NetworkService {
    private OkHttpClient client = new OkHttpClient();
    private Request request;
    private Response response;

    public JSONObject doGetRequest(HttpUrl url) throws IOException {

        request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Boolean doPostRequest(HttpUrl url, RequestBody requestBody) throws IOException {
        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return response.isSuccessful();
        }
    }

    public Boolean doDeleteRequest(HttpUrl url, RequestBody requestBody) throws IOException {
        request = new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();
        try {
            response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return response.isSuccessful();
        }
    }
}
