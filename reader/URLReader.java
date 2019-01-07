package com.fr.performance.reader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuwh on 18.8.8
 * Description:none
 */
public class URLReader {
    private String cUrl;
    private String noParasurl = null;
    private String paras = null;

    public URLReader(String var1){
        cUrl = var1;
        this.urlParse();
    }

    /**
    * @params []
    * @return void
    * @description:分离url地址和参数
    */
    private void urlParse(){
        cUrl = cUrl.trim();

        if (cUrl.length() > 0) {
            if(cUrl.indexOf("?") >= 0){
                String[] cUrlSplit = cUrl.split("[?]");
                if (cUrlSplit.length > 0) {
                    noParasurl = (cUrlSplit[0] != null && cUrlSplit[0] != "") ? cUrlSplit[0] : "";
                    paras = (cUrlSplit[1] != null && cUrlSplit[1] != "") ? cUrlSplit[1] : "";
                }
            } else {
                noParasurl = "";
                paras = cUrl;
            }
        }
    }

    /**
    * @params []
    * @return java.util.Map<java.lang.String,java.lang.String>
    * @description: 按‘&’和‘=’拆分
    */
    public Map<String,Object> getParas(){
        Map<String,Object> var5 = new HashMap<String,Object>();
        if(paras == null || paras == ""){
            return var5;
        }
        String [] var6 = paras.split("[&]");
        for(String var7:var6){
            String [] var8 = var7.split("[=]");
            if(var8.length > 1){
                var5.put(var8[0],var8[1]);
            }else if(var8[0].length() > 0 && var8[1].length() == 0){
                var5.put(var8[0],"");
            }
        }
        return var5;
    }

    public static String getParasString(Map<String,Object> parasMap){
        String str = "";
        String key = "";
        String val = "";
        for(Map.Entry<String, Object> ent : parasMap.entrySet()){
            key = ent.getKey();
            val = ent.getValue().toString();
            if(str == ""){
                str += key+"="+val;
            }else{
                str += "&"+key+"="+val;
            }
        }
        return str;
    }

    private void setParas(String var2){
        paras = var2;
    }

    private void setNoParasurl(String var3){
        noParasurl = var3;
    }

    private void setCUrl(String var4){
        cUrl = var4;
    }
}