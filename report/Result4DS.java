package com.fr.performance.report;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 18.8.7
 * Description:none
 */
public class Result4DS extends ResultHandle {
    private final String TABLE = "CPT��������";
    private final String[] TITLE = {"���ݼ�ͳ��","ִ�мƻ�"};
    private final String DBNAME = "��������";
    private final String DSNAME = "sql���ݼ�";
    private final String T = "ִ��ʱ��/ms";
    private final String L = "�������";
    private final String M = "Ԥ���ڴ�/kb";
    private String cptName = "";
    private String userName = "";
    private String storeoutput = "";

    public Result4DS(String var3, String var2, HashMap var1){
        super(var1);
        beforeHandle(var3,var2);
    }

    public void beforeHandle(String var1,String var2){
        this.cptName = var1;
        this.userName = var2;
    }

    public String objectHandle(){
        return object2Html();
    }

    private String object2Html(){
        Set<Map.Entry> multiSet = multinput.entrySet();
        consum2Html(multiSet);
        return this.multioutput;
    }

    private void consum2Html(Set<Map.Entry> multiSet){
        String var5 = "";

        multioutput = "<h1 id='resultitle'>" +TABLE+"<span>about &lt;"+this.cptName+"&gt; tested by "+this.userName+"</span></h1>"
                +"<div class='resultpart'>"
                +"<div id='p1_title' class='subtitle'>"+TITLE[0]+"</div>"
                +"<div id='p1_help' class='subhelp'><a href='http://kms.finedevelop.com/pages/viewpage.action?pageId=43968215' target='_blank'>?</a></div>"
                +"<div class='slider_slim' style='max-height: 300px;'>"
                +"<table class='analysis'><tr><td>"+DBNAME+"</td><td>"+DSNAME+"</td><td>"+T+"</td><td>"+L+"</td><td>"+M+"</td></tr>";

        for(Map.Entry<String,String[]> var3:multiSet){
            String[] var4 = var3.getValue();
            storeoutput = storeoutput + "<tr><td>"+var4[0]+"</td><td>"+var3.getKey()+"</td><td>"+var4[2]+"</td><td>"+var4[3]+"</td><td>"+var4[4]+"</td></tr>";
            var5 += var4[1];
        }
        multioutput = multioutput+ storeoutput+"</table></div></div>";

        multioutput = multioutput
                +"<div class='resultpart'>"
                +"<div id='p2_title' class='subtitle'>"+TITLE[1]+"</div>"
                +"<div id='p2_help' class='subhelp'><a href='http://kms.finedevelop.com/pages/viewpage.action?pageId=43968219' target='_blank'>?</a></div>"
                +"<div class='slider_slim' style='max-height: 600px;'>"
                +var5+"</div></div>";
    }
    public String getStoreHtml(){
        return storeoutput;
    }
}
