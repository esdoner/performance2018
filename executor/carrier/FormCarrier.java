package com.fr.performance.executor.carrier;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yuwh on 2019/1/15
 * Description:none
 */
public class FormCarrier extends Carrier{
    private static final String[] CARRIERLIST = {"cptName", "userName", "passWord", "reason", "outerParas", "bakbak"};

    public FormCarrier(){
        super(CARRIERLIST);
    }
}
