package com.fr.performance.preanalysis;

import com.fr.performance.handler.Connect2DB4Mysql;
import com.fr.performance.report.ELement.ElementTable;
import com.fr.third.alibaba.druid.pool.DruidPooledResultSet;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuwh on 2018/9/27
 * Description:none
 */
public class QueryAnalysis extends Analysis{
    private Connect2DB4Mysql conn;
    private String queryString;
    private String explainId;
    private ResultSet queryRst;
    private long t1 = 0L,t2 = 0L;
    private int l = 0;
    private long m = 0;
    public static String targetField1 = "rowData";
    public static String targetField2 = "rows";

    public QueryAnalysis(String dbname,String sqlString,String dsname) {
        conn = new Connect2DB4Mysql(dbname);
        this.queryString = sqlString;
        explainId = dsname;
    }

    public void analysistart(){
        if(conn.createCon()){
            t1 = System.currentTimeMillis();
            queryRst = conn.executeDQL(queryString);
            t2 = System.currentTimeMillis();
        }
    }

    public long getTimeConsum(){
        return t2 - t1;
    }

    public int getLineConsum(){
        if(queryRst != null) {
            try {
                queryRst.last();
                l = queryRst.getRow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return l;
    }

    public long getMemoryConsum() {
        if(queryRst != null) {
            Object obj = ((DruidPooledResultSet) queryRst).getRawResultSet();
            Field field = null;
            boolean simpleFFound = false;
            try {
                Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();
                for(int i=0;i<fields.length;i++){
                    if(fields[i].getName().equals(targetField1)){
                        simpleFFound = true;
                    }
                }
                if(simpleFFound){
                    field = obj.getClass().getSuperclass().getDeclaredField(targetField1);
                } else {
                    field = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(targetField1);
                }
                field.setAccessible(true);
                obj = field.get(obj);
                field = obj.getClass().getDeclaredField(targetField2);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            MemAnalysis cIntrospector = new MemAnalysis();
            MemAnalysis.ObjectInfo oInfo = null;
            try {
                oInfo = cIntrospector.introspect(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            m = oInfo.getDeepSize() / (1024);
        }
        return m;
    }

    public String getQueryExplain(){
        queryRst = conn.executeDQL("EXPLAIN "+queryString);
        try {
            return ElementTable.matrix2TableString(makeResultSet(queryRst),explainId+"_explain","explain","Explain Of Dataset '"+explainId+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[][] makeResultSet(ResultSet rs) throws SQLException {
        rs.last();
        int row = rs.getRow()+1;
        rs.beforeFirst();
        int col = rs.getMetaData().getColumnCount();
        String[][] matrix = new String[row][col];
        for(int j=1;j<=col;j++){
            matrix[0][j-1] = rs.getMetaData().getColumnName(j);
        }
        int i = 1;
        while(rs.next()){
            for(int j = 1;j<=col;j++){
                matrix[i][j-1] = rs.getString(j);
            }
            i++;
        }
        return matrix;
    }

    public void analysistop(){
        try {
            queryRst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.closeCon();
    }
}
