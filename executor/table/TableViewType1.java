package com.fr.performance.executor.table;

import com.fr.data.AbstractTableData;
import com.fr.general.data.TableDataException;

/**
 * Created by yuwh on 2018/12/26
 * Description:fr程序数据集可用,程序数据集即使没使用也会加载
 */
public abstract class TableViewType1 extends AbstractTableData {
    protected String[] colNames;
    protected Object[][] rowData;

    public TableViewType1(){ }
    @Override
    public int getColumnCount() throws TableDataException {
        return colNames.length;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return colNames[i];
    }

    @Override
    public int getRowCount() throws TableDataException {
        return rowData.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return rowData[i][i1];
    }
}
