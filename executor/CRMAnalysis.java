package com.fr.performance.executor;

import com.fr.performance.preanalysis.DSPreAnalysis;
import com.fr.script.AbstractFunction;

import static com.fr.performance.reader.EscapeStrReader.unescape;

/**
 * Created by yuwh on 18.7.29
 * Description:none
 */
public class CRMAnalysis extends AbstractFunction {
    public CRMAnalysis() {
    }

    public Object run(Object[] objects) {
        return fetchResult(objects);
    }

    private String fetchResult(Object[] objects){
        DSPreAnalysis var1 = new DSPreAnalysis( unescape(objects[0].toString()), unescape(objects[1].toString()), unescape(objects[2].toString()) );
        if(var1.judgeWorkbook()) {
            var1.analysistart();
            if(!var1.interrupt) {
                var1.analysistop();
            }
        }else{
            var1.analysisInterrput(1);
        }
        return var1.ResultReport;
    }
}