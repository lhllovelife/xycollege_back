package cn.andylhl.xy.service.edu.controller.api;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.util.JwtInfo;
import cn.andylhl.xy.common.base.util.JwtUtils;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.CourseCollect;
import cn.andylhl.xy.service.edu.entity.vo.CourseCollectVO;
import cn.andylhl.xy.service.edu.service.CourseCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@CrossOrigin // 解决跨域问题
@Api(tags = "课程收藏")
@Slf4j
@RestController
@RequestMapping("/api/edu/course-collect")
public class ApiCourseCollectController {

    @Autowired
    private CourseCollectService courseCollectService;

    /**
     * auth 代表：需要登录才能访问该接口
     */
    @ApiOperation(value = "新增课程收藏")
    @PostMapping("/auth/save/{courseId}")
    public R saveCourseCollect(
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {

        log.info("进入service_edu, 新增课程收藏");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        // 新增收藏
        Boolean result = courseCollectService.saveCourseCollect(courseId, jwtInfo.getId());
        if (result) {
            return R.ok().message("收藏成功");
        } else {
         return R.error().message("收藏失败");
        }
    }

    /**
     * auth 代表：需要登录才能访问该接口
     */
    @ApiOperation(value = "获取当前用户课程收藏列表")
    @GetMapping("/auth/list")
    public R getCourseCollectVOList(
            HttpServletRequest request) {

        log.info("进入service_edu, 获取当前用户课程收藏列表");
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        List<CourseCollectVO> courseCollectVOList = courseCollectService.getCourseCollectVOList(jwtInfo.getId());

        return R.ok().data("items", courseCollectVOList);

    }

    /**
     * auth 代表：需要登录才能访问该接口
     */

    @ApiOperation(value = "判断当前用户是否收藏该课程")
    @GetMapping("/auth/is-buy/{courseId}")
    public R isCollectByCourseId (
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {
        log.info("进入service_edu, 判断当前用户是否收藏该课程");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        boolean isCollect = courseCollectService.isCollectByCourseId(courseId, jwtInfo.getId());

        return R.ok().data("isCollect", isCollect);

    }

    /**
     * auth 代表：需要登录才能访问该接口
     */

    @ApiOperation(value = "取消已收藏的课程")
    @DeleteMapping("/auth/remove/{courseId}")
    public R removeCourseCollect(
            @ApiParam(value = "课程id", required = true) @PathVariable("courseId") String courseId,
            HttpServletRequest request) {
        log.info("进入service_edu, 取消已收藏的课程");

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        boolean result = courseCollectService.removeCourseCollect(courseId, jwtInfo.getId());

        if (result) {
            return R.ok().message("已取消收藏");
        } else {
            return R.error().message("数据不存在");
        }

    }

}

