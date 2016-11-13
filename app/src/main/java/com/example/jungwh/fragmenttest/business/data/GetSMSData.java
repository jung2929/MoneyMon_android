package com.example.jungwh.fragmenttest.net.dto;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by seo on 2016-11-04.
 */
public class GetSMSData {
    private String receiveMsgInfo;

    public GetSMSData(String receiveMsgInfo) {
        this.receiveMsgInfo=receiveMsgInfo;
        Log.i("rosa","receiveMsgInfo="+ receiveMsgInfo);
    }

    public  Map<String, Object> tpChkCd(){

        if(receiveMsgInfo.contains("[Web발신]")||receiveMsgInfo.contains("[web발신]")){
            //체크 카드일때
            if(receiveMsgInfo.contains("체크")){
                String tpCkCd="";
                if(receiveMsgInfo.contains("신한")) tpCkCd="신한";
                if(receiveMsgInfo.contains("우리")) tpCkCd="우리";
                if(receiveMsgInfo.contains("국민")) tpCkCd="국민";

                if(tpCkCd==""){
                    // 분석 내용에 없을때 수행할 이벤트...

                }
                // 은행 종류별로 문자 정보 얻기(체크카드)
                return getCkCdInfo(tpCkCd,receiveMsgInfo);

            }
            //신용 카드일때
            else if(receiveMsgInfo.contains("카드")){
                String tpCrCd="";
                if(receiveMsgInfo.contains("신한")) tpCrCd="신한";
                if(receiveMsgInfo.contains("NH")) tpCrCd="NH";

                if(tpCrCd==""){
                    // 분석 내용에 없을때 수행할 이벤트...

                }

                // 은행 종류별로 문자 정보 얻기(신용카드)
                return getCrCdInfo(tpCrCd,receiveMsgInfo);
                //확인 불가능 할때 설정
            }else{
                // 분석 실패 할때 수행할 이벤트...
            }
        }
        return null;
    }

