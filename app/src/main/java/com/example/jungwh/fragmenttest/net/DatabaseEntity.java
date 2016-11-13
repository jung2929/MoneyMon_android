package com.example.jungwh.fragmenttest.net;

/**
 * Created by jungwh on 2016-11-13.
 */

public class DatabaseEntity {
    public static final String ip = "10.0.2.2";

    private DatabaseEntity(){}

    public static String getInstance(){
        return ip;
    }
}
