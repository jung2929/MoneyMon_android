package com.example.jungwh.fragmenttest.business.logic;

import android.util.Log;

import com.example.jungwh.fragmenttest.business.data.Graph1Data;
import com.example.jungwh.fragmenttest.net.dal.Graph1DAL;
import com.example.jungwh.fragmenttest.net.dto.Graph1DTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 김한빛 on 2016-11-09.
 */

public class Graph1Service {

        private Graph1DAL DAL;
        public  Graph1Service() { DAL = new Graph1DAL();}

    public Graph1Data graph1(String userId ,  String inputCategory)
            throws IOException, JSONException {

        Graph1DTO DTO = DAL.graph1(userId, inputCategory);
        Graph1Data graph1Data = new Graph1Data(DTO, userId ,  inputCategory);

        Log.i("HIM","Are you sucess_Service");

        ArrayList<String> arrayList = new ArrayList<>();

       JSONArray jsonArray = DTO.getData();

        for (int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            arrayList.add(jsonObject.getString("CL_CODE_NM"));
        }

        return graph1Data;
        }

}