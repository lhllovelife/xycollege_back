package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Chapter;
import cn.andylhl.xy.service.edu.entity.Video;
import cn.andylhl.xy.service.edu.entity.vo.ChapterVO;
import cn.andylhl.xy.service.edu.entity.vo.VideoVO;
import cn.andylhl.xy.service.edu.feign.VodMediaRemoteService;
import cn.andylhl.xy.service.edu.mapper.ChapterMapper;
import cn.andylhl.xy.service.edu.mapper.VideoMapper;
import cn.andylhl.xy.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Slf4j
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VodMediaRemoteService vodMediaRemoteService;

    /**
     * 删除章节信息(该章节下的课时信息)
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean removeChapterById(String id) {

        // 删除该章节下的课时信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        videoMapper.delete(videoQueryWrapper);

        // 删除章节信息
        return this.removeById(id);
    }

    /**
     * 获取章节信息（嵌套数据列表）
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVO> getNestedListByCourseId(String courseId) {

        // 根据courseId获取章节信息列表
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("sort", "id"); // 先按照sort, sort字段相同，则按照id
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 获取courseId课时信息列表
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("sort", "id"); // 先按照sort升序, sort字段相同，再按照id字段升序
        List<Video> videoList = videoMapper.selectList(videoQueryWrapper);

        // 属性copy List<ChapterVO> chapterVOList
        List<ChapterVO> chapterVOList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            chapterVOList.add(chapterVO);
        }

        // 属性copy List<VideoVO> videoVOList
        List<VideoVO> videoVOList = new ArrayList<>();
        for (Video video : videoList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(video, videoVO);
            videoVOList.add(videoVO);
        }

        // chapterVOList 建立章节id和章节对象的map集合
        Map<String, ChapterVO> idChapterVORelation = new HashMap<>();
        for (ChapterVO chapterVO : chapterVOList) {
            idChapterVORelation.put(chapterVO.getId(), chapterVO);
        }

        // 父子关系匹配
        for (VideoVO videoVO : videoVOList) {
            String chapterId = videoVO.getChapterId();
            ChapterVO chapterVO = idChapterVORelation.get(chapterId);
            chapterVO.getChildren().add(videoVO);
        }

        return chapterVOList;
    }

    /**
     * 根据章节id，删除该章节下的所有视频
     * @param id
     */
    @Override
    public void removeMediaByChapterId(String id) {
        List<String> videoIdList = videoMapper.selectVideoSourceIdlistByChapterId(id);
        if (videoIdList != null && videoIdList.size() > 0) {
            log.info("删除章节下的所有课时的视频：" + videoIdList);
            vodMediaRemoteService.removeVideo(videoIdList);
        }
    }
}
