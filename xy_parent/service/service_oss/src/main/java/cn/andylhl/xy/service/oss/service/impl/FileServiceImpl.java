package cn.andylhl.xy.service.oss.service.impl;

import cn.andylhl.xy.service.oss.service.FileService;
import cn.andylhl.xy.service.oss.util.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/***
 * @Title: FileServiceImpl
 * @Description: 文件上传业务实现
 * @author: lhl
 * @date: 2021/2/6 18:37
 */

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 将文件上传至阿里云
     * @param inputStream 要上传的文件输入流
     * @param module 文件所属模块名
     * @param originalFilename 原始的文件名
     * @return 上传成功后返回的url地址
     */
    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {

        String endpoint = ossProperties.getEndpoint();
        String bucketname = ossProperties.getBucketName();
        String accessKeyId = ossProperties.getKeyId();
        String accessKeySecret = ossProperties.getKeySecret();


        // 1. 判断当前bucket是否存在，如果不存在，则进行创建
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        boolean exists = ossClient.doesBucketExist(ossProperties.getBucketName());
        if (!exists) {
            // 如果不存在，则创建bucket实例
            ossClient.createBucket(bucketname);
            // 设置存储空间的访问权限为公共读
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        // 2.  准备对象名称，在oss文件管理器的文件名称（module,yyyy/MM/dd,uuid + .后缀名）
        String dateTimeStr = new DateTime().toString("yyyy/MM/dd"); // 2021/02/06
        String uuidStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 从原始文件名获取扩展名 (.png)
        String extensionStr =  originalFilename.substring(originalFilename.lastIndexOf("."));
        // objectName example:" "avatar/2021/02/06/123456789.jpg"
        String objectName = module + "/" + dateTimeStr + "/" + uuidStr + extensionStr;

        // 3. 上传文件流。
        ossClient.putObject(bucketname, objectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        // 返回url地址 https://xycollege-file.oss-cn-beijing.aliyuncs.com/avatar/01.jpg
        return "https://"+ bucketname+ "."+ endpoint +"/" + objectName;
    }

    /**
     * 根据文件路径删除文件
     * @param url 阿里云oss地址: https://xycollege-file.oss-cn-beijing.aliyuncs.com/avatar/01.jpg
     */
    @Override
    public void removeFile(String url) {

        String endpoint = ossProperties.getEndpoint();
        String bucketname = ossProperties.getBucketName();
        String accessKeyId = ossProperties.getKeyId();
        String accessKeySecret = ossProperties.getKeySecret();
        // 1. 准备objectName eg: avatar/01.jpg
        // 从url中截取出objecName
        String host = "https://"+ bucketname+ "."+ endpoint +"/";
        String objectName = url.substring(host.length());
        //2. 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //3. 删除文件
        ossClient.deleteObject(bucketname, objectName);

        //4. 关闭OSSClient。
        ossClient.shutdown();
    }
}
