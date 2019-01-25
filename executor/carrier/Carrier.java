package com.fr.performance.executor.carrier;

import java.util.*;

/**
 * Created by yuwh on 2019/1/15
 * Description:
 *  此类及子类的目的
 *  避免初始化原来越长或者set方法越来越多；
 *  避免不同事务采用不同的参数标识符不便阅读；
 *  将不同类的信息分开carrier，可能会方便优化；
 *  还要思考如何新增方法时尽量少地修改？
 */
public abstract class Carrier {
    protected List<String> DUTYLIST;
    protected Map<String, Object> items = new LinkedHashMap<String, Object>();

    public Carrier(String[] args) {
        DUTYLIST = new ArrayList<>(Arrays.asList(args));
    }

    public Carrier addItem(String name, Object obj) {
        if(inDuty(name)) items.put(name, obj);
        return this;
    }

    public Carrier addAllItems(Map<String,Object> items) {
        for (Map.Entry<String, Object> var1: items.entrySet()) addItem(var1.getKey(), var1.getValue());
        return this;
    }

    public Object getItem(String key) { return items.get(key); }

    public Map getAllItems(){ return items;}

    private boolean  inDuty(String name) {
        if(DUTYLIST == null) {
            return false;
        } else {
            return DUTYLIST.contains(name);
        }
    }
}
