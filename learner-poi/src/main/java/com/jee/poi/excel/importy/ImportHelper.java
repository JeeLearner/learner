package com.jee.poi.excel.importy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * excel导入 辅助类
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class ImportHelper {

    public static final String PATTERN_IP = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    public static final String PATTERN_IPMASK = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)" + "\\/\\d+$";


    /**
     * 获取单元格的有效值
     * @param cell
     * @return
     */
    public static String getCellFormatValue(Cell cell){
        String cellValue = "";
        if (cell != null){
            switch (cell.getCellType()){
                case NUMERIC:
                case FORMULA:
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)){
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    cellValue = cell.getRichStringCellValue().toString();
                    break;
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }
}

