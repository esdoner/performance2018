package com.fr.performance.startup;

import com.fr.performance.reader.PropertiesReader;
import com.fr.performance.setup.bucket.ThresholdBucket;
import com.fr.performance.setup.threshold.Threshold;
import com.fr.performance.setup.threshold.ThresholdPlate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/12/10
 * Description:none
 */
public class StartBucketTest {
    public static void main(String args[] ){
        ThresholdBucket a = ThresholdBucket.getInstance();
        ThresholdPlate b= new ThresholdPlate();
        b.putLeavings("TestCaseRequire","0");
        b.putLeavings("WarningLineNumber", "10001");
        b.putLeavings("MaxLineNumber", "10001");
        b.putLeavings("WarningTimeConsume", "10001");
        b.putLeavings("MaxTimeConsume","10001");
        b.putLeavings("WarningMemoryConsume", "10240");
        b.putLeavings("MaxMemoryConsume", "10240");
        b.putLeavings("WarningDBRetrievalNumber", "1000");
        b.putLeavings("MaxDBRetrievalNumber", "1000");
        a.thresholdsWork(b);
        Map c = new HashMap();
        c.put("WarningTimeConsume" , "5000");
        c.put("MaxTimeConsume" , "10000");
        PropertiesReader.getInstance().setupPro(Threshold.propath,c);
        a.reset();
    }
}
