package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.BarChartData;
import com.example.jungwh.fragmenttest.net.dal.BarChartDAL;
import com.example.jungwh.fragmenttest.net.dto.BarChartDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by jungwh on 2016-11-12.
 */

public class BarChartService {
    private BarChartDAL DAL;
    public BarChartService() { DAL = new BarChartDAL(); }

    public BarChartData retrieve(String userId, Integer month, String category)
            throws IOException, JSONException {
        BarChartDTO DTO = DAL.retrieve(userId, month, category);
        BarChartData barChartData = new BarChartData();

        JSONArray jsonArray = DTO.getData();
        if (jsonArray.length() < 2){
            barChartData.setLastYearMonth("");
            barChartData.setLastYearPrice(0);
            barChartData.setCurrentYearMonth("");
            barChartData.setCurrentYearPrice(0);
            barChartData.setGraphInfo("비교 할 자료가 부족합니다.");
            barChartData.setSuccess(false);
            return barChartData;
        }else{
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (i == 0){
                    barChartData.setCurrentYearMonth(jsonObject.getString("INPUT_DATE"));
                    barChartData.setCurrentYearPrice(jsonObject.getInt("SUM_INPUT_PC"));
                }else if( i == 1){
                    barChartData.setLastYearMonth(jsonObject.getString("INPUT_DATE"));
                    barChartData.setLastYearPrice(jsonObject.getInt("SUM_INPUT_PC"));
                }
            }

            Integer computedPrice = barChartData.getCurrentYearPrice() - barChartData.getLastYearPrice();
            //double divisionPrice = barChartData.getCurrentYearPrice() / barChartData.getLastYearPrice();
            //double formattedPrice = Double.parseDouble(String.format("%.2f", divisionPrice));
            if (computedPrice > 0) {
                //barChartData.setGraphInfo(barChartData.getLastYearMonth() + " 대비 식비 지출이 " + computedPrice.toString() + "원(" + Double.toString(formattedPrice) + "%) 증가했습니다. 말일이 다가오는 오늘! 퇴근하고 저녁밥은 집에서 어떠세요?");
                barChartData.setGraphInfo(barChartData.getLastYearMonth() + " 대비 식비 지출이 " + computedPrice.toString() + "원 증가했습니다. 말일이 다가오는 오늘! 퇴근하고 저녁밥은 집에서 어떠세요?");
            } else if (computedPrice == 0) {
                barChartData.setGraphInfo("놀라워요! 작년이랑 동일하게 소비하셨네요~ 꾸준한 소비생활 멋져요!");
            }
            else if (computedPrice < 0) {
                //barChartData.setGraphInfo(barChartData.getLastYearMonth() + " 대비 식비 지출이 " + computedPrice.toString() + "원(" + Double.toString(formattedPrice) + "%) 감소했습니다. 절약정신이 대단하네요~ 앞으로도 열심히 절약해보세요!");
                barChartData.setGraphInfo(barChartData.getLastYearMonth() + " 대비 식비 지출이 " + computedPrice.toString() + "원 감소했습니다. 절약정신이 대단하네요~ 앞으로도 열심히 절약해보세요!");
            }

            barChartData.setSuccess(true);
            return barChartData;
        }
    }
}
