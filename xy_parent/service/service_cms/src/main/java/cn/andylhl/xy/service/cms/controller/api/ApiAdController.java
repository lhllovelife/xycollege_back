package cn.andylhl.xy.service.cms.controller.api;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.cms.entity.Ad;
import cn.andylhl.xy.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @Title: ApiAdController
 * @Description: 广告推荐
 * @author: lhl
 * @date: 2021/2/25 15:17
 */

//@CrossOrigin
@Api(tags = "广告推荐")
@Slf4j
@RestController
@RequestMapping("/api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;

    @ApiOperation("根据推荐位id查询广告推荐")
    @GetMapping("/list/{adTypeId}")
    public R getAdListByAdTypeId(
            @ApiParam(value = "推荐位id", required = true)
            @PathVariable("adTypeId") String adTypeId) {
        log.info("进入service_cms, 根据推荐位id查询广告推荐");

        List<Ad> adList = adService.getAdListByAdTypeId(adTypeId);

        return R.ok().data("items", adList);

    }

}
