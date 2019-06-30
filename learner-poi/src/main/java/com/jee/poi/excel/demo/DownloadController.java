package com.jee.poi.excel.demo;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jeeLearner
 * @date 2019/6/30
 */
@Controller
@RequestMapping("/test")
public class DownloadController {

    /**
     * 下载文件
     * @param response
     * @param fileName
     */
//    @GetMapping(value = "download/{modelName:[a-zA-Z0-9\\.\\_]+}")
    @GetMapping(value = "download/{fileName}")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        System.out.println("开始下载-----------");
        String path = "D://upload";

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);

        OutputStream os = null;
        InputStream in = null;
        try {
            in = new FileInputStream(new File(path+ File.separator + fileName));
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) >0){
                os.write(b, 0, length);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(in);
        }
    }
}

