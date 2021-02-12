package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.CourseDescription;
import cn.andylhl.xy.service.edu.entity.excel.ExcelSubjectData;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
import cn.andylhl.xy.service.edu.mapper.CourseDescriptionMapper;
import cn.andylhl.xy.service.edu.mapper.CourseMapper;
import cn.andylhl.xy.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    /**
     * 保存课程基本信息
     * @param courseInfoForm
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        // 1. 保存课程基本信息（课程描述字段单独放在了一个表中，垂直拆分）
        Course course = new Course();
        // 属性拷贝
        BeanUtils.copyProperties(courseInfoForm, course);
        // 设置状态为未发布
        course.setStatus(CourseInfoForm.COURSE_DRAFT);
        baseMapper.insert(course);

        // 2. 保存课程描述
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        // 课程信息插入后，Mybatis-Plus会将id值设置对象中
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);
        // 返回课程id
        return course.getId();
    }

    /**
     *  根据id查询课程基本信息
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        // 1. 获取课程基本信息
        Course course = baseMapper.selectById(id);
        // 如果根据id查询不到数据，则可能该条课程信息已被删除，返回null
        if (course == null) {
            return null;
        }
        // 2. 获取课程描述信息
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        // 3. 组装数据
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    /**
     * 更新课程信息
     * @param courseInfoForm
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        // 1. 更新课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        // 2. 更新课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoForm.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.updateById(courseDescription);

    }
}
