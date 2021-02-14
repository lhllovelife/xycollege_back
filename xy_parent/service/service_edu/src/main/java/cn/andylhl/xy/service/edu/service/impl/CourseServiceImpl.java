package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.*;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
import cn.andylhl.xy.service.edu.entity.vo.CoursePublishVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseQueryVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseVO;
import cn.andylhl.xy.service.edu.feign.OssFileRemoteService;
import cn.andylhl.xy.service.edu.mapper.*;
import cn.andylhl.xy.service.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private OssFileRemoteService ossFileRemoteService;

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CourseCollectMapper courseCollectMapper;



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

    /**
     * 分页查询（关键词可选）
     * @param page
     * @param limit
     * @param courseQueryVO
     * @return
     */
    @Override
    public Page<CourseVO> selectPage(Long page, Long limit, CourseQueryVO courseQueryVO) {

        // 组装查询条件
        QueryWrapper<CourseQueryVO> queryWrapper = new QueryWrapper<>();
        String title = courseQueryVO.getTitle();
        String teacherId = courseQueryVO.getTeacherId();
        String subjectParentId = courseQueryVO.getSubjectParentId();
        String subjectId = courseQueryVO.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            // '%' title '%'
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            // c.teacher_id = ''
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            // s1.id = ''
            queryWrapper.eq("s1.id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            // s2.id = ''
            queryWrapper.eq("s2.id", subjectId);
        }

        // 按创建时间排序
        queryWrapper.orderByDesc("c.gmt_create");

        // 创建分页对象
        Page<CourseVO> pageInfo = new Page<>(page, limit);

        // 执行查询，Mybatis-Plus会自动组装分页参数
        List<CourseVO> courseVOList = baseMapper.selectPageByCourseQueryVO(pageInfo, queryWrapper);

        return pageInfo.setRecords(courseVOList);
    }

    /**
     * 删除课程封面
     * @param id
     */
    @Override
    public Boolean removeCoverById(String id) {
        Course course = baseMapper.selectById(id);
        // 判断是否存在该课程信息
        if (course != null) {
            String cover = course.getCover();
            // 判断是否有封面地址
            if (!StringUtils.isEmpty(cover)) {
                R r = ossFileRemoteService.removeFile(cover);
                return r.getSuccess();
            }
        }
        return false;
    }

    /**
     * 删除课程相关数据（数据库层面）
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean removeCourseById(String id) {

        // 删除课时信息 video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        // 删除章节信息 chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        // 删除课程评论 comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        // 删除课程收藏 collection
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        // 删除课程描述 description
        courseDescriptionMapper.deleteById(id);

        // 删除课程信息本身 course
        return this.removeById(id);
    }

    /**
     * 获取课程发布基本信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVO getCoursePublishInfo(String id) {

        return baseMapper.selectCoursePublishInfo(id);

    }
}
