package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.util.ExceptionUtils;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
import cn.andylhl.xy.service.edu.entity.vo.CoursePublishVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseQueryVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseVO;
import cn.andylhl.xy.service.edu.entity.vo.TeacherQueryVO;
import cn.andylhl.xy.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Api(tags = "课程管理")
// @CrossOrigin // 跨域
@Slf4j
@RestController
@RequestMapping("/admin/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("保存课程基本信息")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息", required = true) @RequestBody CourseInfoForm courseInfoForm){

        log.info("进入service_edu, 保存课程基本信息");
        log.info("课程信息：{}", courseInfoForm);

        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().message("保存成功").data("courseId", courseId);
    }

    @ApiOperation("根据id查询课程基本信息")
    @GetMapping("/course-info/{id}")
    public R getCourseInfoById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable(value = "id", required = true) String id) {
        log.info("进入service_edu, 根据id查询课程基本信息");
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return R.ok().data("item", courseInfoForm);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("更新课程信息")
    @PutMapping("/update-course-info")
    public R updateCourseInfo(
            @ApiParam(value = "课程信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        log.info("进入service_edu, 根据id查询课程基本信息");
        courseService.updateCourseInfo(courseInfoForm);

        return R.ok().message("修改成功");
    }

    @ApiOperation("课程列表分页查询(关键词可选)")
    @GetMapping("/list/{page}/{limit}")
    public R listPageCourse(
            @ApiParam(value = "页码", required = true) @PathVariable("page") Long page,
            @ApiParam(value = "每页显示记录数", required = true) @PathVariable("limit") Long limit,
            @ApiParam("分页查询条件") CourseQueryVO courseQueryVO) {
        log.info("进入service_edu, 分页查询课程(关键词可选)");

        // 1. 调用service处理业务，执行分页, 返回分页对象信息
        Page<CourseVO> pageModel = courseService.selectPage(page, limit, courseQueryVO);

        // 2. 准备返回值
        // 查询结果集合
        List<CourseVO> teacherList = pageModel.getRecords();
        // 总记录条数
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", teacherList);
    }

    @ApiOperation("根据id删除课程")
    @DeleteMapping("/remove/{id}")
    public R removeCourseById(
            @ApiParam(value = "课程id") @PathVariable("id") String id
    ) {
        log.info("进入service_edu, 根据id删除课程");
        // TODO 删除课程视频
        courseService.removeMediaByCourseId(id);

        // 删除课程封面
        courseService.removeCoverById(id);

        // 删除课程相关数据（数据库层面）
        Boolean reslult = courseService.removeCourseById(id);

        if (reslult) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }

    }

    @ApiOperation("获取课程发布基本信息")
    @GetMapping("/course-publish/{id}")
    public R getCoursePublishInfo(
            @ApiParam("课程id") @PathVariable("id") String id
    ) {
        log.info("进入service_edu, 获取课程发布基本信息");

        CoursePublishVO coursePublishVO = courseService.getCoursePublishInfo(id);

        if (coursePublishVO != null) {
            return R.ok().data("item", coursePublishVO);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("发布课程")
    @PutMapping("/publish-course/{id}")
    public R publishCourse(
            @ApiParam("课程id") @PathVariable("id") String id
    ) {
        log.info("进入service_edu, 发布课程");
        Boolean result = courseService.publishCourse(id);
        if (result) {
            return R.ok().message("发布成功");
        } else {
            return R.error().message("数据不存在");
        }
    }


}