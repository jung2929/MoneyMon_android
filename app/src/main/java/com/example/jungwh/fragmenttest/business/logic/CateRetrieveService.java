package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.CateRetrieveData;
import com.example.jungwh.fragmenttest.net.dal.CateRetrieveDAL;
import com.example.jungwh.fragmenttest.net.dto.CateRetrieveDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jungwh on 2016-11-06.
 */

public class CateRetrieveService {
    private CateRetrieveDAL DAL;
    public CateRetrieveService() { DAL = new CateRetrieveDAL(); }

    public CateRetrieveData retrieve(String userId, String inputDivision)
            throws IOException, JSONException {
        CateRetrieveDTO DTO = DAL.retrieve(userId, inputDivision);
        CateRetrieveData spendCateRetrieveData = new CateRetrieveData();
        ArrayList<String> arrayList = new ArrayList<>();

        JSONArray jsonArray = DTO.getData();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            arrayList.add(jsonObject.getString("CL_CODE_NM"));
        }

        spendCateRetrieveData.setCateList(arrayList);
        return spendCateRetrieveData;
    }

    public boolean register(String userId, String inputDivision, String data)
            throws IOException, JSONException {
        return DAL.register(userId, inputDivision, data);
    }

    public boolean delete(String userId, String inputDivision, String data)
            throws IOException, JSONException {
        return DAL.delete(userId, inputDivision, data);
    }
}
