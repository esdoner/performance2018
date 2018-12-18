package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/12/4
 * Description:none
 */
public final class ThresholdLineNumber extends SubThreshold{
    //目的是读取所有相关的阈值，这里列出key
    static String[] prosArrInit = {"WarningLineNumber", "MaxLineNumber"};

    public ThresholdLineNumber(){
        //2级阀门的开关
        onoffKey = "Switch4LineNumberThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }
}
