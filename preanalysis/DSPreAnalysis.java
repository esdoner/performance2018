package com.fr.performance.preanalysis;

import com.fr.base.*;
import com.fr.data.impl.DBTableData;
import com.fr.io.TemplateWorkBookIO;
import com.fr.main.impl.WorkBook;
import com.fr.performance.gatherer.Gather2DB4DS;
import com.fr.performance.reader.URLReader;
import com.fr.performance.report.Result4DS;
import com.fr.performance.report.Result4Interrupt;
import com.fr.performance.report.Result4NotFound;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yuwh on 18.7.29
 * Description:none
 */
public class DSPreAnalysis extends Analysis{
    private WorkBook workbook;
    private Object SCurrentEnv;
    private Map<String,Object> parameterMap = new HashMap<String,Object>();
    private String cptName;
    private String userName;
    private String singleSQL;
    private Map<String,String> otherParas;
    private Map<String,String[]> ResultMap = new HashMap<String,String[]>() ;
    private Result4DS result4DS;
    public boolean interrupt = false;
    public String ResultReport = "";

    public DSPreAnalysis(String cptName, String outerParas, String userName) {
        SCurrentEnv = null;
        this.cptName = cptName;
        this.userName = userName;
        otherParas = new URLReader(outerParas).getParas();
        try {
            workbook = (WorkBook) TemplateWorkBookIO.readTemplateWorkBook(cptName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean judgeWorkbook(){
        if(workbook != null){
            return true;
        }else{
            return false;
        }
    }
    public String getCptName(){ return this.cptName; }
    public String getUserName(){ return this.userName; }

    public void analysistart(){
        getDataSetParas();
    }

    private void getDataSetParas(){
        Iterator itor = workbook.getTableDataNameIterator();
        while(itor.hasNext() && ! interrupt) {
            String dsName = (String) itor.next();
            TableData tableData = workbook.getTableData(dsName);
            if (tableData instanceof DBTableData) {
                DBTableData dt = (DBTableData) tableData;
                singleSQL = dt.getQuery();
                String dbName = dt.getDatabase().toString().replace("[","").replace(" Database]","");
                ParameterProvider[] ps = dt.getParameters(Calculator.createCalculator());
                for(ParameterProvider var2 : ps) {
                    parameterMap.put(var2.getName(), var2.getValue());
                }
                addParas(otherParas);
                getNewQueryString(parameterMap,singleSQL,dbName,dsName);
            }
            parameterMap.clear();
        }
    }

    private void addParas(Map<String,String> otherparas){
        parameterMap.putAll(otherparas);
    }

    private void getNewQueryString(Map<String,Object> map,String sql,String dbname,String dsname){
        Calculator ca = Calculator.createCalculator();
        ParameterMapNameSpace ns = ParameterMapNameSpace.create(map);
        ca.pushNameSpace(ns);
        try {
            String sqlString = TemplateUtils.renderTpl(ca, sql);
            sqlAnalysis(dbname,dsname,sqlString);
        }catch(Exception e){
            this.analysisInterrput(2);
            interrupt = true;
        }
    }

    private void sqlAnalysis(String dbname,String dsname,String sqlString){
        long t;
        int l;
        long m;
        String e;
        QueryAnalysis qA = new QueryAnalysis(dbname,sqlString,dsname);
        qA.analysistart();
        t = qA.getTimeConsum();
        l = qA.getLineConsum();
        m = qA.getMemoryConsum();
        e = qA.getQueryExplain();
        qA.analysistop();
        ResultMap.put(dsname,new String[]{dbname,e,String.valueOf(t), String.valueOf(l), String.valueOf(m)});
    }

    public void analysistop(){
        /*consuming report*/
        result4DS = new Result4DS(cptName, userName, (HashMap) ResultMap);
        ResultReport = result4DS.objectHandle();
        /*result gather*/
        Gather2DB4DS ResultGather = new Gather2DB4DS(cptName, result4DS.getStoreHtml(), userName,ResultMap,URLReader.getParasString(otherParas));
        if(ResultGather.containerPrepare()){
            ResultGather.containerGather();
        }
    }

    public void analysisInterrput(int reasonid){
        String[] var = {"cptname","result"};
        switch(reasonid) {
            case 1:
                var[0] = cptName;
                var[1] = "The specified template path was not found";
                ResultMap.clear();
                ResultMap.put("tplnotfound",var);
                ResultReport = new Result4NotFound((HashMap) ResultMap).ObjectHandle();
                break;
            case 2:
                var[0] = cptName;
                var[1] = "Template parameters error,ensuring accuracy ";
                ResultMap.clear();
                ResultMap.put("tplparaserr",var);
                ResultReport = new Result4Interrupt((HashMap) ResultMap).ObjectHandle();
                break;
            default:
                ResultReport = this.analysisInterrput();
        }
    }
}
