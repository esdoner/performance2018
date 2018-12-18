package com.fr.performance.judge.calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * Created by yuwh on 2018/12/10
 * Description:none
 */
public class ThresholdCalculator implements Calculator {
    private String[] arguments = null;
    private String[] operators = null;
    private String[] thresholdindexs = null;
    private int space;

    public ThresholdCalculator(int var){
        space = var;
        arguments = new String[space];
        operators = new String[space];
        thresholdindexs = new String[space];
    }

    public void argsIn(String var1, int var2){ arguments[var2] = var1; }

    public void optIn(String var1, int var2){ operators[var2] = var1; }

    public void thdIn(String var1, int var2){ thresholdindexs[var2] = var1; }

    /**
    * @return boolean
    * @description: 抄来的能用^_^
    */
    public boolean[] Mapping(Map<String,String> var) {
        //如果没有初始化好直接就全false
        if(arguments.length != operators.length || thresholdindexs.length != operators.length){
            return new boolean[space];
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object result = false;
        boolean[] results = new boolean[space];
        for(int i=0;i<space;i++){
            //plate里没给出的直接就不通过了
            if(! var.containsKey(arguments[i])){
                results[i] = false;
            }
            String str = var.get(arguments[i])+operators[i]+thresholdindexs[i];
            try {
                result = engine.eval(str);
                results[i] = Boolean.valueOf(result.toString());
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
