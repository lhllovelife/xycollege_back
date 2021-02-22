package cn.andylhl.xy.service.edu.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.Subject;
import cn.andylhl.xy.service.edu.entity.vo.SubjectVO;
import cn.andylhl.xy.service.edu.entity.vo.WebCourseQueruVO;
import cn.andylhl.xy.service.edu.service.CourseService;
import cn.andylhl.xy.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @Title: ApiCourseController
 * @Description:
 * @author: lhl
 * @date: 2021/2/21 17:23
 */

@CrossOrigin
@Api(tags = "课程")
@Slf4j
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    /**
     * 网站端课程列表查询（参数可选）
     * @return
     */
    @ApiOperation("网站端课程列表查询（参数可选）")
    @GetMapping("/list")
    public R list(
            @ApiParam(value = "查询对象", required = true)
            WebCourseQueruVO webCourseQueruVO) {
        log.info("进入service_edu, 网站端课程列表查询（参数可选）");
        log.info(webCourseQueruVO.toString());
        List<Course> courseList = courseService.webGetCourseList(webCourseQueruVO);
        return R.ok().data("courseList", courseList);
    }

    /**
     * 获取课程分类嵌套数据
     * @return
     */
    @ApiOperation("获取课程分类嵌套数据")
    @GetMapping("/nested-list")
    public R nestedList(){
        log.info("进入service_edu, 获取课程分类嵌套数据");

        List<SubjectVO> subjectNestedList = subjectService.getSubjectNestedList();

        return R.ok().data("subjectNestedList", subjectNestedList);
    }


}
