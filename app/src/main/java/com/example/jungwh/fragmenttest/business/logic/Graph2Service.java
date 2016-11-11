package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.Graph2Data;
import com.example.jungwh.fragmenttest.net.dal.Graph2DAL;
import com.example.jungwh.fragmenttest.net.dto.Graph2DTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by seo on 2016-11-11.
 */
public class Graph2Service {

    private Graph2DAL DAL;
    public  Graph2Service() { DAL = new Graph2DAL();}

    public Graph2Data graph2(String inputMonth, String userId,String listExpense, String spendCategory)
            throws IOException, JSONException {
        //Log.i("progressCheck","check");
        Graph2DTO DTO = DAL.selectInfo1(inputMonth, userId, listExpense, spendCategory);
        Graph2Data graph2Data = new Graph2Data(DTO, inputMonth , userId, listExpense, spendCategory);

        ArrayList<String> arrayList = new ArrayList<>();    ///////배열..................

        JSONArray jsonArray = DTO.getData();
        for (int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            // arrayList.add(jsonObject.getString("CL_CODE_NM"));
        }


        return graph2Data;
    }
}
