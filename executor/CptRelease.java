package com.fr.performance.executor;

import com.fr.performance.file.release.CptFileRelease;
import com.fr.performance.reader.PropertiesReader;
import com.fr.script.AbstractFunction;

import java.util.HashMap;
import java.util.Map;

import static com.fr.performance.reader.EscapeStrReader.unescape;

/**
 * Created by yuwh on 2018/10/10
 * Description:none
 */
public class CptRelease extends AbstractFunction {
    public CptRelease(){}

    public Object run(Object[] objects) { return fetchResult(objects); }

    private String fetchResult(Object[] objects){
        Map abc  = PropertiesReader.getInstance().readProperties("com/fr/performance/file/release/release.properties");
        String SpecifiedCPTReleasePath = abc.get("SpecifiedCPTReleasePath").toString();
        String SpecifiedCPTBackUpPath = abc.get("SpecifiedCPTBackUpPath").toString();
        String GitRemoteOriginName = abc.get("GitRemoteOriginName").toString();
        String GitRemoteBranchName = abc.get("GitRemoteBranchName").toString();
        String GitRepositoryRoot = abc.get("GitRepositoryRoot").toString();
        String CptDefaultPathTitle = abc.get("CptDefaultPathTitle").toString();

        Map op = new HashMap<String, Boolean>();
        op.put("mkdir",true);
        if(objects[3].toString().indexOf("true")>=0) {
            op.put("bakbak", false);
        } else {
            op.put("bakbak", true);
        }
        op.put("onlyio",true);
        CptFileRelease d = new CptFileRelease( unescape(objects[0].toString()), SpecifiedCPTReleasePath, SpecifiedCPTBackUpPath, GitRemoteOriginName,GitRemoteBranchName,GitRepositoryRoot,CptDefaultPathTitle, unescape(objects[1].toString()), unescape(objects[2].toString()),op);
        d.stepShift(6);
        d.releaseStop();
        return d.ResultReport;
    }
}
