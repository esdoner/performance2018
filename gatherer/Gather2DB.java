package com.fr.performance.gatherer;

import com.fr.performance.handler.Connect2DB4Mysql;

import java.sql.Statement;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 */
public abstract class Gather2DB implements AnalyzeGatherer{
    protected static String containerType;
    protected String sqlString;
    protected Statement stmt;
    protected Connect2DB4Mysql conn;
    public static final boolean absFinal = true ;

    public Gather2DB(String var1)  {
        conn = new Connect2DB4Mysql(var1);
        conn.createCon();
        stmt = conn.getStmt();
    }

    @Override
    public void setContainerType(String var1) { containerType = var1; }

    @Override
    public String getContainerType() { return containerType; }

    public  String  htmParser(String var2){
        String var3 =  var2.replaceAll("[\'\"]","\\\\'");
        return var3;
    }

    protected void conFinish(){conn.createCon(); }
}
