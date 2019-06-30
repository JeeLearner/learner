package com.jee.poi.excel.demo.importy;

import com.jee.poi.excel.importy.Iimport;
import com.jee.poi.excel.utils.ImportUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入excel 控制层demo
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
@Controller
@RequestMapping("/test")
public class DemoImportController {

    @PostMapping("/import")
    public void testExportExcel(MultipartFile file, String tempdateCode){
        tempdateCode = "20190101_3";


        Iimport ii = new DemoImportExcelHandle();
        List<String[]> excelList = new ArrayList<>();
        try {
            excelList = ImportUtil.importExcel(ii, file, tempdateCode);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导入失败");
        }

        //设置属性
        List<ImportDemo> excelBeans = new ArrayList<>();
        for (int i=0; i<excelList.size(); i++){
            String[] strings = excelList.get(i);
            ImportDemo demo = new ImportDemo();
            demo.setName(strings[1]);
            demo.setIp(strings[2]);
            demo.setNum(strings[3]);
            excelBeans.add(demo);
        }
        System.out.println(excelBeans.size());
    }
}

