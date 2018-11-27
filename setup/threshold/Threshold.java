package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 */
public interface Threshold {
    //切换开关
    void toggleSwitch();

    //设置和获取阈值
    boolean setThreshold ();

    String getThreshold ();

    //创建阈值判断算子
    void createThresholdCalculator();

    //判断结果
    boolean getJudgment();
}
