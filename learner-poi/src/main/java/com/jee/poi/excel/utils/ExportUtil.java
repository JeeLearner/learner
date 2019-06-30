package com.jee.poi.excel.utils;

import com.jee.poi.excel.config.Config;
import com.jee.poi.excel.export.IExport;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Excel导出 工具类
 * @author jeeLearner
 * @date 2019/6/29
 */
public class ExportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 导出excel
     * @param ie
     * @param dataSize
     * @param filePath
     */
    public static void exportExcel(IExport ie, int dataSize, String filePath){
        Workbook wb;
        if (!Config.isOnlyExportXlxs && dataSize < Config.MAX_NUM_XLS) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            ie.export(out, wb);
            logger.info("导出成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出失败！");
        } finally {
            IOUtils.closeQuietly(out);
            logger.info("关流成功！");
        }
    }

    public static void exportExcel(IExport ie, int dataSize, HttpServletResponse response,String fileName){
        Workbook wb;
        String flag = ".xlsx";
        if (!Config.isOnlyExportXlxs && dataSize < Config.MAX_NUM_XLS) {
            wb = new HSSFWorkbook();
            flag = ".xls";
        } else {
            wb = new XSSFWorkbook();
        }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename="+new String(fileName.getBytes("gbk"), "iso8859-1")+flag);
            response.setContentType("application/msexcel");
            response.setCharacterEncoding("utf-8");
            ie.export(out, wb);
            logger.info("导出成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出失败！");
        } finally {
            IOUtils.closeQuietly(out);
            logger.info("关流成功！");
        }
    }
}

