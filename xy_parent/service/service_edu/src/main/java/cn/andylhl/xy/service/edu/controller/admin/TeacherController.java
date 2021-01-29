package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Api(tags = "讲师管理")
@Slf4j
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询全部讲师
     * @return
     */
    @ApiOperation("查询所有讲师")
    @GetMapping("/list")
    public List<Teacher> listAll() {
        log.info("进入service_edu, 查询全部讲师");
        return teacherService.list();
    }

    /**
     * 根据id逻辑删除单个讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除讲师", notes = "根据id逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Boolean removeTeacherById(@ApiParam(value = "讲师id") @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id逻辑删除单个讲师");
        Boolean result = teacherService.removeById(id);
        return result;
    }

}

