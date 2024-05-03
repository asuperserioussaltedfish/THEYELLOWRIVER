//package com.example.theyellowriver.utils;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.PutObjectRequest;
//import com.aliyun.oss.model.PutObjectResult;
//import com.example.theyellowriver.config.AliyunConfiguration;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
///**
// * @author 14158
// */
//public class UploadLocalFile {
//    public  String UploadLocalFile(String filePath, String fileName) {
//        AliyunConfiguration aliyunConfiguration = new AliyunConfiguration();
//
//
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(aliyunConfiguration.getEndPoint(), aliyunConfiguration.getAccessKeyId(), aliyunConfiguration.getAccessKeySecret());
//
//        try {
//
//            byte[] content = Files.readAllBytes(Paths.get(filePath));
//
//             // 打印字节数组长度和内容
//            if (content != null) {
//                System.out.println("文件内容长度：" + content.length);
//            }
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunConfiguration.getBucketName(),fileName, new ByteArrayInputStream(content));
//            // 设置该属性可以返回response。如果不设置，则返回的response为空。
//            putObjectRequest.setProcess("true");
//
//            // 创建PutObject请求。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            // 如果上传成功，则返回200。
//            System.out.println(result.getResponse().getStatusCode());
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (ossClient != null) {
//
//                ossClient.shutdown();
//            }
//        }
//
//        String url = "https://" + aliyunConfiguration.getBucketName() + "." + aliyunConfiguration.getEndPoint() + "/" + fileName;
//
//        return url;
//    }
//}
