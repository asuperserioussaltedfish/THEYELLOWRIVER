package com.example.theyellowriver.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 14158
 */
public interface OssService {
    /**
     * 利用oss显示图片
     * @param file 。
     * @return 。
     * @throws Exception 。
     */
    String uploadFileAvatar(MultipartFile file)  throws Exception;
}
