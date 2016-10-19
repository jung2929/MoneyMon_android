package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.ListRetrieveData;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;
import com.example.jungwh.fragmenttest.net.dal.ListRetrieveDAL;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;
import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDetailDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveService {
    private ListRetrieveDAL DAL;
    public ListRetrieveService() { DAL = new ListRetrieveDAL(); }

    public ListRetrieveData listRetrieve(String inputDateFrom, String inputDateTo, String id)
            throws IOException, JSONException {
        ListRetrieveDTO DTO = DAL.listRetrieve(inputDateFrom, inputDateTo, id);
        ArrayList<ListRetrieveDetailDTO> listRetrieveDetailDTOs = new ArrayList<>();
        ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas = new ArrayList<>();

        JSONArray jsonArray = DTO.getData();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            ListRetrieveDetailDTO listRetrieveDetailDTO = new ListRetrieveDetailDTO();
            listRetrieveDetailDTO.setInputDate(jsonObject.getString("INPUT_DATE"));
            listRetrieveDetailDTO.setInputPc(jsonObject.getString("INPUT_PC"));
            listRetrieveDetailDTO.setInputIem(jsonObject.getString("INPUT_IEM"));
            listRetrieveDetailDTO.setInputCatagory(jsonObject.getString("INPUT_CATAGORY"));
            listRetrieveDetailDTO.setInputMemo(jsonObject.getString("INPUT_MEMO"));
            listRetrieveDetailDTOs.add(listRetrieveDetailDTO);
        }

        for (ListRetrieveDetailDTO listRetrieveDetailDTO : listRetrieveDetailDTOs) {
            listRetrieveDetailDatas.add(new ListRetrieveDetailData(listRetrieveDetailDTO));
        }

        return new ListRetrieveData(DTO, listRetrieveDetailDatas);
    }
}
