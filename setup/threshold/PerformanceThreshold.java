package com.fr.performance.setup.threshold;

import com.fr.performance.reader.PropertiesReader;

import java.util.HashMap;
import java.util.Map;

/**private boolean judgment = true;
 * Created by yuwh on 2018/11/23
 * Description:唯一属性就是onoffSwicth
 */
public abstract class PerformanceThreshold implements Threshold {
    /*表示该Threshold是否加入校验*/
    private static boolean onoffSwicth;

    PerformanceThreshold(){
        onoffSwicth = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(propath,"thresholdperformance"));
    }

    @Override
    public synchronized void toggleSwitch(){
        Map changeList = new HashMap<String,String>();

        onoffSwicth = ! onoffSwicth;
        changeList.put("thresholdperformance",String.valueOf(onoffSwicth));
        PropertiesReader.getInstance().setupPro(propath,changeList);
    }

    @Override
    /**
    * @params []
    * @return java.lang.String
    * @description: 阈值直接就是onoffSwitch，有点奇怪的
    */
    public String getThreshold() {
       return String.valueOf(onoffSwicth);
    }

    @Override
    /**
    * @params [var]
    * @return void
    * @description: 同上
    */
    public void setThreshold(String var) {
        if(onoffSwicth != Boolean.parseBoolean(var)){
            toggleSwitch();
        }
    }

    @Override
    public void createThresholdCalculator() {
        
    }
}
