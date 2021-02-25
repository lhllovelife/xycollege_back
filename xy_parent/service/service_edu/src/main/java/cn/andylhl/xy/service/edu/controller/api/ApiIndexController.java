package cn.andylhl.xy.service.edu.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.service.CourseService;
import cn.andylhl.xy.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取课程列表和热门讲师列表")
    @GetMapping("/get-index-info")
    public R index() {

        log.info("进入service_edu, 获取课程列表和热门讲师列表");
        // 获取首页热门8个课程（浏览量最高的8个）
        List<Course> courseList = courseService.getHotCourseList();
        // 获取首页4个热门讲师（sort字段排序的前4个）
        List<Teacher> teacherList = teacherService.getHotTeacherList();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

    @ApiOperation("redis存储讲师测试")
    @PostMapping("save-teacher-test")
    public R saveTeacherToRedis(
            @ApiParam(value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        log.info("进入service_edu, redis存储讲师测试");

        ValueOperations operations = redisTemplate.opsForValue();

        operations.set("index:teacher", teacher);

        return R.ok();
    }


}
