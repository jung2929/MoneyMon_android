package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveData implements Parcelable {
    private boolean isThereData;
    private String errMsg;
    private static Map<String, ArrayList<ListRetrieveDetailData>> map;


    public ListRetrieveData(ListRetrieveDTO DTO, Map<String, ArrayList<ListRetrieveDetailData>> listRetrieveDetailDatas) {
        if (TextUtils.equals(DTO.getErrMsg(), "") & listRetrieveDetailDatas.size() > 0) {
            setThereData(true);
            setErrMsg(DTO.getErrMsg());
        }else if (TextUtils.equals(DTO.getErrMsg(), "") & listRetrieveDetailDatas.size() == 0) {
            setThereData(false);
            setErrMsg("자료를 찾지 못하였습니다.");
        }else {
            setThereData(false);
            setErrMsg(DTO.getErrMsg());
        }
        setMap(listRetrieveDetailDatas);
    }

    protected ListRetrieveData(Parcel in) {
        isThereData = in.readByte() != 0;
        errMsg = in.readString();
    }

    public static final Creator<ListRetrieveData> CREATOR = new Creator<ListRetrieveData>() {
        @Override
        public ListRetrieveData createFromParcel(Parcel in) {
            return new ListRetrieveData(in);
        }

        @Override
        public ListRetrieveData[] newArray(int size) {
            return new ListRetrieveData[size];
        }
    };

    public static void setMap(Map<String, ArrayList<ListRetrieveDetailData>> map) {
        ListRetrieveData.map = map;
    }

    public static Map<String, ArrayList<ListRetrieveDetailData>> getMap() {
        return map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isThereData ? 1 : 0));
        dest.writeString(errMsg);
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isThereData() {
        return isThereData;
    }

    public void setThereData(boolean thereData) {
        isThereData = thereData;
    }
}
