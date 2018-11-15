package com.hgsoft.zengzhiyingyong.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by JM on 2016-01-15.
 */
public class ExportExcelUtil {

    /**
     * 导出Excel
     *
     * @param fileName     excel文件名（不包含路径）
     * @param tempFileName 模板名称（包含路径）
     * @param filePathName excel文件名称（包含路径）
     * @param dataMap      excel数据
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    public static void exportExcel(String fileName, String tempFileName, String filePathName, Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(tempFileName, dataMap, filePathName);
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.toUpperCase().contains("MSIE") || userAgent.toUpperCase().contains("TRIDENT")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        fileInputStream = new FileInputStream(filePathName);
        outputStream = response.getOutputStream();
        int count = 0;
        byte[] bytes = new byte[1024];
        while ((count = fileInputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, count);
        }
        outputStream.flush();
        if (outputStream != null) {
            outputStream.close();//关闭输出流
        }
        if (fileInputStream != null) {
            fileInputStream.close();//关闭文件输入流
        }
        if (StringUtils.isNotBlank(filePathName)) {
            new File(filePathName).delete();//删除生成的excel文件
        }
    }
}
