package com.fr.performance.setup.table;

import com.fr.performance.executor.table.TableViewType1;
import com.fr.performance.reader.PropertiesReader;
import com.fr.performance.setup.threshold.Threshold;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuwh on 2018/12/26
 * Description: 配置数据集
 */
public class SetupView extends TableViewType1 {
    private Map dataSource = new HashMap();

    public SetupView(){
        //可能是用了单例的问题？模板上的数据集拉不到数，做模板的时候只能手动编辑下单元格，加个id列好拖出个模子
        dataSource.putAll(PropertiesReader.getInstance().readProperties(Threshold.PROPATH));
        dataSource.put("id","1");
        String[] columnNames = new String[dataSource.size()];
        Object[][] rows = {new String[dataSource.size()]};
        int index = 0;
        for(Map.Entry<String,String> var2 : (Set<Map.Entry>)dataSource.entrySet()){
            columnNames[index] = var2.getKey();
            rows[0][index] = var2.getValue();
            index++;
        }
        this.colNames = columnNames;
        this.rowData = rows;
    }
}
