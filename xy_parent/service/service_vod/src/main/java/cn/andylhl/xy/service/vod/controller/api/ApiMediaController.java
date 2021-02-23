package cn.andylhl.xy.service.vod.controller.api;

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

import java.util.Map;

/***
 * @Title: ApiMediaController
 * @Description: 阿里云视频点播
 * @author: lhl
 * @date: 2021/2/23 16:38
 */

@Slf4j
@CrossOrigin
@RestController
@Api(tags = "阿里云视频点播")
@RequestMapping("/api/vod/media")
public class ApiMediaController {

    @Autowired
    private MediaService mediaService;

    @ApiOperation("获取视频播放地址和封面地址")
    @GetMapping("/get-play-url/{videoSourceId}")
    public R getPlayUrlAndCoverUrl(
            @ApiParam(value = "阿里云视频文件的id", required = true)
            @PathVariable("videoSourceId") String videoSourceId) {
        log.info("进入service_vod, 获取视频播放地址和封面地址");

        try {
            Map<String, Object> map = mediaService.getPlayUrlAndCoverUrl(videoSourceId);

            return R.ok().message("获取视频播放地址成功").data(map);
        } catch (Exception e) {
            throw new XyCollegeException(ResultCodeEnum.FETCH_VIDEO_UPLOADAUTH_ERROR);
        }
    }


}
