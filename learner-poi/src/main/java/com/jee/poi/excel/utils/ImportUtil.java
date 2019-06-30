package com.jee.poi.excel.utils;

import com.jee.poi.excel.importy.Iimport;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Excel导入 工具类
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class ImportUtil {

    /**
     * 导入excel
     * @param ii
     * @param file
     * @param tempdateCode
     */
    public static List<String[]> importExcel(Iimport ii, MultipartFile file, String tempdateCode) throws Exception {
        String fileName = file.getOriginalFilename();

        boolean isXls = fileName.endsWith("xls");
        boolean isXlsx = fileName.endsWith("xlsx");
        if (!isXls && !isXlsx){
            throw new Exception("文件后缀名仅支持xls/xlsx");
        }

        InputStream inputStream = null;
        Workbook wb = null;
        try {
            inputStream = file.getInputStream();
            wb = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //判断模板是否匹配
        boolean flag = ii.validateTemplate(wb, tempdateCode);
        if (!flag){
            throw new Exception("文件与模板不匹配！");
        }

        //获取有效数据行号索引值
        int i = tempdateCode.indexOf("_");
        String firstRowNumStr = tempdateCode.substring(i + 1, tempdateCode.length());
        int firstRowNum = Integer.valueOf(firstRowNumStr);

        //执行导入
        List<String[]> excelList = ii.importExcel(wb, firstRowNum);
        return excelList;
    }
}

