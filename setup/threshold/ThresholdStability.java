package com.fr.performance.setup.threshold;

import com.fr.performance.reader.PropertiesReader;

/**
 * Created by yuwh on 2018/12/4
 * Description:none
 */
public final class ThresholdStability extends PrimaryThreshold {
    public ThresholdStability(){
        onoffKey = "Switch4StabilityThreshold";
        onoffSwitch = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(PROPATH,onoffKey));
        thd = createThresholdCalculator();
    }
}
