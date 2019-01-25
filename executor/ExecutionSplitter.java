package com.fr.performance.executor;

import com.fr.script.AbstractFunction;

import java.lang.reflect.Method;

import static com.fr.performance.reader.ClassInfoReader.*;

/**
 * Created by yuwh on 2018/12/27
 * Description:none
 */
public class ExecutionSplitter extends AbstractFunction {
    private String packageName;
    private String className;
    private Object[] objects;

    public ExecutionSplitter(){ packageName = getPackName(this.getClass(), false); }

    @Override
    public String run(Object[] objects) {
        String result = "";
        if(objects == null){
            throw new RuntimeException("No FunctionName Found In Request");
        } else {
            className = packageName + "." + objects[0];
            this.objects = new Object[objects.length-1];
            System.arraycopy(objects,1, this.objects,0, objects.length-1);
            if( !judgeClassExists(packageName, className ) ){
                throw new RuntimeException("No Class Matched In Package");
            } else {
                try {
                    Class c = Class.forName(className);
                    Method m = c.getDeclaredMethod("fetchResult", Object[].class);
                    Object o = c.newInstance();
                    result = String.valueOf(m.invoke(o, (Object) this.objects ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
