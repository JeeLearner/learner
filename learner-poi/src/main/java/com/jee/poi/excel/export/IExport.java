package com.jee.poi.excel.export;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * Excel导出 接口
 *
 * @author jeeLearner
 * @date 2019/6/29
 */
public interface IExport {

    /**
     * 导出excel
     * @param out
     * @throws Exception
     */
    void export(OutputStream out,Workbook wb) throws Exception ;
}