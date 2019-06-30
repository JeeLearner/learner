package com.jee.poi.excel.demo.export;

import com.jee.poi.excel.export.DefaultExportExcelHandle;
import com.jee.poi.excel.export.IExport;
import com.jee.poi.excel.utils.ExportUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeeLearner
 * @date 2019/6/29
 */
public class DemoExport {

    public static void test(){
        //文件名定义
        String filePath = "D://导出测试文件.xlsx";

        // 定义表的标题
        String title = "导出-测试数据";

        //定义表的列名
        String[] rowsName = new String[] { "名字", "部门","年龄" };

        //定义表的内容  数据库查询
        List<SimpleDemo> list = new ArrayList<>(2);
        list.add(new SimpleDemo("jeeLearner", "集团总部",12));
        list.add(new SimpleDemo("jee","研发部",12));
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < list.size(); i++) {
            SimpleDemo per = list.get(i);
            objs = new Object[rowsName.length];
            objs[0] = per.getName();
            objs[1] = per.getDeptName();
            objs[2] = per.getAge();
            dataList.add(objs);
        }

        IExport ie = new DefaultExportExcelHandle.Builder()
                .setTitle(title)
                .setRowsName(rowsName)
                .setDataList(dataList)
                .build();
        ExportUtil.exportExcel(ie, dataList.size(),filePath);
    }

    public static void main(String[] args) {
        test();
    }
}

