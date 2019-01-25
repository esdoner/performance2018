package com.fr.performance.executor.carrier;

/**
 * Created by yuwh on 2019/1/15
 * Description: 配置参数
 */
public class OptionCarrier extends Carrier{
    private static final String[] CARRIERLIST = {"mkdir", "bakbak"};

    public OptionCarrier() {
        super(CARRIERLIST);
    }
}
