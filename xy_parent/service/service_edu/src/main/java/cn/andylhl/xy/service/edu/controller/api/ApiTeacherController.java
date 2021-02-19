package cn.andylhl.xy.service.edu.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/***
 * @Title: ApiTeacherController
 * @Description: 网站端-讲师控制器
 * @author: lhl
 * @date: 2021/2/19 18:41
 */

@CrossOrigin
@Api(tags = "讲师")
@Slf4j
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询全部讲师
     * @return
     */
    @ApiOperation("查询所有讲师")
    @GetMapping("/list")
    public R listAllTeacher() {
        log.info("进入service_edu, 查询全部讲师");
        List<Teacher> teacherList = teacherService.list();
        return R.ok().message("获取讲师列表成功").data("items", teacherList);
    }

    /**
     * 根据id获取讲师及其主讲课程信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取讲师及其主讲课程信息")
    @GetMapping("/get/{id}")
    public R getTeacherById(@ApiParam(value = "讲师id", required = true) @PathVariable("id") String id){
        log.info("进入service_edu, 根据id获取讲师及其主讲课程信息");
        Map<String, Object> map = teacherService.getTeacherInfoandCourseInfoById(id);
        if (map != null) {
            return R.ok().data(map);
        } else {
            return R.error().message("数据不存在");
        }
    }

}
