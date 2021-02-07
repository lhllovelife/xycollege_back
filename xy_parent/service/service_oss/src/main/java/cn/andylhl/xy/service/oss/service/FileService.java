package cn.andylhl.xy.service.oss.service;

import java.io.InputStream;

/***
 * @Title: FileService
 * @Description: 文件上传业务接口
 * @author: lhl
 * @date: 2021/2/6 18:34
 */
public interface FileService {

    /**
     * 将文件上传至阿里云
     * @param inputStream 要上传的文件输入流
     * @param module 文件所属模块名
     * @param originalFilename 原始的文件名
     * @return 上传成功后返回的url地址
     */
    String upload(InputStream inputStream, String module, String originalFilename);

}
