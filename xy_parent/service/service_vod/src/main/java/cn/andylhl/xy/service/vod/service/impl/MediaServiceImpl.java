package cn.andylhl.xy.service.vod.service.impl;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.vod.service.MediaService;
import cn.andylhl.xy.service.vod.util.AliyunVodSDKUtils;
import cn.andylhl.xy.service.vod.util.VodProperties;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

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
     * 根据videoId集合删除视频
     *
     * @param videoIdList
     * @throws ClientException
     */
    @Override
    public void removeVideoByIdList(List<String> videoIdList) throws ClientException {

        //1.  初始化client
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(vodProperties.getKeyId(), vodProperties.getKeySecret());
        DeleteVideoRequest request = new DeleteVideoRequest();

        //2.准备videoId数据
        //  视频ID列表。多个ID使用英文逗号（,）分隔。最多支持20个。
        StringBuffer idListStr = new StringBuffer();

        int size = videoIdList.size();

        // 遍历数组，拼接数据
        // size = 10  1 10
        // size = 20  1 20
        // size = n * 20 + m
        int cnt = 0;
        for (int i = 1; i <= size; i++) {

            if (i % 20 == 0 || i == size) {
                // 当为20的整数倍，或者达到最后一个时执行删除
                idListStr.append(videoIdList.get(i - 1));
                log.info("第" + (++cnt) + "组：" + idListStr.toString());
                //支持传入多个视频ID，多个用逗号分隔
                request.setVideoIds(idListStr.toString());
                // 删除视频
                client.getAcsResponse(request);
                // 清空idListStr
                idListStr = new StringBuffer();
            } else if (i % 20 > 0) {
                // 拼接数据
                idListStr.append(videoIdList.get(i - 1));
                idListStr.append(",");
            }
        }
    }

}
