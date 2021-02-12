package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
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
}
