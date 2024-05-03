package com.example.management.controller;

import com.example.management.service.OssService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 14158
 */
@RestController
@RequestMapping("/prod-api/upload")
public class OssController {

    @Resource
    private OssService ossService;
 
    /**
     * 上传头像，返回图片的url给
     */
    @PostMapping
    public String uploadOssFile(@RequestParam("image") MultipartFile file) throws Exception{
        //获取上传文件 MultipartFile
        //返回图片在oss上的路径
        return ossService.uploadFileAvatar(file);

    }

}