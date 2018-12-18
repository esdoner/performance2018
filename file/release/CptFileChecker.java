package com.fr.performance.file.release;

import com.fr.performance.handler.Connect2DB4Mysql;
import com.fr.performance.setup.bucket.ThresholdBucket;
import com.fr.performance.setup.threshold.ThresholdPlate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yuwh on 2018/10/10
 * Description:none
 */
public class CptFileChecker {
    private String rPath;
    private BasicFileAttributes aAttr;
    private HashMap<String,String> fileInfo = new HashMap<>();
    private boolean fileExists = false;
    private HashMap<String,String> fileQualifications = new HashMap<>();
    private List<String> failmessage = new ArrayList<>();

    public CptFileChecker(String cptName,String cptDirectory){
        rPath = this.getClass().getResource("/").getPath();
        File  var1 = new File(rPath);
        rPath = var1.getParent();
        fileInfo.put("cptname",cptName);
        fileInfo.put("cptdirectory",cptDirectory);
        initFileInfo();
    }

    private void initFileInfo(){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf= new SimpleDateFormat(format);

        try {
            Path aPath = Paths.get(rPath+fileInfo.get("cptdirectory")+fileInfo.get("cptname"));
            BasicFileAttributeView aView =  Files.getFileAttributeView(aPath,BasicFileAttributeView.class);
            aAttr = aView.readAttributes();
            if(aAttr != null)fileExists = true;
            setFileCreated(sdf.format(new Date(aAttr.creationTime().toMillis())));
            setFileModified(sdf.format(new Date(aAttr.lastModifiedTime().toMillis())));
            setFileRecentAnalysis(String.valueOf(initFileRecentAnalysis()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int initFileRecentAnalysis(){
        int result = 0;
        //是否已经性能检查
        Connect2DB4Mysql conn = new Connect2DB4Mysql("performance2018");
        String queryString = "SELECT 1,test_time_max,test_index1_max,test_index2_max FROM cptanalysis_record " +
                "WHERE test_valid = 1 AND UNIX_TIMESTAMP(now())-3600 <= test_time AND test_path = '"+fileInfo.get("cptname")+"' ORDER BY test_time DESC LIMIT 1";
        if(conn.createCon()){
            try{
                ResultSet queryRst = conn.executeDQL(queryString);
                while(queryRst.next()){
                    fileQualifications.put("WarningLineNumber", queryRst.getString(3));
                    fileQualifications.put("MaxLineNumber", queryRst.getString(3));
                    fileQualifications.put("WarningTimeConsume", queryRst.getString(2));
                    fileQualifications.put("MaxTimeConsume",queryRst.getString(2));
                    fileQualifications.put("WarningMemoryConsume", queryRst.getString(4));
                    fileQualifications.put("MaxMemoryConsume", queryRst.getString(4));
                    fileQualifications.put("WarningDBRetrievalNumber", "0");
                    fileQualifications.put("MaxDBRetrievalNumber", "0");
                    result = queryRst.getInt(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn.closeCon();
            }
        }
        //是否已经稳定性检查，大部分还未强制检查,所以先默认是过关的
        fileQualifications.put("TestCaseRequire","1");
        Connect2DB4Mysql conn1 = new Connect2DB4Mysql("testcase2018");
        String queryString1 = "SELECT 1 FROM test_case_demand JOIN test_case_cpt ON cpt_id = demand_cpt " +
                "WHERE demand_ifpass = 0 AND concat('/',cpt_path,'/',cpt_filename) = '"+fileInfo.get("cptname")+"' LIMIT 1";
        if(conn1.createCon()){
            try{
                ResultSet queryRst1 = conn1.executeDQL(queryString1);
                while(queryRst1.next()){
                    fileQualifications.put("TestCaseRequire","0");
                }
            }catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn1.closeCon();
            }
        }
        return result;
    }

    private void setFileCreated(String var1){
        fileInfo.put("craeted",var1);
    }

    private void setFileModified(String var2){
        fileInfo.put("modified",var2);
    }

    private void setFileRecentAnalysis(String var3) {
        fileInfo.put("analysised",var3);
    }

    public String getRootPath() { return rPath; }

    public HashMap getFileInfo(){
        return fileInfo;
    }

    public String getFileInfo(String key){
        return fileInfo.get(key);
    }

    public boolean judgeHasAnalysed(){
        String var1 = getFileInfo("analysised");
        if(var1.indexOf("1")>=0){
            return true;
        }else{
            failmessage.add("fileanalysised");
            return false;
        }
    }

    public boolean judgePassThreshold(){
        ThresholdBucket curbucket = ThresholdBucket.getInstance();
        ThresholdPlate curplate = new ThresholdPlate();
        //step 1
        curplate.putLeavings(fileQualifications);
        //step 2
        curbucket.thresholdsWork(curplate);
        //step 3
        curplate.failFilter(failmessage);
        return ! Boolean.valueOf(curplate.getnotes("gameover").toString());
    }

    public boolean judgeExisted(){
        if(! fileExists) {
            failmessage.add("fileexists");
        }
        return fileExists;
    }

    public String[] getFailMessage(){
        return failmessage.toArray(new String[failmessage.size()]);
    }
}
