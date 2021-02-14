package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Chapter;
import cn.andylhl.xy.service.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */

@CrossOrigin
@Api(tags = "章节管理")
@Slf4j
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("新增章节")
    @PostMapping("/save")
    public R saveChapter(@ApiParam(value = "章节对象", required = true) @RequestBody Chapter chapter) {
        log.info("进入service_edu, 新增章节");
        boolean result = chapterService.save(chapter);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("根据id查询章节")
    @GetMapping("/get/{id}")
    public R getChapterById(
            @ApiParam(value = "章节id", required = true)
            @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id获取章节对象信息");
        Chapter chapter = chapterService.getById(id);
        if (chapter != null) {
            return R.ok().data("item", chapter);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id更新章节")
    @PutMapping("/update")
    public R updateChapterById(
            @ApiParam(value = "章节对象", required = true)
            @RequestBody Chapter chapter) {
        log.info("进入service_edu, 根据id更新章节");
        boolean result = chapterService.updateById(chapter);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id删除章节")
    @DeleteMapping("/remove/{id}")
    public R removeChapter(
            @ApiParam(value = "章节id", required = true)
            @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id删除章节");

        // TODO 删除视频
        // 调用vod中的删除视频文件的接口

        // 数据库层面：删除章节信息(该章节下的课时信息)
        boolean result = chapterService.removeChapterById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

}

