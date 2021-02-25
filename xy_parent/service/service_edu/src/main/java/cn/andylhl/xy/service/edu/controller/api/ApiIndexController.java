package cn.andylhl.xy.service.edu.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.service.CourseService;
import cn.andylhl.xy.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @Title: ApiIndexController
 * @Description: 首页
 * @author: lhl
 * @date: 2021/2/25 21:28
 */

@CrossOrigin
@Api(tags = "首页")
@Slf4j
@RestController
@RequestMapping("/api/edu/index")
public class ApiIndexController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("课程列表和热门讲师列表")
    @GetMapping("/get-index-info")
    public R index() {

        // 获取首页热门8个课程（浏览量最高的8个）
        List<Course> courseList = courseService.getHotCourseList();
        // 获取首页4个热门讲师（sort字段排序的前4个）
        List<Teacher> teacherList = teacherService.getHotTeacherList();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);    }


}
