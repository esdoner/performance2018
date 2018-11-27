package com.fr.performance.reader;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;

/**
 * Created by yuwh on 2018/10/22
 * Description:none
 */
public class PropertiesReader {
    private static PropertiesReader ourInstance = new PropertiesReader();
    private String filepath = PropertiesReader.class.getResource("/").getPath();
    private Map<String, Map> proMap = new HashMap();

    private PropertiesReader() { }

    public static PropertiesReader getInstance() {
        return ourInstance;
    }

    /**
    * @params [propath]
    * @return java.util.Map
    * @description: proMap中没有则先载入后返回，有的话直接返回当前的配置
    */
    public Map readProperties(String propath){
        if(! proMap.containsKey(propath)) {
            this.loadPro2Map(propath,proMap);
        }
        Map<String,String> resultMap = proMap.get(propath);
        return resultMap;
    }

    /**
    * @params [propath, prokey]
    * @return java.lang.String
    * @description: none
    */
    public String readProperties(String propath,String prokey){
        return readProperties(propath).get(prokey).toString();
    }

    /**
    * @params []
    * @return java.util.Map
    * @description: 仅返回所有配置列表
    */
    public Map readProperties(){
        return proMap;
    }

    /**
    * @params [propath, filepath]
    * @return java.util.Map
    * @description: 仅将配置文件读入指定的Map
    */
    private void loadPro2Map(String propath,Map promaps) {
        Map promap = new HashMap();
        try {
            Properties props = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath+propath));
            props.load(bufferedReader);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = props.getProperty(key);
                promap.put(key, value);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            promaps.put(propath, promap);
        }
    }

    /**
    * @params [propath, prokeys, provalues]
    * @return boolean
    * @description: 修改原配置文件的属性,顺序会变的问题再说
    */
    private synchronized boolean writePro2File(String propath,Map promap) {
        try{
            Properties props = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath+propath));
            props.load(bufferedReader);
            for(Map.Entry<String,String> et : (Set<Map.Entry>)promap.entrySet()){
                props.setProperty(et.getKey(),et.getValue());
            }
            FileOutputStream fos = new FileOutputStream(filepath+propath);
            props.store(fos,"");
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
        return true;
    }

    /**
    * @params [propath]
    * @return boolean
    * @description: 更新proMap，注销掉之前的proMap
    */
    private synchronized boolean reloadPro2Map(String propath) {
        Map proMap_c = new HashMap();

        if(proMap != null) {proMap_c.putAll(proMap);}
        loadPro2Map(propath,proMap_c);
        if(proMap_c == null){
            return false;
        } else {
            Map tmp = proMap;
            proMap = proMap_c;
            tmp.clear();
        }
        return true;
    }

    public void setupPro(String propath,Map promap){
        if(writePro2File(propath,promap)){
            if(reloadPro2Map(propath)){
                System.out.println("PropertiesMap Reload Successfully");
            } else {
                System.out.println("Reload To Memory Failed");
            }
        } else {
            System.out.println("Write To Properties Failed");
        }
    }
}