    // 은행 종류별로 문자 정보 얻기(체크카드)
    public static Map<String, Object> getCkCdInfo(String tpCkCd, String receiveMsgInfo) {
        String[] receiveMsg = receiveMsgInfo.split("\\s+");
        Map<String, Object> msgInfo = null;
        String price = "";

        Log.i("rosa", "---메세지 불러온 처음 정보----");
        for (int i = 0; i < receiveMsg.length; i++) {
            Log.i("rosa", receiveMsg[i]);
        }
        switch (tpCkCd) {
            //국민은행
            case "국민":
                //분석하기.
                msgInfo = new HashMap<>();
                msgInfo.put("tpCdNm", "국민");                            //카드 은행 종류 : tpCdNm

                msgInfo.put("cdNo", receiveMsg[1].substring(receiveMsg[1].length() - 5, receiveMsg[1].length() - 1));
                //카드 번호 : cdNo
                msgInfo.put("useDate", receiveMsg[3]);                    //사용 날짜 : useDate
                msgInfo.put("useTime", receiveMsg[4]);                    //사용 시간 : useTime

                price = receiveMsg[5].replace("원", "");
                price = price.replace(",", "");
                msgInfo.put("price", Integer.parseInt(price));            //사용 금액 : price
                msgInfo.put("usePlace", receiveMsg[6]);                    //상호명   : usePlace

                break;

            //국민은행
            case "신한":
                //분석하기.
                msgInfo = new HashMap<>();
                msgInfo.put("tpCdNm", "신한");                            //카드 은행 종류 : tpCdNm


                msgInfo.put("cdNo", receiveMsg[2].substring(receiveMsg[2].length() - 5, receiveMsg[2].length() - 1));
                //카드 번호 : cdNo
                msgInfo.put("useDate", receiveMsg[3]);                    //사용 날짜 : useDate
                msgInfo.put("useTime", receiveMsg[4]);                    //사용 시간 : useTime

                price = receiveMsg[5].replace("원", "");
                price = price.replace(",", "");
                msgInfo.put("price", Integer.parseInt(price));            //사용 금액 : price
                msgInfo.put("usePlace", receiveMsg[6]);                    //상호명   : usePlace

                break;

            //우리은행
            case "우리":
                //분석하기.
                msgInfo = new HashMap<>();
                msgInfo.put("tpCdNm", "우리");                            //카드 은행 종류 : tpCdNm


                msgInfo.put("cdNo", receiveMsg[3].substring(3, 7));
                //카드 번호 : cdNo
                msgInfo.put("useDate", receiveMsg[4]);                    //사용 날짜 : useDate
                msgInfo.put("useTime", receiveMsg[5]);                    //사용 시간 : useTime

                price = receiveMsg[2].replace("원", "");
                price = price.replace(",", "");
                msgInfo.put("price", Integer.parseInt(price));            //사용 금액 : price
                msgInfo.put("usePlace", receiveMsg[6]);                    //상호명   : usePlace

                break;

            default:
                break;
        }
        if(!msgInfo.isEmpty()) {
            /** 여기에 무조건 로그인된 사용자 이름 추가하기 !!!!!!* */
            msgInfo.put("useId", "");

            msgInfo.put("tpCd", "체크"); // 카드 종류 : tpCd

            if (receiveMsgInfo.contains("취소")) {
                msgInfo.put("refundYn", 'Y');
                //우리체크카드 취소 일때 예외처리
                if (tpCkCd == "우리") {
                    msgInfo.put("cdNo", receiveMsg[3].substring(5, 9));
                }
            }

            Log.i("rosa", "---메세지 불러온 후 Map에 정보 저장(map 그대로 가지고 들어가서 db에 저장해주기----");

            Iterator<String> iterator = msgInfo.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Log.i("rosa", "key=" + key);
                Log.i("rosa", " value=" + msgInfo.get(key));
            }
        }
        return msgInfo;
    }

    // 은행 종류별로 문자 정보 얻기(신용카드)
    public static Map<String, Object> getCrCdInfo(String tpCrCd, String receiveMsgInfo){
        String[] receiveMsg = receiveMsgInfo.split("\\s+");
        Map<String, Object> msgInfo = null;
        String price ="";

        Log.i("rosa", "---메세지 불러온 처음 정보----");

        for(int i =0;i<receiveMsg.length;i++){
            Log.i("rosa", receiveMsg[i]);
        }

        switch (tpCrCd) {

            //농협
            case "NH":
                //분석하기.
                msgInfo = new HashMap<>();
                msgInfo.put("tpCdNm","NH");                      //카드 은행 종류 : tpCdNm

                msgInfo.put("cdNo",receiveMsg[3].substring(receiveMsg[3].length()-5,receiveMsg[3].length()-1));
                //카드 번호 : cdNo
                msgInfo.put("useDate",receiveMsg[6]);               //사용 날짜 : useDate
                msgInfo.put("useTime",receiveMsg[7]);               //사용 시간 : useTime

                price= receiveMsg[2].replace("원", "");
                price= price.replace(",", "");
                msgInfo.put("price", Integer.parseInt(price));         //사용 금액 : price
                msgInfo.put("usePlace", receiveMsg[9]);               //상호명   : usePlace
                break;

            default:
                break;
        }

        /** 여기에 무조건 로그인된 사용자 이름 추가하기 !!!!!!* */
        if(!msgInfo.isEmpty()) {
            msgInfo.put("useId", "");

            msgInfo.put("tpCd", "신용"); // 카드 종류 : tpCd

            if (receiveMsgInfo.contains("취소")) {
                msgInfo.put("refundYn", 'Y');
            }
            Log.i("rosa", "---메세지 불러온 후 Map에 정보 저장(map 그대로 가지고 들어가서 db에 저장해주기----");

            Iterator<String> iterator = msgInfo.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Log.i("rosa", "key=" + key);
                Log.i("rosa", " value=" + msgInfo.get(key));
            }
        }
        return msgInfo;
    }
}