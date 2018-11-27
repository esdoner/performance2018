package com.fr.performance.preanalysis;

import com.fr.performance.reader.ConfigReader;

/**
 * Created by yuwh on 18.8.7
 * Description:none
 */
public abstract class Analysis {
    static{
    }

    public Analysis(){
    }

    public void analysistart(){}

    public String analysisInterrput() {
        return "no special reasons for rejection";
    }

    public void analysistop(){}
}