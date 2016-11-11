package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.net.dal.IncomeRegisterDAL;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-10-29.
 */

public class IncomeRegisterService {
    private IncomeRegisterDAL DAL;
    public IncomeRegisterService() { DAL = new IncomeRegisterDAL(); }

    public Boolean register(String incomeDate, String incomePrice, String incomeContents, String incomeMemo, String incomeCategory, String userId)
            throws IOException, JSONException {
        return DAL.register(incomeDate, incomePrice, incomeContents, incomeMemo, incomeCategory, userId);
    }
}