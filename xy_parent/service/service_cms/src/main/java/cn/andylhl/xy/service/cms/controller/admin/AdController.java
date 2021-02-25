package cn.andylhl.xy.service.cms.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.cms.entity.Ad;
import cn.andylhl.xy.service.cms.entity.vo.AdVO;
import cn.andylhl.xy.service.cms.service.AdService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 广告推荐 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-02-24
 */
@CrossOrigin
@Api(tags = "广告推荐管理")
@Slf4j
@RestController
@RequestMapping("/admin/cms/ad")
public class AdController {

    @Autowired
    private AdService adService;

    @ApiOperation("广告推荐分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPageAd(
            @ApiParam(value = "页码", required = true) @PathVariable("page") Long page,
            @ApiParam(value = "每页显示记录数", required = true) @PathVariable("limit") Long limit) {
        log.info("进入service_cms, 分页查询广告推荐信息");

        // 1. 调用service处理业务，执行分页, 返回分页对象信息
        Page<AdVO> pageModel = adService.selectPage(page, limit);

        // 2. 准备返回值
        // 查询结果集合
        List<AdVO> adVOList = pageModel.getRecords();
        // 总记录条数
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", adVOList);
    }



    @ApiOperation("新增广告推荐")
    @PostMapping("/save")
    public R saveAd(
            @ApiParam(value = "广告推荐对象", required = true)
            @RequestBody Ad ad) {
        log.info("进入service_cms, 新增广告推荐");

        boolean result = adService.save(ad);

        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "更新广告推荐", notes = "根据id修改")
    @PutMapping("/update")
    public R updateAdById(@ApiParam(value = "广告推荐对象", required = true) @RequestBody Ad ad) {
        log.info("进入service_cms, 更新广告推荐");

        boolean result = adService.updateById(ad);

        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据id删除广告推荐")
    @DeleteMapping("/remove/{id}")
    public R removeAdById(@ApiParam(value = "广告推荐id") @PathVariable("id") String id) {
        log.info("进入service_cms, 根据id删除单个广告推荐");

        // 删除图片
        adService.removeAdImageById(id);

        boolean result = adService.removeById(id);

        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据id获取广告推荐信息")
    @GetMapping("/get/{id}")
    public R getAdById(@ApiParam(value = "广告推荐id", required = true) @PathVariable("id") String id){
        log.info("进入service_cms, 根据id获取推荐位信息");

        Ad ad = adService.getById(id);

        if (ad != null) {
            return R.ok().data("item", ad);
        } else {
            return R.error().message("数据不存在");
        }
    }


}