package com.fr.performance.setup.threshold;

/**
 * Created by yuwh on 2018/11/23
 * Description:阈值的集合，提供判断是否通过
 */
public class ThresholdBucket {
    private static ThresholdBucket ourInstance= new ThresholdBucket();

    private ThresholdBucket(){}

    public static ThresholdBucket getInstance(){
        return ourInstance;
    }

    private class Put12Bucket<T extends Threshold> {

    }
}
