package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
import cn.andylhl.xy.service.edu.entity.vo.CourseQueryVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存课程基本信息
     * @param courseInfoForm
     * @return
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 根据id查询课程基本信息
     * @param id
     * @return
     */
    CourseInfoForm getCourseInfoById(String id);

    /**
     * 更新课程信息
     * @param courseInfoForm
     */
    void updateCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 分页查询（关键词可选）
     * @param page
     * @param limit
     * @param courseQueryVO
     * @return
     */
    Page<CourseVO> selectPage(Long page, Long limit, CourseQueryVO courseQueryVO);

    /**
     * 删除课程封面
     * @param id
     */
    Boolean removeCoverById(String id);

    /**
     * 删除课程相关数据（数据库层面）
     * @param id
     * @return
     */
    Boolean removeCourseById(String id);
}
