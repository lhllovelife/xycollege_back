package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.Video;
import cn.andylhl.xy.service.edu.feign.VodMediaRemoteService;
import cn.andylhl.xy.service.edu.mapper.ChapterMapper;
import cn.andylhl.xy.service.edu.mapper.CourseMapper;
import cn.andylhl.xy.service.edu.mapper.VideoMapper;
import cn.andylhl.xy.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Slf4j
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VodMediaRemoteService vodMediaRemoteService;

    /**
     * 用户点击免费，则该视频是免费的
     * 点击的是默认，需要判断当前课程的价格，
     *  如果大于0，则默认是收费；
     *  如果等于0，默认是收费
     * @param video
     * @return
     */
    @Override
    public boolean saveVideo(Video video) {

        // 判断用户点击的是 免费还是，默认
        // 免费 true
        // 默认 false
        Boolean free = video.getFree();
        String courseId = video.getCourseId();
        Course course = courseMapper.selectById(courseId);
        // 获取当前课程价格
        BigDecimal price = course.getPrice();
        int intPrice = price.intValue();

        // 默认情况
        if (!free && intPrice == 0) {
            video.setFree(true);
        }

        // 执行保存

        return this.save(video);
    }

    @Override
    public boolean updateVideo(Video video) {
        // 判断用户点击的是 免费还是，默认
        // 免费 true
        // 默认 false
        Boolean free = video.getFree();
        String courseId = video.getCourseId();
        Course course = courseMapper.selectById(courseId);
        // 获取当前课程价格
        BigDecimal price = course.getPrice();
        int intPrice = price.intValue();

        // 默认情况
        if (!free && intPrice == 0) {
            video.setFree(true);
        }

        return this.updateById(video);
    }

    /**
     * 根据课时信息删除单个视频
     * @param id
     */
    @Override
    public void removeMediaByVideo(String id) {
        // 获取课时信息
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        // 如果videoSourceId不为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            List<String> videoIdList = new ArrayList<>();
            videoIdList.add(videoSourceId);
            log.info("执行删除：videoIdList: " + videoIdList);
            // 调用远程服务执行删除
            vodMediaRemoteService.removeVideo(videoIdList);
        }
    }
}
