package com.example.jungwh.fragmenttest.business.logic;

import com.example.jungwh.fragmenttest.business.data.InputData;
import com.example.jungwh.fragmenttest.net.dal.InputDAL;
import com.example.jungwh.fragmenttest.net.dto.InputDTO;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-10-26.
 */

public class InputService {

    private InputDAL DAL;
    public InputService() { DAL = new InputDAL(); }

    public InputData retrieve(String userId)
            throws IOException, JSONException {
        InputDTO inputDTO = DAL.retrieve(userId);
        return new InputData(inputDTO);
    }
}
