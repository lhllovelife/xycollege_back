package cn.andylhl.xy.service.vod.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.vod.service.MediaService;
import cn.andylhl.xy.service.vod.util.VodProperties;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/***
 * @Title: MediaServiceImpl
 * @Description: 媒资业务实现类
 * @author: lhl
 * @date: 2021/2/17 17:37
 */
@Slf4j
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(InputStream inputStream, String originalFileName) {

        String accessKeyId = vodProperties.getKeyId();
        String accessKeySecret = vodProperties.getKeySecret();
        // 上传后视频的标题（原始文件名去掉后缀）
        // 第一集.mp4 => 第一集
        String title = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        // 创建请求对象，设置请求参数
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, originalFileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        // 发送请求，得到响应
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = response.getVideoId();
        log.info("videoId: " + videoId);

        // 根据 VideoId 是否为空串判断上传是否成功
        if (StringUtils.isEmpty(videoId)) {
            // 上传失败
            log.error("阿里云视频上传失败：" + response.getCode() + "-" + response.getMessage());
            // 抛出自定义异常
            throw new XyCollegeException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        return videoId;
    }

    /**
     * 上传视频可设置的参数
     */
    private static void testUploadStream() {

        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        //request.setCallback("http://callback.sample.com");
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");

    }
}
