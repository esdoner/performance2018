package com.fr.performance.report;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/10/18
 * Description:none
 */
public class Result4Release extends ResultHandle {
    private final String CHECKFILE = "������ɼ��";
    private final String VERIFYPATH = "Ŀ¼���";
    private final String BACKUPFILE = "�����ļ�";
    private final String PREPAREFILE = "�����ļ�";
    private final String RELEASEFILE = "�ύ����";
    private final String RESULTGATHER = "��¼����";
    private final String SUCCESS = "�ɹ�";
    private final String FAIL = "ʧ��";

    public Result4Release(HashMap var1){
        super(var1);
        beforeHandle();
    }

    public void beforeHandle(){ }

    public String objectHandle(){
        return object2Html();
    }

    private String object2Html(){
        Set<Map.Entry> multiSet = multinput.entrySet();
        try {
            consum2Html(multiSet);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.multioutput;
    }

    private void consum2Html(Set<Map.Entry> multiSet) throws NoSuchFieldException, IllegalAccessException {
        multioutput = "<div><ul>";
        for(Map.Entry<String,String> var1:multiSet){
            String var2 = var1.getKey();
            String var3 = var1.getValue()=="success"?SUCCESS:FAIL;
            String classname = var1.getValue()=="success"?"release_sub_success":"release_sub_fail" ;
            multioutput = multioutput + "<li class='"+classname+"'><span>"+this.getClass().getDeclaredField(var2.toUpperCase()).get(this)+"</span><span>"+var3+"</span></li>";
        }
        multioutput = multioutput + "</ul></div>";
    }
}
