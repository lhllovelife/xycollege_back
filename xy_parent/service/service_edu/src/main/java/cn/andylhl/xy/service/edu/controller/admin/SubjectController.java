package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.edu.entity.vo.SubjectVO;
import cn.andylhl.xy.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */

// @CrossOrigin
@Api(tags = "课程分类管理")
@Slf4j
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * Excel批量导入课程分类数据(前台提交的是03版的excel)
     * @param file
     * @return
     */
    @PostMapping("/import")
    @ApiOperation("Excel批量导入课程分类数据")
    public R batchImport(
            @ApiParam(value = "Excel文件(.xls)", required = true) @RequestParam("file") MultipartFile file) {
        log.info("进入service_edu, Excel批量导入课程分类数据");
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            // 执行到这里，没有异常抛出说明执行成功
            return R.ok().message("批量导入成功");

        } catch (Exception e) {
            throw new XyCollegeException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @GetMapping("/nested-list")
    @ApiOperation("获取课程分类的嵌套数据列表")
    public R getSubjectNestedList() {
        log.info("进入service_edu, 获取课程分类的嵌套数据列表");
        List<SubjectVO> subjectVOList = subjectService.getSubjectNestedList();
        return R.ok().data("items", subjectVOList);
    }


}

