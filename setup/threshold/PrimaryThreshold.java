package com.fr.performance.setup.threshold;

import com.fr.performance.judge.calculator.ThresholdCalculator;
import com.fr.performance.reader.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/12/4
 * Description: 1级阀门的父类，目前没有阈值项只有开关
 */
public abstract class PrimaryThreshold implements Threshold{
    boolean onoffSwitch;
    String onoffKey;
    ThresholdCalculator thd;

    PrimaryThreshold(){}

    @Override
    public void loadThresholdIndex() {

    }

    @Override
    public void toggleSwitch() {
        Map changeList = new HashMap<String,String>();
        onoffSwitch = ! onoffSwitch;
        changeList.put(onoffKey,String.valueOf(onoffSwitch));
        PropertiesReader.getInstance().setupPro(PROPATH,changeList);
    }

    @Override
    public void setThreshold(Map var1) {

    }

    @Override
    /**
    * @return com.fr.performance.judge.calculator.ThresholdCalculator
    * @description: 1级阀门只检查开关
    */
    public ThresholdCalculator createThresholdCalculator(){
        ThresholdCalculator thd = new ThresholdCalculator(1);
        thd.argsIn(onoffKey,0);
        thd.optIn("==", 0);
        thd.thdIn("true",0);

        return thd;
    };

    public String getThreshold(String var1) {
        return String.valueOf(onoffSwitch);
    }

    public ThresholdCalculator createThresholdCalculator(Map<String,String> var) {
        return createThresholdCalculator();
    }
}
