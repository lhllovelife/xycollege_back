package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.form.CourseInfoForm;
import cn.andylhl.xy.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Api(tags = "课程管理")
@CrossOrigin // 跨域
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

}

