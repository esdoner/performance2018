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
        //在配置文件中取出和更新相关的配置值
        Map var1  = PropertiesReader.getInstance().readProperties(FileRelease.PROPATHOFRELEASE);
        //模板更新路径
        String var2 = var1.get("SpecifiedCPTReleasePath").toString();
        //模板备份路径
        String var3 = var1.get("SpecifiedCPTBackUpPath").toString();
        //originname
        String var4 = var1.get("GitRemoteOriginName").toString();
        //branchname
        String var5 = var1.get("GitRemoteBranchName").toString();
        //仓库根目录，包含.git文件夹
        String var6 = var1.get("GitRepositoryRoot").toString();
        //模板默认路径截断，用于非reportlets仓库，可为空
        String var7 = var1.get("CptDefaultPathTitle").toString();

        Map op = new HashMap<String, Boolean>();
        //
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
