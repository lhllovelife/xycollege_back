package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.CourseCollect;
import cn.andylhl.xy.service.edu.entity.vo.CourseCollectVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface CourseCollectService extends IService<CourseCollect> {

    /**
     * 新增课程收藏
     * @param courseId
     * @param memberId
     * @return
     */
    Boolean saveCourseCollect(String courseId, String memberId);


    /**
     * 获取当前用户课程收藏列表
     * @param memberId
     * @return
     */
    List<CourseCollectVO> getCourseCollectVOList(String memberId);

    /**
     * 判断当前用户是否收藏该课程
     * @param courseId
     * @param memberId
     * @return
     */
    boolean isCollectByCourseId(String courseId, String memberId);

    /**
     * 取消已收藏的课程
     * @param courseId
     * @param memberId
     * @return
     */
    boolean removeCourseCollect(String courseId, String memberId);
}
