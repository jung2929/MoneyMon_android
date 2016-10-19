package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.jungwh.fragmenttest.net.dto.ListRetrieveDTO;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jungwh on 2016-10-19.
 */

public class ListRetrieveData implements Parcelable {
    private boolean isThereData;
    private String errMsg;
    private ArrayList<ListRetrieveDetailData> childList;

    public ListRetrieveData(ListRetrieveDTO DTO, ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas) {
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
        setChildList(listRetrieveDetailDatas);
    }

    protected ListRetrieveData(Parcel in) {
        isThereData = in.readByte() != 0;
        errMsg = in.readString();
        Parcelable[] parcelableArray = in.readParcelableArray(ListRetrieveDetailData.class.getClassLoader());
        if (parcelableArray != null) {
            ListRetrieveDetailData[] orderDetailArr = Arrays.copyOf(parcelableArray, parcelableArray.length, ListRetrieveDetailData[].class);
            childList = new ArrayList<ListRetrieveDetailData>(Arrays.asList(orderDetailArr));
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isThereData ? 1 : 0));
        dest.writeString(errMsg);

        ListRetrieveDetailData[] orderDetailArr = new ListRetrieveDetailData[childList.size()];
        dest.writeParcelableArray(orderDetailArr, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
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

    public ArrayList<ListRetrieveDetailData> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<ListRetrieveDetailData> childList) {
        this.childList = childList;
    }
}
