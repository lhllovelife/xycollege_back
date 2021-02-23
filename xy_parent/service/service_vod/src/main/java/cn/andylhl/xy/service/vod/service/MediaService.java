package cn.andylhl.xy.service.vod.service;

import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/***
 * @Title: MediaService
 * @Description: 媒资业务
 * @author: lhl
 * @date: 2021/2/17 17:35
 */
public interface MediaService {

    /**
     * 上传视频
     * @param inputStream 视频的文件流
     * @param originalFileName 文件原始名称
     * @return 视频上传成功后的id
     */
    String uploadVideo(InputStream inputStream, String originalFileName);

    /**
     * 根据videoId集合删除视频
     * @param videoIdList
     * @return
     */
    void removeVideoByIdList(List<String> videoIdList) throws ClientException;

    /**
     * 获取视频播放地址和封面地址
     * @param videoSourceId
     * @return
     */
    Map<String, Object> getPlayUrlAndCoverUrl(String videoSourceId) throws ClientException;
}
