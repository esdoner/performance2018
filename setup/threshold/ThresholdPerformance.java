package com.fr.performance.setup.threshold;

import com.fr.performance.reader.PropertiesReader;

/**
 * Created by yuwh on 2018/11/23
 * Description:唯一属性就是onoffSwicth
 */
public final class ThresholdPerformance extends PrimaryThreshold {
    public ThresholdPerformance(){
        onoffKey = "Switch4PerformanceThreshold";
        onoffSwitch = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(PROPATH,onoffKey));
        thd = createThresholdCalculator();
    }
}
