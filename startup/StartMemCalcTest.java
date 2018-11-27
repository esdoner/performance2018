package com.fr.performance.startup;

import com.fr.performance.preanalysis.MemAnalysis;

/**
 * Created by yuwh on 2018/9/26
 * Description:none
 */
public class StartMemCalcTest {
    private final String[] a = {"FanRuan","NO.1","^0^"};

    public static void main(String[] args) throws Exception {
        StartMemCalcTest b = new StartMemCalcTest();

        MemAnalysis cIntrospector = new MemAnalysis();
        MemAnalysis.ObjectInfo oInfo = cIntrospector.introspect(b.a);
        System.out.println(oInfo.getDeepSize());
    }
}