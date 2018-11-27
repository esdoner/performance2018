package com.fr.performance.reader;

/**
 * Created by yuwh on 18.8.8
 * Description:none
 */
public class ConfigReader {
    private static ConfigReader cr;

    private ConfigReader(){
    }

    public static synchronized ConfigReader getReader(){
        return cr;
    }
}
