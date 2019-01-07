package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/12/28
 * Description:none
 */
public class ThresholdTotalTime extends SubThreshold {
    static String[] prosArrInit = {"WarningTotalTime", "MaxTotalTime"};

    public ThresholdTotalTime(){
        //2级阀门的开关
        onoffKey = "Switch4TotalTimeThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }
}
