package com.jee.poi.excel.config;

/**
 * Excel导出配置
 * @author jeeLearner
 * @date 2019/6/29
 */
public class Config {

    /** 是否仅导出xlsx文件，false的话支持导出xls  */
    public static boolean isOnlyExportXlxs = true;

    /** xls最大支持行数*/
    public static final int MAX_NUM_XLS = 65535;
}

