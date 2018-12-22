package com.cskaoyan.hackernews2.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUtil {
    //文件上传工具类服务方法

    public static HashMap<String, Object> uploadFile(MultipartFile fileUpload) throws Exception{
        HashMap<String,Object> map=new HashMap<>();
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片
        String filePath = "/Users/macghc/idea_11_javaweb/SpringBoot/hackernews2/src/main/resources/static/images/img/";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath+fileName));
            map.put("code",0);
            map.put("msg",filePath+fileName);
        } catch (Exception e) {
           map.put("code",1);
        }
        return map;
    }
    }


