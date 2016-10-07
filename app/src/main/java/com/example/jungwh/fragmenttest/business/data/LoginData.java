package com.example.jungwh.fragmenttest.business.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.jungwh.fragmenttest.net.dto.LoginDTO;

/**
 * Created by jungwh on 2016-10-06.
 */

public class LoginData implements Parcelable {
    private boolean isValidLogin;
    private String data;
    private String errMsg;
    private String loginId;
    private String loginPassword;
    private Boolean result;

    public LoginData() {}

    public LoginData(LoginDTO DTO, String id, String password) {
        setValidLogin( TextUtils.equals(DTO.getErrMsg(), "") && DTO.getResult());

        setData(DTO.getData());
        setErrMsg(DTO.getErrMsg());
        setResult(DTO.getResult());

        setLoginId(id);
        setLoginPassword(password);

    }

    protected LoginData(Parcel in) {
        isValidLogin = in.readByte() != 0;
        data = in.readString();
        errMsg = in.readString();
        loginId = in.readString();
        loginPassword = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isValidLogin ? 1 : 0));
        dest.writeString(data);
        dest.writeString(errMsg);
        dest.writeString(loginId);
        dest.writeString(loginPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    public boolean isValidLogin() {
        return isValidLogin;
    }
    public void setValidLogin(boolean isValidLogin) {
        this.isValidLogin = isValidLogin;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
}
