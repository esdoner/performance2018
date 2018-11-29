package com.fr.performance.setup.threshold;

import java.util.LinkedHashMap;

/**
 * Created by yuwh on 2018/11/23
 * Description:none
 */
public class ThresholdBucket {
    private static ThresholdBucket ourInstance= new ThresholdBucket();
    private LinkedHashMap<String,Threshold> thresholdMap;

    private ThresholdBucket(){}

    public static ThresholdBucket getInstance(){
        return ourInstance;
    }

    public Threshold getThreshold(String key){
        return thresholdMap.get(key);
    }

    public class BucketManager<T extends Threshold> {
        private T threshold;
        private String key;

        public BucketManager(String var1,T var2){
            key = var1;
            threshold = var2;
        }

        public void setBucket(){
            thresholdMap.put(key,threshold);
        }
    }
}
