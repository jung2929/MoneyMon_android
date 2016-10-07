package com.example.jungwh.fragmenttest.business.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.jungwh.fragmenttest.business.data.LoginData;
import com.example.jungwh.fragmenttest.net.dal.LoginDAL;
import com.example.jungwh.fragmenttest.net.dto.LoginDTO;
import com.google.gson.Gson;

import org.json.JSONException;
import java.io.IOException;


/**
 * Created by jungwh on 2016-10-03.
 */

public class LoginService {
    private static final String PREF_AUTO_LOGIN = "AUTO_LOGIN";
    private static final String PREF_LOGIN_DATA = "LOGIN_DATA";

    private LoginDAL DAL;
    public LoginService() { DAL = new LoginDAL(); }

    public LoginData login(String id, String password)
            throws IOException, JSONException {
        LoginDTO DTO = DAL.login(id, password);
        LoginData loginData = new LoginData(DTO, id, password);
        return loginData;
    }

    //자동 로그인 해제 및 Caching된 로그인 데이터 삭제
    public void logout(Context ctx) {
        saveAutoLoginOption(ctx, false);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGIN_DATA, "");
        editor.commit();
    }

    public void saveLoginData(Context ctx, LoginData loginData) {
        Gson gson = new Gson();
        if (loginData == null) {
            throw new IllegalArgumentException("LoginData cannot be empty");
        }
        String loginDataInJson = gson.toJson(loginData);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGIN_DATA, loginDataInJson);
        editor.commit();
    }

    public void saveAutoLoginOption(Context ctx, boolean autoLogin) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_AUTO_LOGIN, autoLogin);
        editor.commit();
    }

    public LoginData getCachedLoginData(Context ctx) {
        String loginDataInJson =
                getSharedPreferences(ctx).getString(PREF_LOGIN_DATA, "");
        if (TextUtils.isEmpty(loginDataInJson)) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(loginDataInJson, LoginData.class);
    }

    public boolean isAutoLoginEnabled(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_AUTO_LOGIN, false);
    }

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
}
