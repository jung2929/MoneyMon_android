package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.PieChartData;
import com.example.jungwh.fragmenttest.net.dal.PieChartDAL;
import com.example.jungwh.fragmenttest.net.dto.PieChartDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class PieChartService {

    private PieChartDAL DAL;
    public PieChartService() { DAL = new PieChartDAL();}

    public PieChartData retrieve(String userId , Integer month)
            throws IOException, JSONException {
        PieChartDTO DTO = DAL.retrieve(userId, month);
        PieChartData pieChartData = new PieChartData();
        ArrayList<String> arrayCategoryList = new ArrayList<>();
        ArrayList<Integer> arraySumPriceList = new ArrayList<>();

        JSONArray jsonArray = DTO.getData();
        if (jsonArray.length() == 0){
            arrayCategoryList.add("Non-Data");
            arraySumPriceList.add(100);
            pieChartData.setSuccess(false);
        }else{
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                arrayCategoryList.add(jsonObject.getString("INPUT_CATEGORY"));
                arraySumPriceList.add(jsonObject.getInt("SUM_INPUT_PC"));
            }
            pieChartData.setSuccess(true);
        }
        String[] categoryArray = new String[arrayCategoryList.size()];
        Integer[] sumPriceArray = new Integer[arraySumPriceList.size()];
        pieChartData.setxValues(arrayCategoryList.toArray(categoryArray));
        pieChartData.setyValues(arraySumPriceList.toArray(sumPriceArray));

        return pieChartData;
    }
}