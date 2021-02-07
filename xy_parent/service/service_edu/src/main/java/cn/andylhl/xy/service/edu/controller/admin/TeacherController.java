package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.entity.vo.TeacherQueryVO;
import cn.andylhl.xy.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@CrossOrigin
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
    public R listAllTeacher() {
        log.info("进入service_edu, 查询全部讲师");
        List<Teacher> teacherList = teacherService.list();
        return R.ok().data("items", teacherList);
    }

    /**
     * 根据id逻辑删除单个讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除讲师", notes = "根据id逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public R removeTeacherById(@ApiParam(value = "讲师id") @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id逻辑删除单个讲师");
        Boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据id列表批量删除讲师
     * @param idList
     * @return
     */
    @ApiOperation(value = "批量删除讲师", notes = "根据id列表逻辑删除讲师")
    @DeleteMapping("/batch-remove")
    public R batchRemoveTeacher(@ApiParam(value = "讲师id列表", required = true) @RequestBody List<String> idList) {
        log.info("进入service_edu, 根据id列表批量删除讲师");
        Boolean result = teacherService.removeByIds(idList);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }


    @ApiOperation("讲师列表分页查询(关键词可选)")
    @GetMapping("/list/{page}/{limit}")
    public R listPageTeacher(
            @ApiParam(value = "页码", required = true) @PathVariable("page") Long page,
            @ApiParam(value = "每页显示记录数", required = true) @PathVariable("limit") Long limit,
            @ApiParam("分页查询条件") TeacherQueryVO teacherQueryVO) {
        log.info("进入service_edu, 分页查询讲师(关键词可选)");

        // 1. 调用service处理业务，执行分页, 返回分页对象信息
        Page<Teacher> pageModel = teacherService.selectPage(page, limit, teacherQueryVO);

        // 2. 准备返回值
        // 查询结果集合
        List<Teacher> teacherList = pageModel.getRecords();
        // 总记录条数
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", teacherList);
    }

    /**
     * 新增讲师
     * @param teacher
     * @return
     */
    @ApiOperation("新增讲师")
    @PostMapping("/save")
    public R saveTeacher(@ApiParam(value = "讲师对象", required = true)  @RequestBody Teacher teacher) {
        log.info("进入service_edu, 新增讲师");
        log.info("讲师: " + teacher);
        boolean result = teacherService.save(teacher);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    /**
     * 根据id更新讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "更新讲师", notes = "根据id修改")
    @PutMapping("/update")
    public R updateTeacherById(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        log.info("进入service_edu, 更新讲师");
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据id获取讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取讲师信息")
    @GetMapping("/get/{id}")
    public R getTeacherById(@ApiParam(value = "讲师id", required = true) @PathVariable("id") String id){
        log.info("进入service_edu, 根据id获取讲师信息");
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("item", teacher);
        } else {
            return R.error().message("数据不存在");
        }
    }

    /**
     * 根据左关键词查询讲师名字列表
     * @return
     */
    @ApiOperation("根据左关键词查询讲师名字列表")
    @GetMapping("/list/name/{key}")
    public R getNameListByKey(@ApiParam(value = "查询关键词", required = true) @PathVariable("key") String key) {
        log.info("进入service_edu, 根据左关键词查询讲师名字列表");

        List<Map<String, Object>> nameList = teacherService.getNameListByKey(key);

        return R.ok().data("nameList", nameList);
    }


}

