package com.fr.performance.reader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by yuwh on 2018/12/27
 * Description:none
 */
public abstract class ClassInfoReader {

    public static List<Class<?>> getClassInfo(String packageName) {
        URL url;
        File file;
        File[] fls;
        Class<?> c = null;
        List<Class<?>> cs = new ArrayList<Class<?>>() ;
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".","/"));
            while(urls.hasMoreElements()){
                url = urls.nextElement();
                if("file".equals(url.getProtocol())){
                    file = new File(url.getPath());
                    if(file.isDirectory()){
                        fls = file.listFiles();
                        for(File fl : fls){
                            String className = fl.getName();
                            if(className.indexOf(".class")>=0) {
                                className = className.substring(0, className.lastIndexOf("."));
                                c = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
                                cs.add(c);
                            }else{
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return cs;
        }
    }

    public static boolean judgeClassExists(String packageName, String className){
        List<Class<?>> list = getClassInfo(packageName);
        boolean result = false;
        try {
            Class<?> c = Class.forName(className);
            if(list.contains(c)){
                result = true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            return result;
        }
    }

    public abstract Object getPackInfo();
}
