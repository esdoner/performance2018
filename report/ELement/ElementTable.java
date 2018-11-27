package com.fr.performance.report.ELement;

/**
 * Created by yuwh on 18.8.8
 * Description:none
 */
public final class ElementTable implements Element {
    private static String stringOutput;

    private ElementTable(){
    }

    public static String matrix2TableString(String[][] var1,String var2,String var3,String var4){
        int rownum = var1.length;
        int colnum = var1[0].length;
        stringOutput = ""
                +"<table id='"+var2+"' class='"+var3+"'><caption>"+var4+"</caption><tr>";
        for(int j = 0;j<colnum;j++){
            stringOutput += "<td>"+var1[0][j]+"</td>";
            if(j == colnum-1){stringOutput += "</tr>";}
        }
        for(int i = 1;i<rownum;i++){
            stringOutput += array2Tr(var1[i]);
        }
        stringOutput += "</table>";;
        return stringOutput;
    }

    public static String array2Tr(String[] var2){
        String var3 = "<tr>";
        int var4 = var2.length;
        for(int i = 0;i<var4;i++){
            if(var2[i] == null) {
                var3 += "<td class='nul'>" + var2[i] + "</td>";
            } else {
                var3 += "<td>" + var2[i] + "</td>";
            }
        }
        var3 += "</tr>";
        return var3;
    }
}
