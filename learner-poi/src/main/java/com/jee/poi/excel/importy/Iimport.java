package com.jee.poi.excel.importy;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;

/**
 * Excel导入 接口
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public interface Iimport {

    /**
     * 导入excel
     * @throws Exception
     */
    /**
     * 导入excel
     * @param wb 工作簿
     * @param firstRowNum 有效数据行号索引
     * @return 导入的数据list
     * @throws Exception
     */
    List<String[]> importExcel(Workbook wb, int firstRowNum) throws Exception;

    /**
     * 模板校验
     * @param wb 工作簿
     * @param tempdateCode 模板编码
     */
    boolean validateTemplate(Workbook wb, String tempdateCode) throws IOException;

    /**
     * 数据校验
     * @param row 当前行
     * @param cIndex 当前行的列的索引
     * @return 有效数据
     * @throws Exception
     */
    String validateData(Row row, int cIndex) throws Exception ;


}

