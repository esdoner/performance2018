package com.fr.performance.setup.threshold;

import com.fr.performance.reader.PropertiesReader;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 */
public abstract class PerformanceThreshold implements Threshold {
    /*表示该Threshold是否加入校验*/
    private static boolean onoffSwicth = false;
    private boolean judgment = true;

    PerformanceThreshold(){
        onoffSwicth = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(propath,"thresholdperformance"));
    }

    @Override
    public void toggleSwitch(){
        onoffSwicth = ! onoffSwicth;
    }
}
