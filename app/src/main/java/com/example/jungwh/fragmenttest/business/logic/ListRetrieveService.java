package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.ListRetrieveData;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;
import com.example.jungwh.fragmenttest.net.dal.ListRetrieveDAL;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDetailDTO;
import com.example.jungwh.fragmenttest.util.Tuple;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveService {
    private ListRetrieveDAL DAL;
    public ListRetrieveService() { DAL = new ListRetrieveDAL(); }

    public Tuple<ListRetrieveData, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>>> listRetrieve(String inputDateFrom, String inputDateTo, String id)
            throws IOException, JSONException {
        ListRetrieveDTO DTO = DAL.listRetrieve(inputDateFrom, inputDateTo, id);
        ArrayList<ListRetrieveDetailDTO> listRetrieveDetailDTOs = new ArrayList<>();
        ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas = new ArrayList<>();

        JSONArray jsonArray = DTO.getData();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            ListRetrieveDetailDTO listRetrieveDetailDTO = new ListRetrieveDetailDTO();
            listRetrieveDetailDTO.setInputDate(jsonObject.getString("INPUT_DATE"));
            listRetrieveDetailDTO.setInputPc(jsonObject.getInt("INPUT_PC"));
            listRetrieveDetailDTO.setInputIem(jsonObject.getString("INPUT_IEM"));
            listRetrieveDetailDTO.setInputCategory(jsonObject.getString("INPUT_CATEGORY"));
            listRetrieveDetailDTO.setInputMemo(jsonObject.getString("INPUT_MEMO"));
            listRetrieveDetailDTOs.add(listRetrieveDetailDTO);
        }

        /*Map<String, ArrayList<ListRetrieveDetailDTO>> detailDTOMap =  new HashMap<>();
        for (ListRetrieveDetailDTO detailDTO : listRetrieveDetailDTOs) {
            String key = detailDTO.getInputDate();
            if (detailDTOMap.get(key) == null) {
                detailDTOMap.put(key, new ArrayList<ListRetrieveDetailDTO>());
            }
            detailDTOMap.get(key).add(detailDTO);
        }*/
        LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> detailMap =  new LinkedHashMap<>();
        for (ListRetrieveDetailDTO detailDTO : listRetrieveDetailDTOs) {
            String key = detailDTO.getInputDate();
            if (detailMap.get(key) == null) {
                detailMap.put(key, new ArrayList<ListRetrieveDetailData>());
            }
            detailMap.get(key).add(new ListRetrieveDetailData(detailDTO));
        }

        /*for (ListRetrieveDetailDTO listRetrieveDetailDTO : listRetrieveDetailDTOs) {
            listRetrieveDetailDatas.add(new ListRetrieveDetailData(listRetrieveDetailDTO));
        }*/

        Tuple<ListRetrieveData, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>>> tupleResult = new Tuple(new ListRetrieveData(DTO, detailMap), detailMap);

        return tupleResult;
    }
}
