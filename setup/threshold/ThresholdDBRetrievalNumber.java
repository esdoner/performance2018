package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/12/7
 * Description:none
 */
public final class ThresholdDBRetrievalNumber extends SubThreshold {
    static String[] prosArrInit = {"WarningDBRetrievalNumber", "MaxDBRetrievalNumber"};

    public ThresholdDBRetrievalNumber(){
        //2级阀门的开关
        onoffKey = "Switch4DBRetrievalNumberThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }
}