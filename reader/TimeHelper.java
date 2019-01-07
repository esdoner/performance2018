package com.fr.performance.reader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by yuwh on 2019/1/3
 * Description:none
 */
public class TimeHelper {
    private static HashMap<Integer, String> MODLIST= new HashMap<>();

    static{
        MODLIST.put(1, "yyyy-MM-dd HH:mm:ss");
        MODLIST.put(2, "yyyy-MM-dd");
        MODLIST.put(3, "HH:mm:ss");
        MODLIST.put(4, "HH:mm");
        MODLIST.put(5, "yyyy-w");
        MODLIST.put(6, "yyyy-MM-W");
        MODLIST.put(7, "MMM dd,yyyy");
    }

    private static String getSimpleFormat(Date date, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(date);
    }

    public static String simpleFormat(Date date, int mod){
        return MODLIST.containsKey(mod)? getSimpleFormat(date, MODLIST.get(mod)): getSimpleFormat(date, MODLIST.get(1));
    }

    public static String simpleFormat(Date date, String format) {
        String result = "";
        try {
            result = getSimpleFormat(date, format);
        } catch(Exception e){
            result = getSimpleFormat(date, MODLIST.get(1));
            e.printStackTrace();
        } finally{
            return result;
        }
    }

    public static String simpleFormat(int mod){ return simpleFormat(new Date(), mod); }

    public static String simpleFormat(String format){ return simpleFormat(new Date(), format); }
}
