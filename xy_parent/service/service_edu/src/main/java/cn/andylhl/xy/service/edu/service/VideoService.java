package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface VideoService extends IService<Video> {

    /**
     * 保存课时信息
     * @param video
     * @return
     */
    boolean saveVideo(Video video);

    /**
     * 更新课时信息
     * @param video
     * @return
     */
    boolean updateVideo(Video video);

    /**
     * 根据课时信息删除单个视频
     * @param id
     */
    void removeMediaByVideo(String id);
}
