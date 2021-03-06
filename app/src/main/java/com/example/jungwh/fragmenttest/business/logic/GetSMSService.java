package com.example.jungwh.fragmenttest.business.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import com.example.jungwh.fragmenttest.net.dal.InsertSMSInfoDAL;
import com.example.jungwh.fragmenttest.net.dto.GetSMSData;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;


/**
 * Created by seo on 2016-11-04.
 */
public class GetSMSService extends BroadcastReceiver {

    private static final String ROSA_ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private InsertSMSInfoDAL DAL = new InsertSMSInfoDAL();
    @Override
    public void onReceive(Context context, Intent intent) {
        String getMessage = intent.getAction();
        String receiveMsgInfo = "";
        if (getMessage.equals(ROSA_ACTION_SMS_RECEIVED)) {
            // Log.w("rosa", "getMessage");
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                // Log.w("rosa", "Message bundle is null !!");
                return;
            } else {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj == null) {
                    // Log.w("rosa", "Message pdu's are null !!");
                    return;
                }

                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                Log.i("rosa", "retrieving : " + pdusObj.length + "messages");

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    Log.i("rosa", messages[i].getOriginatingAddress()
                            + "::" + messages[i].getMessageBody());
                    receiveMsgInfo += messages[i].getMessageBody();
                    if (i != messages.length - 1) {
                        receiveMsgInfo += " ";
                    }
                }
                try {
                    getSMSData(receiveMsgInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getSMSData(String receiveMsgInfo) throws IOException, JSONException {

        if (true) {
            GetSMSData getSMSData = new GetSMSData(receiveMsgInfo);
            Map<String, Object> getSMSInfo = getSMSData.tpChkCd();
            // Log.i("rosa", "---getSMSInfo----"+getSMSInfo);
            insertSMSInfo(getSMSInfo);
        } else {
            return;

        }
    }

    public Boolean insertSMSInfo(Map<String, Object> getSMSInfo )
            throws IOException, JSONException {
        // Log.i("rosa", "---getSMSInfo----"+getSMSInfo);
        return DAL.insertSMSInfo(getSMSInfo);
    }
}