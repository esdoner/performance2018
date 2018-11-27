package com.fr.performance.startup;

import com.fr.performance.reader.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/11/22
 * Description:none
 */
public class StartProManageTest {
    public static void main(String[] args) throws Exception {
        Map a  = PropertiesReader.getInstance().readProperties("com\\fr\\performance\\file\\release\\release.properties");
        Map b  = PropertiesReader.getInstance().readProperties("com\\fr\\performance\\setup\\threshold.properties");

        Map changeList = new HashMap<String,String>();
        changeList.put("WarningLineNumber","10000");
        PropertiesReader.getInstance().setupPro("com\\fr\\performance\\setup\\threshold.properties",changeList);

        b  = PropertiesReader.getInstance().readProperties("com\\fr\\performance\\setup\\threshold.properties");
    }
}
