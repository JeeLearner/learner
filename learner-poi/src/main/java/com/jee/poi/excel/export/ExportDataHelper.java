package com.jee.poi.excel.export;

import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * Excel导出 数据辅助类
 *
 * @author jeeLearner
 * @date 2019/6/29
 */
public class ExportDataHelper {

    /**
     * 默认数据处理方式
     * @param wb
     * @param dataList
     */
    public static void defaultHandle(Workbook wb,List<Object[]> dataList){
        //获取sheet（默认近仅一个）
        Sheet sheet = wb.getSheetAt(0);

        for (int i=0; i<dataList.size(); i++){
            Object[] obj = dataList.get(i);
            Row row = sheet.createRow(i+3);
            Cell cell;
            //设置序号
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(i+1);
            cell.setCellStyle(ExportStyleHelper.defaultCellStyle(wb));
            //设置真实数据
            for (int j=0; j<obj.length; j++){
                cell = row.createCell(j+1, CellType.STRING);
                if (obj[j] != null){
                    cell.setCellValue(obj[j].toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(ExportStyleHelper.defaultCellStyle(wb));
            }
        }
    }

}

