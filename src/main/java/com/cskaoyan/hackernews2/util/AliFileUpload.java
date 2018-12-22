package com.cskaoyan.hackernews2.util;

import com.aliyun.oss.OSSClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
@Service
public class AliFileUpload {

    // endpoint以杭州为例，其它region请按实际情况填写
    String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
    String accessKeyId = "LTAIFlFQHLKIUgg7";
    String accessKeySecret = "5QnfPOFWHW9evIVXjhDbLqtHSebpWu";


    String buketname=    "cskaoyanghc";
    public String upload(MultipartFile file) throws IOException {




        // 创建OSSClient实例
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 使用访问OSS

        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID().toString().replace("-","")+suffixName;


        // 上传
        byte[] content = new byte[0];
        content = file.getBytes();


            client.putObject(buketname, fileName,new ByteArrayInputStream(content));


        // 关闭client
        client.shutdown();


        return  "https://"+buketname+".oss-cn-shenzhen.aliyuncs.com" +"/"+fileName;

    }
}
