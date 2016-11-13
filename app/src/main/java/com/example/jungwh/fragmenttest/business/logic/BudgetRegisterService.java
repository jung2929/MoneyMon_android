package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.net.dal.BudgetRegisterDAL;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-11-14.
 */

public class BudgetRegisterService {
    private BudgetRegisterDAL DAL;
    public BudgetRegisterService() { DAL = new BudgetRegisterDAL(); }

    public Boolean register(String budgetDate, String budgetPrice, String userId)
            throws IOException, JSONException {
        return DAL.register(budgetDate, budgetPrice, userId);
    }
}
