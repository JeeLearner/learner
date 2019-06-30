package com.jee.poi.excel.demo.importy;

import com.jee.poi.excel.importy.DefaultImportExcelHandle;
import com.jee.poi.excel.importy.ImportHelper;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;

/**
 * 导入excel 自定义实现
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class DemoImportExcelHandle extends DefaultImportExcelHandle{

    @Override
    public String validateData(Row row,int cIndex) throws Exception {
        Cell cell = row.getCell(cIndex);
        String value = "";
        if (cell != null){
            value = ImportHelper.getCellFormatValue(cell);
            //验证数据
            if (cIndex == 1 && value == ""){
                throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]不能为空，请检查Excel文件！");
            }
            // 验证ip地址格式
            if (cIndex == 2 && !value.matches(ImportHelper.PATTERN_IP)) {
                throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]格式出错，请检查Excel文件！");
            }
            // 验证ip地址掩码位数
            if (cIndex == 3) {
                if(value==""){
                    throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]不能为空，请检查Excel文件！");
                }
                if(! NumberUtils.isDigits(value)){
                    throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]格式出错，请检查Excel文件！");
                }
                if( NumberUtils.toInt(value)>30 || NumberUtils.toInt(value)<1){
                    throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]格式出错，请检查Excel文件！");
                }
            }
        }
        return value;
    }


}

