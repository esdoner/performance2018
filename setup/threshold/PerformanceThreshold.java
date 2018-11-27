package com.fr.performance.setup.threshold;

import com.fr.performance.reader.PropertiesReader;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 */
public abstract class PerformanceThreshold implements Threshold {
    /*表示该Threshold是否加入校验*/
    private static boolean onoffSwicth = false;
    private static String propath = "com\\fr\\performance\\setup\\threshold\\threshold.properties";

    PerformanceThreshold(){
        onoffSwicth = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(propath,"thresholdperformance"));
    }

    public void toggleSwitch(){
        onoffSwicth = ! onoffSwicth;
    };
}
