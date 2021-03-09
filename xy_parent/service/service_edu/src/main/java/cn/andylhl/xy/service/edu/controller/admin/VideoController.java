package cn.andylhl.xy.service.edu.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Video;
import cn.andylhl.xy.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
// @CrossOrigin
@Api(tags = "课时管理")
@Slf4j
@RestController
@RequestMapping("/admin/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("/save")
    public R saveVideo(@ApiParam(value = "课时对象", required = true) @RequestBody Video video) {
        log.info("进入service_edu, 新增课时");

        boolean result = videoService.saveVideo(video);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("根据id查询课时信息")
    @GetMapping("/get/{id}")
    public R getVideoById(
            @ApiParam(value = "课时id", required = true)
            @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id查询课时信息");
        Video video = videoService.getById(id);
        if (video != null) {
            return R.ok().data("item", video);
        } else {
            return R.error().message("数据不存在");
        }
    }


    @ApiOperation("根据id更新课时")
    @PutMapping("/update")
    public R updateVideoById(
            @ApiParam(value = "课时对象", required = true)
            @RequestBody Video video) {
        log.info("进入service_edu, 根据id更新课时");
        boolean result = videoService.updateVideo(video);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id删除课时")
    @DeleteMapping("/remove/{id}")
    public R removeVideo(
            @ApiParam(value = "课时id", required = true)
            @PathVariable("id") String id) {
        log.info("进入service_edu, 根据id删除课时");

        // TODO 删除视频
        // 调用vod中的删除视频文件的接口
        videoService.removeMediaByVideo(id);

        // 数据库层面：删除课时信息(该章节下的课时信息)
        boolean result = videoService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

}

