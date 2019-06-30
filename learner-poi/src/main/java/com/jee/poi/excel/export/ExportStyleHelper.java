package com.jee.poi.excel.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * Excel导出 样式辅助类
 *
 * @author jeeLearner
 * @date 2019/6/29
 */
public class ExportStyleHelper {

    /**
     * 默认标题样式
     * @param wb
     * @param lastCol
     */
    public static void defaultTitleStyle(Workbook wb, int lastCol){
        //获取sheet（默认近仅一个）
        Sheet sheet = wb.getSheetAt(0);

        //定义标题
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);

        //标题合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 1, 0,lastCol);
        sheet.addMergedRegion(cellAddresses);
        // 使用RegionUtil类为合并后的单元格添加边框
        RegionUtil.setBorderTop(BorderStyle.THIN,cellAddresses, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN,cellAddresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN,cellAddresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,cellAddresses, sheet);

        //设置标题样式和值
        CellStyle columnTopStyle = setColumnTopStyle(wb);
        titleCell.setCellStyle(columnTopStyle);
        titleCell.setCellValue(sheet.getSheetName());
    }

    /**
     * 默认列头样式
     * @param wb
     * @param rowsName
     */
    public static void defaultColumnTopStyle(Workbook wb, String[] rowsName){
        //获取sheet（默认近仅一个）
        Sheet sheet = wb.getSheetAt(0);

        // 定义所需列数及获取列头行
        int columnNum = rowsName.length;
        Row row = sheet.createRow(2);

        // 将列头设置到sheet的单元格中
        Cell cell = row.createCell(0, CellType.NUMERIC);
        //设置值
        cell.setCellValue("序号");
        CellStyle style = setColumnTopStyle(wb);
        //设置填充色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        for (int n = 0; n < columnNum; n++) {
            cell = row.createCell(n+1, CellType.STRING);
            //设置值
            CreationHelper createHelper = wb.getCreationHelper();
            cell.setCellValue(createHelper.createRichTextString(rowsName[n]));
            //设置填充色
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }

    /**
     * 默认数据单元格样式
     * @param wb
     */
    public static CellStyle defaultCellStyle(Workbook wb){
        return setCellStyle(wb);
    }

    /**
     * 自动适应列宽 样式
     * @param sheet
     * @param columnNum 列数
     * @return
     */
    public static void autoColumeWidthStyle(Sheet sheet, int columnNum){
        for (int colNum=0; colNum<columnNum; colNum++){
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum=0; rowNum<sheet.getLastRowNum(); rowNum++){
                Row currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null){
                    Cell currentCell = currentRow.getCell(colNum);
                    if (CellType.STRING.equals(currentCell.getCellType())){
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length){
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0){
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
    }






    /**
     * 定义列头样式对象
     */
    private static CellStyle setColumnTopStyle(Workbook wb){
        /** 设置字体 */
        Font font = wb.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 设置字体名字
        font.setFontName("Courier New");
        // 字体加粗
        font.setBold(true);
        // 字体斜体
        //font.setItalic(true);
        //字体删除线
        //font.setStrikeout(true);

        /** 设置样式 */
        CellStyle style = wb.createCellStyle();
        //设置字体
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);

        return style;
    }

    /**
     * 定义数据单元格样式对象
     */
    private static CellStyle setCellStyle(Workbook wb){
        /** 设置字体 */
        Font font = wb.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 设置字体名字
        font.setFontName("Courier New");

        /** 设置样式 */
        CellStyle style = wb.createCellStyle();
        //设置字体
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);

        return style;
    }
}

