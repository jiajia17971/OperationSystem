package com.hgsoft.zengzhiyingyong.module.rbac.rest;

import com.hgsoft.zengzhiyingyong.common.domain.BasePageController;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.UploadEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Vehicle;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.Voucher;
import com.hgsoft.zengzhiyingyong.module.rbac.service.UploadService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VehicleService;
import com.hgsoft.zengzhiyingyong.module.rbac.service.VoucherService;
import com.hgsoft.zengzhiyingyong.util.Base64Utils;
import com.hgsoft.zengzhiyingyong.util.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WQP on 2018/7/31.
 * File restful控制器
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileRestController extends BasePageController<UploadEntity>{

    private Logger logger = LoggerFactory.getLogger(FileRestController.class);


    @Autowired
    private UploadService uploadService;


    /**
     * 通过file图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload",method =RequestMethod.POST )
    public Map<String, Object>  fileUpload(@RequestParam("file") CommonsMultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String filenowname=null;//系统生成的名称
        String fileoriname=null;//原名称
        String nameid = "";
        if(file != null){
            nameid = UUIDUtils.getUUID()+ new Date().getTime();
            fileoriname = file.getOriginalFilename();//获取原名字
            filenowname = nameid +"."+ FilenameUtils.getExtension(fileoriname);//UUID生成新的唯一名字+文件扩展名
        }

        try {
//            /F:/JavaEE/HGITSpace/CaveOperationSystem/out/artifacts/CaveOperation/WEB-INF/classes/
            String url = this.getClass().getClassLoader().getResource("/").getPath();
            String path =  url.substring(0,url.indexOf("/WEB-INF/"));
            //获取输出流
            OutputStream os=new FileOutputStream(path +"/upload/" +filenowname);
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();
            //保存附件上传
            UploadEntity upload = new UploadEntity();
            upload.setFilepath(nameid);
            upload.setOrname(fileoriname);
            upload.setSize(file.getSize());
            upload.setStatus(1);
            upload.setExtension(FilenameUtils.getExtension(fileoriname));
            uploadService.save(upload);
            //返回信息
            HashMap<String, Object> data =  new HashMap<String, Object>();
            data.put("src","/fileDowload/" +nameid);
            data.put("name",nameid);
            map.put("code",0);
            map.put("msg",0);
            map.put("data",data);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("msg","文件上传错误");
            logger.info("上传附件失败！附件：" + fileoriname);
        }

        return map;
    }

    /**
     * 通过Base64上传图片
     * @param uploadEntity
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadBase64",method =RequestMethod.POST )
    public Map<String, Object>  fileUpload(UploadEntity uploadEntity) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String filenowname=null;//系统生成的名称
        String fileoriname=null;//原名称
        String nameid = "";
        if(uploadEntity != null){
            nameid = UUIDUtils.getUUID()+ new Date().getTime();
            fileoriname = uploadEntity.getOrname();//获取原名字
            filenowname = nameid +"."+ FilenameUtils.getExtension(fileoriname);//UUID生成新的唯一名字+文件扩展名
        }

        try {
            String url = this.getClass().getClassLoader().getResource("/").getPath();
            String path =  url.substring(0,url.indexOf("/WEB-INF/"));
            Base64Utils.Base64ToImage(uploadEntity.getFilepath(),path +"/upload/" +filenowname);


            //保存附件上传
            UploadEntity upload = new UploadEntity();
            upload.setFilepath(nameid);
            upload.setOrname(fileoriname);
            upload.setSize(uploadEntity.getSize());
            upload.setStatus(1);
            upload.setExtension(FilenameUtils.getExtension(fileoriname));
            upload.setVoucherid(uploadEntity.getVoucherid());
            uploadService.save(upload);
            //返回信息
            HashMap<String, Object> data =  new HashMap<String, Object>();
            data.put("src","/fileDowload/" +nameid);
            data.put("name",nameid);
            map.put("code",0);
            map.put("msg",0);
            map.put("data",data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("msg","文件上传错误");
            logger.info("上传附件失败！附件：" + fileoriname);
        }

        return map;
    }

    /**
     * 图片下载
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/fileDowload/{id}",method =RequestMethod.GET )
    public void  fileDowload(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = null;
        try{
            //获取附件文件信息
            UploadEntity at =  uploadService.getAttachment(id);
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            String filename= URLEncoder.encode(at.getOrname(),"utf-8");
            response.setContentType("multipart/form-data");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName="+filename);

            String url = this.getClass().getClassLoader().getResource("/").getPath();
            String path =  url.substring(0,url.indexOf("/WEB-INF/"));

            //通过文件路径获得File对象
            File file = new File(path +"/upload/" +id+"."+at.getExtension());

            in = new FileInputStream(file);

            //通过response获取ServletOutputStream对象(out)
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1){
                b = in.read(buffer);
                if(b != -1){
                    response.getOutputStream().write(buffer,0,b);//4.写到输出流(out)中
                }

            }
        } catch (Exception e) {
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("关闭文件IOException!");
            }
        }

    }
    /**
     * 初始化查询开库单附件
     * @param voucher
     * @return
     */
    @RequestMapping(value = "/initUpload", method = RequestMethod.POST)
    public List<UploadEntity> initUpload(Voucher voucher) {
        try {
            logger.info("反序列化的voucher为 :" + voucher);
           int id =  Integer.parseInt(voucher.getId());
           List<UploadEntity> list =  uploadService.findAttaches(id);
          return  list;


        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }


}