package cn.andylhl.xy.service.edu.mapper;

import cn.andylhl.xy.service.edu.entity.CourseCollect;
import cn.andylhl.xy.service.edu.entity.vo.CourseCollectVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */

@Repository
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {

    /**
     * 获取当前用户课程收藏列表
     * @param memberId
     * @return
     */
    List<CourseCollectVO> selectCourseCollectVOList(String memberId);
}
