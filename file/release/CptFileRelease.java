package com.fr.performance.file.release;

import com.fr.performance.gatherer.Gather2DB4Release;
import com.fr.performance.handler.FileBasicHandler;
import com.fr.performance.handler.GitBasicHandler;
import com.fr.performance.report.Result4Release;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yuwh on 2018/10/12
 * Description:
 * 1)没有的文件路径自动创建
 * 2)备份文件夹下的文件如果不需要可以随便删除，不会影响之后的备份
 * 3)option选项参考FileBasicHandler的方法描述
 */
public class CptFileRelease implements FileRelease {
    private String cptName;
    private String username;
    private String password;
    private String origin;
    private String branch;
    private String repository;
    private String cptitle;
    private String reason;
    private final String cptDirectory = "/reportlets";
    private File cptPath;
    private File desPath;
    private File bakPath;
    private Map<String, Boolean> option;
    private int Step;
    private boolean isInterrupt = true;
    private String releaseType;
    public LinkedHashMap resultMap = new LinkedHashMap<String,String[][]>();
    public Result4Release rst4Release;
    public Gather2DB4Release rstGather;
    public String ResultReport = "";

    public CptFileRelease(String cptName, String desPath, String bakPath,String origin,String branch,String repository,String cptitle,String user,String psw, String reas,Map op) {
        this.cptName = cptName;
        this.username = user;
        this.password = psw;
        this.origin = origin;
        this.branch = branch;
        this.repository = repository;
        this.cptitle = cptitle;
        this.desPath = new File(desPath);
        this.bakPath = new File(bakPath);
        this.reason = reas;
        this.option = op;
        Step = 0;
    }

    @Override
    public void checkFile() {
        CptFileChecker cfc = new CptFileChecker(cptName,cptDirectory);
        cptPath = new File(cfc.getRootPath()+cfc.getFileInfo("cptdirectory")+cptName);
        if(cfc.judgeExisted() && cfc.judgeHasAnalysed(reason.length() > 0) && cfc.judgePassThreshold()) {
            setSubresult("success");
            Step = 1;
        } else {
            setSubresult("fail",cfc.getFailMessage());
            setIsInterrupt(false);
        }
    }

    @Override
    public void verifyPath() {
        if(desPath != null) {
            if( FileBasicHandler.ensureDirectory(desPath,option.get("mkdir"))){
                setSubresult("success");
                Step = 2;
            } else {
                setSubresult("fail");
                setIsInterrupt(false);
            }
        }
    }

    @Override
    public void backupFile() {
        File des = new File(desPath.getPath()+cptDirectory+cptName);
        File bak = new File(bakPath.getPath()+cptDirectory+cptName);
        if(FileBasicHandler.flexibleBackUp(des,bak,option.get("bakbak"))){
            setSubresult("success");
            Step = 3;
        } else {
            setSubresult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void prepareFile() {
        File des = new File(desPath.getPath()+cptDirectory+cptName);
        releaseType =des.exists() ? "Modify" : "created";
        if(FileBasicHandler.copyFile(cptPath,des,option.get("onlyio"))){
            setSubresult("success");
            Step = 4;
        } else {
            setSubresult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void releaseFile() {
        String path = repository+"\\.git";
        GitBasicHandler a = new GitBasicHandler(path);
        a.insertAuth(username,password,origin,branch);
        if(a.addOperation((cptitle + cptName).substring(1))){
            a.commitOperation(releaseType+" "+ cptName);
            if(a.pushOperation()){
                setSubresult("success");
                Step = 5;
            } else {
                setSubresult("fail");
                setIsInterrupt(false);
            }
        } else {
            setSubresult("fail");
            setIsInterrupt(false);
        }
    }

    @Override
    public void resultGather() {
        rstGather = new Gather2DB4Release(cptName,releaseType,username,reason);
        if(rstGather.containerPrepare()){
            rstGather.containerGather();
        }
        Step = 6;
        setSubresult("success");
    }

    public void stepShift(int stepNum){
        while(stepNum > 0 && isInterrupt){
            switch(Step){
                case 0:
                    checkFile();
                    break;
                case 1:
                    verifyPath();
                    break;
                case 2:
                    backupFile();
                    break;
                case 3:
                    prepareFile();
                    break;
                case 4:
                    releaseFile();
                    break;
                case 5:
                    resultGather();
                    break;
                default:
                    setIsInterrupt(false);
            }
            stepNum -= 1;
        }
    }

    public void setIsInterrupt(boolean var){ isInterrupt = var; }

    private void setSubresult(String subresult) {
        String parentMethosName = Thread.currentThread().getStackTrace()[2].getMethodName();
        resultMap.put(parentMethosName.toLowerCase(), new String[][]{{subresult},{}});
    }

    private void setSubresult(String subresult, String[] failMessage) {
        String parentMethosName = Thread.currentThread().getStackTrace()[2].getMethodName();
        resultMap.put(parentMethosName.toLowerCase(), new String[][]{{subresult},failMessage});
    }

    public void releaseStop(){
        rst4Release = new Result4Release(resultMap);
        ResultReport = rst4Release.objectHandle();
    }
}
