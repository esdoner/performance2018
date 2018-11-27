package com.fr.performance.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuwh on 2018/10/12
 * Description:none
 */
public final class FileBasicHandler {
    static{}
    /**
    * @params [src, des, option]
    * @return boolean
    * @description: use io only while option is true
    */
    public static boolean copyFile(File src, File des, boolean option) {
        if(!src.exists() || des.isDirectory() || src.isDirectory())return false;
        ensureDirectory(des.getParentFile(),true);
        if(des.exists() || option) {
            FileInputStream fi = null;
            FileOutputStream fo = null;
            FileChannel in = null;
            FileChannel out = null;

            try {
                fi = new FileInputStream(src);
                fo = new FileOutputStream(des);
                in = fi.getChannel();
                out = fo.getChannel();
                in.transferTo(0, in.size(), out);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fi.close();
                    in.close();
                    fo.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Files.copy(src.toPath(),des.toPath());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /* *
    * @params [dir, option]
    * @return boolean
    * @description: make directory while not found the dir and option is true
    */
    public static boolean ensureDirectory(File dir,boolean option) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
               return true;
            } else {
               return false;
            }
        } else if(option) {
            return dir.mkdirs();
        } else {
            return false;
        }
    }

    /**
    * @params [des, bak, cover]
    * @return boolean
    * @description: Cover the previous backup directly while cover is true.
     * Otherwise rename the old bak's name to make it inaccessible in our platform
    */
    public static boolean flexibleBackUp(File des, File bak,boolean cover) {
        if(!des.exists())return true;
        if(cover){
            return copyFile(des, bak, true);
        } else {
            if(bak.exists()){
                String format = "yyyyMMddHHmmss";
                SimpleDateFormat sdf= new SimpleDateFormat(format);
                File bakbak = new File(bak.getParent()+bak.separator+"#"+sdf.format(new Date(System.currentTimeMillis()))+"_"+bak.getName());
                bak.renameTo(bakbak);
                return copyFile(des, bak, true);
            } else {
                return copyFile(des, bak, true);
            }
        }
    }
}
