package com.fr.performance.gatherer;

/**
 * Created by yuwh on 2018/9/19
 * Description:none
 */
public interface AuthInfoProvider {

    void setRequestUrl();

    void setUser();

    void setPassword();

    void setKey();

    String showMessage(String var1);

    String testMessage(String var1);
}