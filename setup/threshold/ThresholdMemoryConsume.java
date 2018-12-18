package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/12/7
 * Description:none
 */
public final class ThresholdMemoryConsume extends SubThreshold {
    static String[] prosArrInit = {"WarningMemoryConsume", "MaxMemoryConsume"};

    public ThresholdMemoryConsume(){
        //2级阀门的开关
        onoffKey = "Switch4MemoryConsumeThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }
}
