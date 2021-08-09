package com.example.myappv1.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MLongToDate {
    static SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static String convert(Long val){
        try {
            Date date = new Date(val);
            return df2.format(date);
        }
        catch (Exception e){
            e.printStackTrace();
        }return null;
    }

}
