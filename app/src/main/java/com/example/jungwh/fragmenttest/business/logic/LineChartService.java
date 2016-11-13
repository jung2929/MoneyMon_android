package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.LineChartData;
import com.example.jungwh.fragmenttest.net.dal.LineChartDAL;
import com.example.jungwh.fragmenttest.net.dto.LineChartDTO;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by seo on 2016-11-11.
 */
public class LineChartService {

    private LineChartDAL DAL;
    public LineChartService() { DAL = new LineChartDAL();}

    public LineChartData retrieve(String userId)
            throws IOException, JSONException {
        LineChartDTO DTO = DAL.retrieve(userId);
        LineChartData lineChartData = new LineChartData();
        ArrayList<String> arrayDateList = new ArrayList<>();
        ArrayList<Entry> arraySumPriceEntryList = new ArrayList<>();
        ArrayList<Entry> arrayBudgetEntryList = new ArrayList<>();

        JSONArray jsonArray = DTO.getData();
        if (jsonArray.length() == 0){
            arrayDateList.add("");
            arraySumPriceEntryList.add(new Entry(0, 0));
            arrayBudgetEntryList.add(new Entry(0, 0));
            lineChartData.setDateYYYYMM(arrayDateList);
            lineChartData.setsumPriceEntry(arraySumPriceEntryList);
            lineChartData.setbudgetEntry(arrayBudgetEntryList);
            lineChartData.setGraphInfo("비교 할 자료가 부족합니다.");
            lineChartData.setSuccess(false);
            return lineChartData;
        }else{
            String lastInputDate = "";
            Integer lastSumPrice = 0, lastBudget = 0;
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                arrayDateList.add(jsonObject.getString("INPUT_DATE"));
                arraySumPriceEntryList.add(new Entry(jsonObject.getInt("SUM_INPUT_PC"), i));
                arrayBudgetEntryList.add(new Entry(jsonObject.getInt("BUDGET"), i));
                if (i+1 == jsonArray.length()){
                    lastInputDate = jsonObject.getString("INPUT_DATE");
                    lastBudget = jsonObject.getInt("BUDGET");
                    lastSumPrice = jsonObject.getInt("SUM_INPUT_PC");
                }
            }

            lineChartData.setDateYYYYMM(arrayDateList);
            lineChartData.setsumPriceEntry(arraySumPriceEntryList);
            lineChartData.setbudgetEntry(arrayBudgetEntryList);

            Integer computedPrice = lastBudget - lastSumPrice;
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedSumPrice = decimalFormat.format(computedPrice);
            if (computedPrice > 0) {
                lineChartData.setGraphInfo(lastInputDate + " 설정한 예산에 비해 이번달 비용은 " + formattedSumPrice + "원 남았습니다. 하루 평균 6,300원씩 지출하길 추천합니다!");
            } else if (computedPrice == 0) {
                lineChartData.setGraphInfo("예산을 모두 소비하셨습니다.");
            }
            else if (computedPrice < 0) {
                lineChartData.setGraphInfo(lastInputDate + " 설정한 예산에 비해 이번달 비용은 " + formattedSumPrice + "원 초과했습니다. 절약하세요!");
            }

            lineChartData.setSuccess(true);
            return lineChartData;
        }
    }
}
