package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.net.dal.SpendRegisterDAL;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-10-29.
 */

public class SpendRegisterService {
    private SpendRegisterDAL DAL;
    public SpendRegisterService() { DAL = new SpendRegisterDAL(); }

    public Boolean register(String spendDate, String spendPrice, String spendContents, String spendMemo, String spendCategory, String spendMethodContents, String userId)
            throws IOException, JSONException {
        return DAL.register(spendDate, spendPrice, spendContents, spendMemo, spendCategory, spendMethodContents, userId);
    }
}