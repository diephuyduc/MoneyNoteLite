package com.example.myappv1.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MNumberFormatMoney {
    public static String Money(long money){
        if(money >=0 ){
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
           return formatter.format(money);
        }
        return "0";

    }

}
