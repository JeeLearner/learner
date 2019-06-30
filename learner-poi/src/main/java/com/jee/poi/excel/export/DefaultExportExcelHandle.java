package com.jee.poi.excel.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出excel 默认实现
 *
 * @author jeeLearner
 * @date 2019/6/28
 */
public class DefaultExportExcelHandle implements IExport{

    private static final Logger logger = LoggerFactory.getLogger(DefaultExportExcelHandle.class);

    /**
     * 显示的导出表的标题
     */
    private String title;
    /**
     * 导出表的列名
     */
    private String[] rowsName;
    /**
     * data
     */
    private List<Object[]> dataList = new ArrayList<Object[]>();

    private  DefaultExportExcelHandle(){}
    private DefaultExportExcelHandle(Builder builder){
        this.title = builder.title;
        this.rowsName = builder.rowsName;
        this.dataList = builder.dataList;
    }

    @Override
    public void export(OutputStream out,Workbook wb) throws IOException {
        String sheetName = title;
        Sheet sheet = wb.createSheet(sheetName);
        //-----------
        //设置标题
        ExportStyleHelper.defaultTitleStyle(wb, rowsName.length);
        //设置列头
        ExportStyleHelper.defaultColumnTopStyle(wb, rowsName);
        //设置数据
        ExportDataHelper.defaultHandle(wb, dataList);
        //让列宽随着导出的列长自动适应
        ExportStyleHelper.autoColumeWidthStyle(sheet, rowsName.length+1);

        wb.write(out);
        logger.info("文件写入成功！");
    }



    public static class Builder{
        /**
         * 显示的导出表的标题
         */
        private String title;
        /**
         * 导出表的列名
         */
        private String[] rowsName;
        /**
         * data
         */
        private List<Object[]> dataList = new ArrayList<Object[]>();

        public DefaultExportExcelHandle.Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public DefaultExportExcelHandle.Builder setRowsName(String[] rowsName){
            this.rowsName = rowsName;
            return this;
        }

        public DefaultExportExcelHandle.Builder setDataList(List<Object[]> dataList){
            this.dataList = dataList;
            return this;
        }

        public DefaultExportExcelHandle build(){
            return new DefaultExportExcelHandle(this);
        }
    }
}

