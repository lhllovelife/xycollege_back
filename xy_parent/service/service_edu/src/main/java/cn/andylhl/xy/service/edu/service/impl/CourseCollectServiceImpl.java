package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.CourseCollect;
import cn.andylhl.xy.service.edu.entity.vo.CourseCollectVO;
import cn.andylhl.xy.service.edu.mapper.CourseCollectMapper;
import cn.andylhl.xy.service.edu.service.CourseCollectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {

    /**
     * 新增课程收藏
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public Boolean saveCourseCollect(String courseId, String memberId) {

        // 封装课程收藏对象
        CourseCollect courseCollect = new CourseCollect();
        courseCollect.setCourseId(courseId);
        courseCollect.setMemberId(memberId);

        return this.save(courseCollect);
    }

    /**
     * 获取当前用户课程收藏列表
     * @param memberId
     * @return
     */
    @Override
    public List<CourseCollectVO> getCourseCollectVOList(String memberId) {

        return baseMapper.selectCourseCollectVOList(memberId);
    }

    /**
     * 判断当前用户是否收藏该课程
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public boolean isCollectByCourseId(String courseId, String memberId) {

        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId);

        // courseCollec不为null，说明用户收藏了该课程，反之则为收藏
        CourseCollect courseCollect = baseMapper.selectOne(queryWrapper);

        return courseCollect == null ? false : true;
    }

    /**
     * 取消已收藏的课程
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public boolean removeCourseCollect(String courseId, String memberId) {

        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        // 根据课程id和会员id定位到用户已收藏的课程
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId);

        return this.remove(queryWrapper);
    }
}
