package cn.andylhl.xy.service.edu.mapper;

import cn.andylhl.xy.service.edu.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */

@Repository
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 根据chapter查询所有的video_source_id
     * @param id
     * @return
     */
    List<String> selectVideoSourceIdlistByChapterId(String id);

    /**
     * 根据course查询所有的video_source_id
     * @param id
     * @return
     */
    List<String> selectVideoSourceIdlistByCourseId(String id);
}
