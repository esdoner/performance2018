package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/12/7
 * Description:none
 */
public final class ThresholdTimeConsume extends SubThreshold {
    static String[] prosArrInit = {"WarningTimeConsume", "MaxTimeConsume"};

    public ThresholdTimeConsume(){
        //2级阀门的开关
        onoffKey = "Switch4TimeConsumeThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }
}