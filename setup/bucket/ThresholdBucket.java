package com.fr.performance.setup.bucket;

import com.fr.performance.setup.threshold.ThresholdPlate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/11/23
 * Description: 不断产生的残羹>>一个盘子>>一个桶子>>若干躲在桶子里的挑食的野猫
 */
public class ThresholdBucket implements BucketBase {
    private static ThresholdBucket ourInstance= new ThresholdBucket();
    private static String[][] thresholdNames= {
            {"Performance","LineNumber","TimeConsume","MemoryConsume","DBRetrievalNumber","TotalTime"},
            {"Stability","TestCase"}
    };
    private LinkedHashMap<String,Object> thresholdMap = new LinkedHashMap<String,Object>();

    private ThresholdBucket(){}

    public static ThresholdBucket getInstance(){
        if(ourInstance.thresholdMap.isEmpty()){
            ourInstance.ThresholdsIn();
        }
        return ourInstance;
    }

    public Object getThreshold(String key){ return thresholdMap.get(key); }

    private void setThresholdNames(String[][] var){ thresholdNames = var; }

    /**
    * @return void
    * @description: 这里只负责 内存 中的threshold的配置修改，要确保配置文件已经被重载到内存，不然不会变
    */
    public synchronized void reset(){
        ourInstance.thresholdMap.clear();
        ourInstance.ThresholdsIn();
    }

    /**
    * @params []
    * @return void
    * @description: 猫咪躲进桶子；全量载入新的阀门类
    */
    private synchronized void ThresholdsIn(){
        for(int i = 0;i<thresholdNames.length;i++){
            try {
                //利用反射先获取1级阀门是否打开
                Class pt = Class.forName("com.fr.performance.setup.threshold."+"Threshold"+thresholdNames[i][0]);
                Field ptf = pt.getSuperclass().getDeclaredField("onoffSwitch");
                ptf.setAccessible(true);
                Object pti= pt.newInstance();

                if(Boolean.valueOf(ptf.get(pti).toString())){
                    thresholdMap.put(thresholdNames[i][0], pti);
                    for(int j = 1;j<thresholdNames[i].length;j++){
                        //依次获取2级阀门是否打开
                        Class st = Class.forName("com.fr.performance.setup.threshold."+"Threshold"+thresholdNames[i][j]);
                        Field stf = st.getSuperclass().getDeclaredField("onoffSwitch");
                        stf.setAccessible(true);
                        Object sti= st.newInstance();

                        if(Boolean.valueOf(stf.get(sti).toString())) {
                            thresholdMap.put(thresholdNames[i][j], sti);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * @params []
    * @return void
    * @description: 开始营业，盘子拿来倒进桶里
    */
    public void thresholdsWork(ThresholdPlate var){
        //先标记下
        var.putnotes("gameover",false);
        //加上一级阀门的开关
        for(int i=0;i<thresholdNames.length;i++){
            if(thresholdMap.containsKey(thresholdNames[i][0])){
                var.putLeavings("Switch4"+thresholdNames[i][0]+"Threshold","true");
            }
        }
        //开始挑选
        Set<Map.Entry<String,Object>>  var1 = thresholdMap.entrySet();
        for(Map.Entry<String,Object> var2:var1){
            Object var3 = var2.getValue();
            Class<?> var4 = var3.getClass();
            try {
                Field var5 = var4.getSuperclass().getDeclaredField("thd");
                var5.setAccessible(true);
                Object var6 = var5.get(var3);

                Method var7 = var6.getClass().getMethod("Mapping", Map.class);
                Object var8 = var7.invoke(var6, var.getplate());
                boolean var9 = hasLeavings(var8);

                if(!var9){var.putnotes("gameover",true);}
                var.putnotes(var2.getKey(),var9);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasLeavings(Object var){
        boolean[] var1 = (boolean[]) var;
        for(boolean var2 : var1){
            if(! var2){
                return false;
            }
        }
        return true;
    }
}
