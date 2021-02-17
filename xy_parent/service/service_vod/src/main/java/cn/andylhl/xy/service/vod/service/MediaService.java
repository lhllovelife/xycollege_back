package cn.andylhl.xy.service.vod.service;

import java.io.InputStream;

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

}
