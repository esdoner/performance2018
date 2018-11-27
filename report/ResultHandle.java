package com.fr.performance.report;

import java.util.HashMap;

/**
 * Created by yuwh on 18.8.7
 * Description:none
 */
public abstract class ResultHandle {
    protected HashMap multinput;
    protected String multioutput;

    public ResultHandle(){
        System.out.println("final processing no print,might have data stored");
    }

    public ResultHandle(HashMap var1){ multinput = var1; }

    public void beforeHandle(){
    }

    public Object objectHandle(){
        return null;
    }

    public void afterHandle(){
    }
}
