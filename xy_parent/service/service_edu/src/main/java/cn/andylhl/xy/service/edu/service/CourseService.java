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
}
