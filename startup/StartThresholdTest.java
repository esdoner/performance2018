package com.fr.performance.startup;

import com.fr.performance.setup.bucket.ThresholdBucket;
import com.fr.performance.setup.threshold.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/11/27
 * Description:none
 */
public class StartThresholdTest {
    public static void main(String[] args){
        String a1 = "MaxLineNumber";
        String a2 = "Fake";
        String a3 = "WarningLineNumber";

        //测试阀门
        ThresholdLineNumber b = new ThresholdLineNumber();
        ThresholdDBRetrievalNumber b1 = new ThresholdDBRetrievalNumber();
        ThresholdMemoryConsume b2 = new ThresholdMemoryConsume();
        ThresholdTimeConsume b3 = new ThresholdTimeConsume();
        ThresholdPerformance b4 = new ThresholdPerformance();
        ThresholdStability b5 = new ThresholdStability();

        String[] c = {a1,a2};
        //测试开关,reload第一次
        //b.toggleSwitch();
        //测试get
        String d1 = b.getThreshold("MaxLineNumber");
        d1 = String.valueOf(Integer.valueOf(d1)+1);
        String d2 = "";
        String d3 = b.getThreshold("WarningLineNumber");
        d3 = String.valueOf(Integer.valueOf(d3)+1);
        //测试set,reload第一次
        Map e = new HashMap();
        e.put(a1,d1);
        e.put(a2,d2);
        e.put(a3,d3);
        b.setThreshold(e);

        ThresholdBucket.getInstance();
    }
}
