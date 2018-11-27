package com.fr.performance.gatherer;

/**
 * Created by yuwh on 2018/10/18
 * Description:none
 */
public class Gather2DB4Release extends Gather2DB{
    private String path;
    private String type;
    private String name;

    public Gather2DB4Release(String var1, String var2, String var3) {
        super("performance2018");
        setContainerType("2DB4Release");
        this.path = var1;
        this.type = var2;
        this.name = var3;
    }

    @Override
    public boolean containerPrepare() {
        String fields = "release_path,release_time,release_type,release_name";
        sqlString = "INSERT INTO cptrelease_record ("+fields+") VALUES ('"+path+"', UNIX_TIMESTAMP(),'"+type+"','"+name+"')";
        if(sqlString.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void containerGather() {
        try {
            conn.executeDML(sqlString);
            sqlString = "UPDATE cptanalysis_record set test_valid = 0 WHERE test_valid = 1 AND test_path = '"+path+"' AND test_name = '"+name+"'";
            conn.executeDML(sqlString);
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
