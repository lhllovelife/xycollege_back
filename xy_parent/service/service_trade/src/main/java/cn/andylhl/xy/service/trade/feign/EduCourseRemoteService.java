package cn.andylhl.xy.service.trade.feign;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.base.dto.CourseDTO;
import cn.andylhl.xy.service.trade.feign.fallback.EduCourseRemoteServiceFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/***
 * @Title: EduCourseRemoteService
 * @Description: edu模块提供的远程接口
 * @author: lhl
 * @date: 2021/3/4 11:06
 */

// 1. 指定对应的微服务的名称
// 2. 指定容错类
@Service
@FeignClient(value = "service-edu", fallback = EduCourseRemoteServiceFallback.class)
public interface EduCourseRemoteService {

    /**
     * 根据课程获取订单中需要的课程信息和讲师信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/api/edu/course/inner/get-course-dto/{courseId}")
    CourseDTO getCourseDTO(@PathVariable("courseId") String courseId);


    /**
     * 更新课程销量
     * @param courseId
     * @return
     */
    @ApiOperation("更新课程销量")
    @GetMapping("/api/edu/course/inner/update-buy-count/{courseId}")
    R updateCourseBuyCount(@PathVariable("courseId") String courseId);

}
