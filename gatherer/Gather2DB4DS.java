package com.fr.performance.gatherer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 */
public class Gather2DB4DS extends Gather2DB{
    private String path;
    private String paras;
    private String result;
    private String name;
    private  String queryIndexStr;

    public Gather2DB4DS(String var1, String var2, String var3, Map<String, String[]> var4, String var5) {
        super("performance2018");
        setContainerType("2DB4DS");
        path = var1;
        result = var2;
        name = var3;
        queryIndexStr = makeIndex((HashMap) var4);
        paras = var5;
    }

    private String makeIndex(HashMap var5){
        ArrayList<Integer> indexXMin = new ArrayList<>(2);
        ArrayList<Integer> indexXMax = new ArrayList<>(2);
        Set<Map.Entry> var6 = var5.entrySet();
        for(Map.Entry<String,String[]> var7:var6){
            String[] var8 = var7.getValue();
            short i = 2;
            while(i + 1 <= var8.length) {
                if(indexXMax.size() < i-1 ||  indexXMin.size() < i-1){ indexXMax.add(Integer.parseInt(var8[i]));indexXMin.add(Integer.parseInt(var8[i])); }
                if (Integer.parseInt(var8[i]) > indexXMax.get(i-2)) { indexXMax.set(i-2 , Integer.parseInt(var8[i])); }
                if (Integer.parseInt(var8[i]) < indexXMin.get(i-2)) { indexXMin.set(i-2 , Integer.parseInt(var8[i])); }
                i++;
            }
        }
        return indexXMin.toString().replaceAll("[\\[\\]]","") + "," + indexXMax.toString().replaceAll("[\\[\\]]","");
    }

    @Override
    public boolean containerPrepare() {
        result = htmParser(result);
        String fields = "test_paras,test_path,test_result,test_name,test_time,test_time_min,test_index1_min,test_index2_min,test_time_max,test_index1_max,test_index2_max";
        sqlString = "INSERT INTO cptanalysis_record("+fields+") VALUES ('"+paras+"','"+path+"','"+result+"','"+name+"', UNIX_TIMESTAMP(),"+queryIndexStr+")";
        if(sqlString.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void containerGather() {
        try {
            Object rst = conn.executeDML(sqlString);
            conFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String containerFeed() {
        return null;
    }
}
