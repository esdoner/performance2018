package com.fr.performance.executor.carrier;

import com.fr.performance.reader.ClassInfoReader;

/**
 * Created by yuwh on 2019/1/21
 * Description:none
 */
public class CarrierFactory {

    /*
    * @params [type]
    * @return com.fr.performance.executor.carrier.Carrier
    * @description: 在包内查找是否有对应的carrier,type不分大小写
    */
    public Carrier getCarrier(String type){
        if(type == null) return null;
        StringBuilder prefix = new StringBuilder();
        prefix.append(ClassInfoReader.getPackName(this.getClass(), false)).append(".").append(type.substring(0, 1).toUpperCase()).append(type.substring(1).toLowerCase()).append("Carrier");
        Class clas;
        Object carrier = null;
        try {
            clas = Class.forName(prefix.toString());
            carrier =clas.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Carrier)carrier;
    }
}
