package com.jee.poi.excel.importy;

import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入excel 默认实现
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class DefaultImportExcelHandle implements Iimport{

    @Override
    public List<String[]> importExcel(Workbook wb, int firstRowNum) throws Exception {
        List<String[]> excelList = new ArrayList<String[]>();

        Sheet sheet = wb.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        int columnNum = sheet.getRow(firstRowNum).getPhysicalNumberOfCells();

        for (int rIndex=firstRowNum; rIndex<= lastRowNum; rIndex++){
            Row row = sheet.getRow(rIndex);
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();

            String[] s = new String[columnNum];
            for (int cIndex=firstCellNum; cIndex<lastCellNum; cIndex++){
                s[cIndex] = validateData(row, cIndex);
            }
            excelList.add(s);
        }
        return excelList;
    }

    @Override
    public boolean validateTemplate(Workbook wb, String tempdateCode) throws IOException {
        int i = tempdateCode.indexOf("_");
        String firstRowNumStr = tempdateCode.substring(i + 1, tempdateCode.length());
        int firstRowNum = Integer.valueOf(firstRowNumStr);

        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(firstRowNum-1);
        int cellCount = row.getPhysicalNumberOfCells();

        //根据tempdateCode获取excel
        //实际查询数据库
        Resource resource = new ClassPathResource("import_" + tempdateCode + ".xlsx");
        File sourceFile =  resource.getFile();

        Workbook tempWb = WorkbookFactory.create(sourceFile);
        Sheet tempSheet = tempWb.getSheetAt(0);
        Row tempRow = tempSheet.getRow(firstRowNum-1);
        int tempCellCount = tempRow.getPhysicalNumberOfCells();

        //规则可以自定义
        if (cellCount == tempCellCount){
            return true;
        } else {
            return false;
        }
    }

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
            if (cIndex == 2 && value == ""){
                throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]不能为空，请检查Excel文件！");
            }
            if (cIndex == 3 && value == ""){
                throw new Exception("["+(row.getRowNum()+1)+","+(cIndex+1)+"]不能为空，请检查Excel文件！");
            }
        }
        return value;
    }


}

