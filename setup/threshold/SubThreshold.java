package com.fr.performance.setup.threshold;

import com.fr.performance.judge.calculator.ThresholdCalculator;
import com.fr.performance.reader.PropertiesReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/12/4
 * Description: 2级阀门的父类
 */
public abstract class SubThreshold implements Threshold{
    boolean onoffSwitch;
    String onoffKey;
    String[] prosArr;
    Map<String,String> thresholdsMap = new HashMap<>();
    ThresholdCalculator thd;

    SubThreshold(){}

    @Override
    /**
    * @return
    * @description: 全部 阈值 重载；为 内存 载入 配置文件 中，且在 prosArr 中的 阈值
    */
    public void loadThresholdIndex() {
        onoffSwitch = Boolean.parseBoolean(PropertiesReader.getInstance().readProperties(propath,onoffKey));
        String v;
        for (String s : prosArr) {
            v = PropertiesReader.getInstance().readProperties(propath, s);
            thresholdsMap.put(s, v);
        }
    }

    @Override
    /**
    * @return void
    * @description: 开关重载；改 onoffSwitch >> 根据 onoffKey 使用 setupPro 修改 配置文件 及 内存 中的开关配置
    */
    public synchronized void toggleSwitch(){
        Map changeList = new HashMap<String,String>();
        onoffSwitch = ! onoffSwitch;
        changeList.put(onoffKey,String.valueOf(onoffSwitch));
        PropertiesReader.getInstance().setupPro(propath,changeList);
    }

    /**
    * @return
    * @description:  单个 阈值 重载；改 内存 中的 thresholdMap >> 使用 setupPro 修改 配置文件 及 内存 中的指定配置
    */
    public synchronized void setThreshold(Map var1) {
        Map changeList = new HashMap<String,String>();
        Set<Map.Entry> var3 = var1.entrySet();
        for(Map.Entry<String,String> var4 : var3){
            if(containsThreshold(var4.getKey())) {
                thresholdsMap.put(var4.getKey(), var4.getValue());
                changeList.put(var4.getKey(), var4.getValue());
            }
        }
        PropertiesReader.getInstance().setupPro(propath,changeList);
    }

    /**
     * @return java.lang.String
     * @description: 判断下是开关还是阈值，直接读实例里的值
     */
    public String getThreshold(String var1) {
        if(var1 == onoffKey){
            return String.valueOf(onoffSwitch);
        } else if(containsThreshold(var1)) {
            return thresholdsMap.get(var1);
        } else{
            return "";
        }
    }

    /**
    * @return boolean
    * @description: 判断2级阀门的 prosArr 里是否有指定属性
    */
    public  boolean containsThreshold(String var){
        if(Arrays.asList(prosArr).contains(var)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ThresholdCalculator createThresholdCalculator(){
        ThresholdCalculator thd = new ThresholdCalculator(prosArr.length);
        for(int i=0;i<prosArr.length;i++) {
            thd.argsIn(prosArr[i],i);
            thd.optIn("<=", i);
            thd.thdIn(thresholdsMap.get(prosArr[i]), i);
        }
        return thd;
    };
}
