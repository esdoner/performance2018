package com.fr.performance.executor.carrier;

/**
 * Created by yuwh on 2019/1/15
 * Description:none
 */
public class ConfigureCarrier extends Carrier {
    private final static String[] CARRIERLIST = {"desPath", "bakPath", "origin", "branch", "repository", "cptTitle"};

    public ConfigureCarrier() {
        super(CARRIERLIST);
    }
}
