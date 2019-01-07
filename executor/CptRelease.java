package com.fr.performance.executor;

import com.fr.performance.file.release.CptFileRelease;
import com.fr.performance.file.release.FileRelease;
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
    private String[] strings;
    public CptRelease(){}

    public Object run(Object[] objects) {
        return fetchResult(objects);
    }

    protected String fetchResult(Object[] objects){
        strings  = unescape(objects);
        Map var1  = PropertiesReader.getInstance().readProperties(FileRelease.PROPATHOFRELEASE);
        String var2 = var1.get("SpecifiedCPTReleasePath").toString();
        String var3 = var1.get("SpecifiedCPTBackUpPath").toString();
        String var4 = var1.get("GitRemoteOriginName").toString();
        String var5 = var1.get("GitRemoteBranchName").toString();
        String var6 = var1.get("GitRepositoryRoot").toString();
        String var7 = var1.get("CptDefaultPathTitle").toString();

        Map op = new HashMap<String, Boolean>();
        op.put("mkdir",true);
        if(objects[3].toString().indexOf("true")>=0) {
            op.put("bakbak", false);
        } else {
            op.put("bakbak", true);
        }
        op.put("onlyio",true);

        CptFileRelease d = new CptFileRelease( strings[0], var2, var3, var4, var5, var6, var7, strings[1], strings[2], strings[4], op);
        d.stepShift(6);

        d.releaseStop();
        return d.ResultReport;
    }
}
