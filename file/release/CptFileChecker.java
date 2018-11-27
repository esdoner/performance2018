package com.fr.performance.file.release;

import com.fr.performance.handler.Connect2DB4Mysql;

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
import java.util.Date;
import java.util.HashMap;

/**
 * Created by yuwh on 2018/10/10
 * Description:none
 */
public class CptFileChecker {
    private String rPath;
    private BasicFileAttributes aAttr;
    private HashMap<String,String> fileInfo = new HashMap<>();
    private static boolean fileExists = false;

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
        Connect2DB4Mysql conn = new Connect2DB4Mysql("performance2018");
        String queryString = "SELECT 1 FROM cptanalysis_record " +
                "WHERE test_valid = 1 AND UNIX_TIMESTAMP(now())-3600 <= test_time AND test_path = '"+fileInfo.get("cptname")+"'";
        if(conn.createCon()){
            try{
                ResultSet queryRst = conn.executeDQL(queryString);
                while(queryRst.next()){
                    return queryRst.getInt(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
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
            return false;
        }
    }

    public boolean judgeExisted(){ return fileExists; }
}
