package cn.andylhl.xy.service.trade.feign.fallback;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.base.dto.CourseDTO;
import cn.andylhl.xy.service.trade.feign.EduCourseRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * @Title: EduCourseRemoteServiceFallback
 * @Description:
 * @author: lhl
 * @date: 2021/3/4 11:07
 */
@Slf4j
@Service
public class EduCourseRemoteServiceFallback implements EduCourseRemoteService {

    @Override
    public CourseDTO getCourseDTO(String courseId) {
        log.info("service-edu course 降级处理");
        return null;
    }

    @Override
    public R updateCourseBuyCount(String courseId) {
        log.info("service-edu course 降级处理");
        return R.error();
    }
}
