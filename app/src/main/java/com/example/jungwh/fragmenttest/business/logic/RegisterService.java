package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.net.dal.RegisterDAL;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-10-06.
 */

public class RegisterService {

    private RegisterDAL DAL;
    public RegisterService() { DAL = new RegisterDAL(); }

    public Boolean register(String userId, String userPassword, String userNm, String moblphon, String email)
            throws IOException, JSONException {
        return DAL.register(userId, userPassword, userNm, moblphon, email);
    }
}
