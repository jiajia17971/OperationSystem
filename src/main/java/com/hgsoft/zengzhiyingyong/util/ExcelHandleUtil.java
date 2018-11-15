package com.hgsoft.zengzhiyingyong.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hegc on 2016/7/20.
 * Excel解析类
 */
public final class ExcelHandleUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelHandleUtil.class);

    /**
     * 上传并解析excel文件
     * @param file 上传的文件
     * @param uploadDir 文件存放目录
     * @param columns excel文件列中文名
     * @param properties javabean属性名
     * @param classes javabean属性名对应的class
     * @param data 解析后的数据集
     * @param clazz javabean对应的class
     * @param <T>
     * @return
     */
    public  static <T> UploadStatus handleFile(MultipartFile file, String uploadDir, String[] columns, String[] properties, Class[] classes, List<T> data, Class<T> clazz) {
        Result result = upload(file, uploadDir);
        if(result.getStatus() != UploadStatus.SUCCESS) {
            return result.getStatus();
        }
        File source = result.getFile();
        try {
            UploadStatus status = parseExcel(source, columns, properties, classes, data, clazz);
            return status;
        } catch (Exception e) {
            logger.error("解析excel文件出错，{}", e.getMessage());
            return UploadStatus.EXCEPTION_ERROR;
        } finally {
            source.delete();
            logger.info("删除上传的xls文件{}", source.getAbsolutePath());
        }
    }

    /**
     * 上传文件服务类
     * @param file 原始上传文件
     * @param uploadDir 文件存放目录
     * @return
     */
    public static Result upload(MultipartFile file, String uploadDir) {
        Result result = new Result();
        File desc = null;
        String originalName = file.getOriginalFilename();
        String uploadFileSuffix = originalName.substring(originalName.lastIndexOf('.'));
        if(!".xls".equals(StringUtils.lowerCase(uploadFileSuffix))) {
            result.setStatus(UploadStatus.SUFFIX_ERROR);
            return result;
        }
        String rename = Identities.uuid() + uploadFileSuffix;
        File dir = new File(uploadDir);
        try {
            if(!dir.exists()) {
                dir.mkdirs();
                logger.info("文件存放目录{}不存在，开始新建目录。", uploadDir);
            }
            desc = new File(dir, rename);
            file.transferTo(desc);
        } catch (IOException e) {
            logger.error("上传文件出错，错误详情：{}", e.getMessage());
            result.setStatus(UploadStatus.EXCEPTION_ERROR);
            return result;
        }
        logger.info("上传文件成功。上传文件路径为：{}", desc.getAbsolutePath());
        result.setFile(desc);
        result.setStatus(UploadStatus.SUCCESS);
        return result;
    }

    /**
     * 解析excel文件(properties, classes, clz三个参数用于做反射)
     * @param file  待解析的excel文件
     * @param columns 第一列列头中文
     * @param properties javabean实体属性
     * @param classes   对应实体属性的class
     * @param data  解析后的数据集合
     * @param clz javabean的class
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> UploadStatus parseExcel(File file, String[] columns, String[] properties, Class[] classes, List<T> data, Class<T> clz) throws Exception {
        InputStream is = new FileInputStream(file);
        try {
        	
            Workbook book = WorkbookFactory.create(is); 
//            try { 
//                book = new XSSFWorkbook(is); 
//            } catch (Exception ex) { 
//                book = new HSSFWorkbook(is); 
//            } 
            
//            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            
            if (rows <= 1) {
                logger.error("上传文件{}内容为空！", file.getAbsolutePath());
                return UploadStatus.EMPTY_ERROR;
            }
            //模版第一行, 校验模版是否准确
            Row row = sheet.getRow(0);
            int cells = row.getLastCellNum();
            if (cells != columns.length) {
                logger.error("上传文件{}使用的模版错误！", file.getAbsolutePath());
                return UploadStatus.TEMPLATE_ERROR;
            }

            String[] tmpColumns = new String[cells];
            for (int i = 0; i < cells; i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    tmpColumns[i] = getValue(cell);
                }
            }
            boolean checkTemplate = true;
            for (int i = 0; i < columns.length; i++) {
                if (!StringUtils.equals(columns[i], tmpColumns[i])) {
                    checkTemplate = false;
                    break;
                }
            }

            if (!checkTemplate) {
                logger.error("上传文件{}使用的模版错误！", file.getAbsolutePath());
                return UploadStatus.TEMPLATE_ERROR;
            }
            for (int index = 1; index < rows; index++) {
                row = sheet.getRow(index);
                if (row == null) {
                    continue;
                }
                try {
                    T instance = clz.newInstance();
                    for(int propIndex = 0; propIndex < properties.length; propIndex++) {
                        String property = properties[propIndex];
                        String setMethodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
                        Method setMethod = clz.getMethod(setMethodName, classes[propIndex]);
                        String columnValue = getValue(row.getCell(propIndex));
                        if(classes[propIndex] == BigDecimal.class) {
                        	setMethod.invoke(instance, new BigDecimal(columnValue));
                        } else if (classes[propIndex] == Integer.class){
                        	setMethod.invoke(instance, (int)Double.parseDouble(columnValue));
                        } else {
                        	setMethod.invoke(instance, columnValue);
                        }
                    }
                    data.add(instance);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                    throw ex;
                    //看是否需要记录哪一行产生的错误.
                }
            }
            return UploadStatus.SUCCESS;
        } catch (Exception ex) {
            throw ex;
        } finally {
            is.close();
        }
    }

    /**
     * 获取单元格数据
     * @param cell 单元格
     * @return
     */
    private static String getValue(Cell cell) {
        if(null == cell){
            return "";
        } else {
            if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else {
                String value = StringUtils.trim(cell.getStringCellValue());
                return StringUtils.isNotBlank(value) ? value : "";
            }
        }
    }
}

/**
 * 定义处理结果类
 */
class Result{
    private UploadStatus status;    //上传状态
    private File file; //上传后的文件

    public Result() {
    }

    public Result(UploadStatus status, File file) {
        this.status = status;
        this.file = file;
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
