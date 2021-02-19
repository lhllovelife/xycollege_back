package cn.andylhl.xy.service.vod.controller.admin;

import cn.andylhl.xy.common.base.exception.XyCollegeException;
import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.common.base.result.ResultCodeEnum;
import cn.andylhl.xy.service.vod.service.MediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/***
 * @Title: MediaController
 * @Description: 阿里云视频点播
 * @author: lhl
 * @date: 2021/2/17 17:58
 */

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "阿里云视频点播")
@RequestMapping("/admin/vod/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @ApiOperation("视频上传")
    @PostMapping("/upload")
    public R uploadVideo(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        log.info("进入service_vod, 上传视频");

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String videoId = mediaService.uploadVideo(inputStream, originalFilename);
            return R.ok().data("videoId", videoId).data("originalFilename", originalFilename);
        } catch (Exception e) {
            // 视频上传至业务服务器失败
            throw new XyCollegeException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }

    }

    @ApiOperation("删除视频")
    @DeleteMapping("/remove")
    public R removeVideo(
            @ApiParam(value = "视频id集合", required = true)
            @RequestBody List<String> videoIdList) {
        log.info("进入service_vod, 删除视频");

        try {
            mediaService.removeVideoByIdList(videoIdList);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            throw new XyCollegeException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }

    }
}
