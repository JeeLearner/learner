package com.jee.demo.poi;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/15
 * @Version:v1.0
 */
public class ExportUtils {

    private static final int MAX_NUM_XLS = 65535;

    /**
     * 导出excel
     * @param fileName
     * @param title
     * @param rowsName
     * @param dataList
     * @param response
     * @throws Exception
     */
    public static void exportExcel(String fileName, String title, String[] rowsName, List<Object[]> dataList, HttpServletResponse response) throws Exception{
        IExportExcel ex;
        String flag;
        if (dataList.size() < MAX_NUM_XLS){
            flag = ".xls";
            ex = new ExportExcelForXls(title, rowsName, dataList);
        } else {
            flag = ".xlsx";
            ex = new ExportExcelForXlsx(title, rowsName, dataList);
        }
        // 输出Excel文件
        try {
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename="+new String(fileName.getBytes("gbk"), "iso8859-1")+flag);
            response.setContentType("application/msexcel");
            response.setCharacterEncoding("utf-8");
            ex.export(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

