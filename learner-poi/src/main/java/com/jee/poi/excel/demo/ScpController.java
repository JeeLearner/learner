package com.jee.poi.excel.demo;

import com.jee.poi.excel.utils.ScpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Scp 控制器demo
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
@Controller
@RequestMapping("/test")
public class ScpController {

    @GetMapping("/scp")
    public void testExportExcel(HttpServletResponse response, String fileName){
        InputStream in = null;
        OutputStream out = null;
        try {
            in = ScpUtil.getInstance("140.143.45.241", 22, "jee", "jee123")
                    .getStream(fileName, "/home/jee/");
            out = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition",
                    "attachment; filename="+new String(fileName.getBytes("gbk"), "iso8859-1"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");

            byte[] bytes = new byte[2048];
            int length;
            while ((length = in.read(bytes)) != -1){
                out.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }


    }
}

