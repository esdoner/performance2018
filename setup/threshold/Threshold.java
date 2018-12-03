package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 */
public interface Threshold {
    String propath = "com/fr/performance/setup/threshold/threshold.properties";

    //切换开关
    void toggleSwitch();

    //设置和获取阈值
    void setThreshold (String var);

    String getThreshold ();

    //创建阈值判断算子
    void createThresholdCalculator();
}
