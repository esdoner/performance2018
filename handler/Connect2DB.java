package com.fr.performance.handler;

/**
 * Created by yuwh on 2018/9/27
 * Description:none
 */
public interface Connect2DB {
    boolean createCon();

    boolean closeCon();

    Object executeDDL(String var1);

    Object executeDQL(String var2);

    Object executeDML(String var3);

    Object executeDCL(String var4);
}
