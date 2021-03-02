package cn.andylhl.xy.service.oss;

import cn.andylhl.xy.service.oss.service.FileService;
import cn.andylhl.xy.service.oss.util.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/***
 * @Title: TestAliyuOss
 * @Description: 测试oss服务环境
 * @author: lhl
 * @date: 2021/2/6 18:08
 */
@SpringBootTest
public class TestAliyuOss {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private FileService fileService;

    @Test
    public void teetUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("F:\\xylogo.png"));
        String avatarUrl = fileService.upload(inputStream, "avatar", "lhl.jpg");
        System.out.println("avatarUrl: " + avatarUrl);
    }

    @Test
    public void testBucketExist() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getKeyId(), ossProperties.getKeySecret());

        boolean exists = ossClient.doesBucketExist(ossProperties.getBucketName());
        String location = ossClient.getBucketLocation(ossProperties.getBucketName());
        System.out.println(exists);
        System.out.println(location);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void testPassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }

}
