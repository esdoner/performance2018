package com.fr.performance.file.release;

/**
 * Created by yuwh on 2018/10/12
 * Description:none
 */
public interface FileRelease {
    void checkFile();

    void verifyPath();

    void backupFile();

    void prepareFile();

    void releaseFile();

    void resultGather();
}
