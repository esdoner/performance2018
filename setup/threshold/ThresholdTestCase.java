package com.fr.performance.setup.threshold;

import com.fr.performance.judge.calculator.ThresholdCalculator;

/**
 * Created by yuwh on 2018/12/11
 * Description:none
 */
public class ThresholdTestCase extends SubThreshold{
    static String[] prosArrInit = {"TestCaseRequire"};

    public ThresholdTestCase(){
        //2级阀门的开关
        onoffKey = "Switch4TestCaseThreshold";
        prosArr = prosArrInit;
        loadThresholdIndex();
        thd = createThresholdCalculator();
    }

    @Override
    public ThresholdCalculator createThresholdCalculator(){
        ThresholdCalculator thd = new ThresholdCalculator(prosArr.length);
        for(int i=0;i<prosArr.length;i++) {
            thd.argsIn(prosArr[i],i);
            thd.optIn("==", i);
            thd.thdIn(thresholdsMap.get(prosArr[i]), i);
        }
        return thd;
    };
}
