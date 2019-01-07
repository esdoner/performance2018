package com.fr.performance.executor;

import com.fr.performance.reader.PropertiesReader;
import com.fr.performance.setup.bucket.ThresholdBucket;
import com.fr.performance.setup.threshold.Threshold;
import com.fr.script.AbstractFunction;
import static com.fr.performance.reader.EscapeStrReader.unescape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/12/17
 * Description:none
 */
public class RelesseSetUp extends AbstractFunction {
    public RelesseSetUp() {
    }

    public Object run(Object[] objects) {
        return fetchResult(objects);
    }

    protected String fetchResult(Object[] objects){
        String[] a = unescape(objects[0].toString().trim()).split(";");
        Map b = new HashMap();
        for (int i = 1;i < a.length; i++){
            String[] c = a[i].split(",");
            if (c[0].isEmpty() || c[1].isEmpty()) {
                return "0";
            } else {
                b.put(c[0] ,c[1]);
            }
        }

        Map d = PropertiesReader.ProCompare(Threshold.PROPATH,b,true);
        //先要把文件中的，内存中的 配置 更新，用PropertiesReader.setupPro
        if(d.size() > 0) {
            PropertiesReader.getInstance().setupPro(Threshold.PROPATH, d);
            //用ThresholdBucket.reset();注意是clear()了的
            ThresholdBucket.getInstance().reset();
        }
        return "1";
    }
}
