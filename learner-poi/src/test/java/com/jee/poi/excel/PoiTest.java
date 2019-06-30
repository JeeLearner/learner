package com.jee.poi.excel;

/**
 * @author jeeLearner
 * @date 2019/6/30
 */

import com.jee.poi.excel.demo.export.DemoExportController;
import com.jee.poi.excel.demo.importy.DemoImportController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 1.MVC请求模拟
 */
@RunWith(SpringRunner.class)
@WebMvcTest({DemoImportController.class, DemoExportController.class})
//@WebMvcTest(value = {})
public class PoiTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 测试文件导出
     * @throws Exception
     */
    @Test
    public void testExport() throws Exception {
        //mvc调用
        mvc.perform(get("/test/export"));
    }

    /**
     * 测试文件导入
     * @throws Exception
     */
    @Test
    public void fileTest1() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "import_test.xlsx","application/ms-excel", new FileInputStream(new File("D://import_test.xlsx")));
        mvc.perform(multipart("/test/import")
                .file(mockMultipartFile))
                .andExpect(status().isOk());
    }
}

