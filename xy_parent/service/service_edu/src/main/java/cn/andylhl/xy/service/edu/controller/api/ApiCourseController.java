package cn.andylhl.xy.service.edu.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.base.dto.CourseDTO;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.vo.ChapterVO;
import cn.andylhl.xy.service.edu.entity.vo.SubjectVO;
import cn.andylhl.xy.service.edu.entity.vo.WebCourseQueruVO;
import cn.andylhl.xy.service.edu.entity.vo.WebCourseVO;
import cn.andylhl.xy.service.edu.service.ChapterService;
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

//@CrossOrigin
@Api(tags = "课程")
@Slf4j
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ChapterService chapterService;

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

    /**
     * 课程详情
     * @return
     */
    @ApiOperation("根据id查询课程")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam(value = "课程id", required = true)
                         @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id查询课程");

        // 查询课程信息和讲师信息
        WebCourseVO webCourseVO = courseService.webGetWebCourseVOById(id);

        // 查询章节信息
        List<ChapterVO> chapterVOList = chapterService.getNestedListByCourseId(id);

        return R.ok().data("webCourseVO", webCourseVO).data("chapterVOList", chapterVOList);
    }

    /**
     * inner 供微服务进行调用
     */
    @ApiOperation("根据id获取订单中需要的课程信息和讲师信息")
    @GetMapping("/inner/get-course-dto/{courseId}")
    public CourseDTO getCourseDTO(
            @ApiParam(value = "课程id", required = true)
            @PathVariable("courseId") String courseId) {
        log.info("进入service_edu, 根据id获取订单中需要的课程信息和讲师信息");

        CourseDTO courseDTO = courseService.getCourseDTO(courseId);

        return courseDTO;

    }

    /**
     * inner 供微服务进行调用
     */
    @ApiOperation("更新课程销量")
    @GetMapping("/inner/update-buy-count/{courseId}")
    public R updateCourseBuyCount(
            @ApiParam(value = "课程id", required = true)  @PathVariable("courseId") String courseId) {

        log.info("进入service_edu, 更新课程销量");

        boolean result = courseService.updateCourseBuyCount(courseId);

        if (result) {
            return R.ok().message("销量更新成功");
        } else {
            return R.error().message("数据不存在");
        }

    }

}
