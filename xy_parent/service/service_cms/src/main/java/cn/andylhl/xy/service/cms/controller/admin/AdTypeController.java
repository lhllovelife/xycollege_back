package cn.andylhl.xy.service.cms.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.cms.entity.AdType;
import cn.andylhl.xy.service.cms.service.AdTypeService;
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
 * 推荐位 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-02-24
 */
//@CrossOrigin
@Api(tags = "推荐位管理")
@Slf4j
@RestController
@RequestMapping("/admin/cms/ad-type")
public class AdTypeController {

    @Autowired
    private AdTypeService adTypeService;

    @ApiOperation("查询全部推荐位")
    @GetMapping("/list")
    public R listAllAdType() {
        log.info("进入service_cms, 查询全部推荐位");
        List<AdType> adTypeList = adTypeService.list();
        return R.ok().data("items", adTypeList);
    }

    @ApiOperation("推荐位分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPageAdType(
            @ApiParam(value = "页码", required = true) @PathVariable("page") Long page,
            @ApiParam(value = "每页显示记录数", required = true) @PathVariable("limit") Long limit) {
        log.info("进入service_cms, 分页查询推荐位");

        // 1. 封装分页信息对象
        Page<AdType> pageParam = new Page<>(page, limit);

        // 2. 调用service执行分页
        Page<AdType> pageModel = adTypeService.page(pageParam);

        // 3. 准备返回值
        // 查询结果集合
        List<AdType> adTypeList = pageModel.getRecords();
        // 总记录条数
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", adTypeList);
    }


    @ApiOperation("新增推荐位")
    @PostMapping("/save")
    public R saveAdType(
            @ApiParam(value = "推荐位对象", required = true)
            @RequestBody AdType adType) {
        log.info("进入service_cms, 新增推荐位");

        boolean result = adTypeService.save(adType);

        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "更新推荐位", notes = "根据id修改")
    @PutMapping("/update")
    public R updateAdTypeById(@ApiParam(value = "推荐位对象", required = true) @RequestBody AdType adType) {
        log.info("进入service_cms, 更新推荐位");

        boolean result = adTypeService.updateById(adType);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据id删除推荐位")
    @DeleteMapping("/remove/{id}")
    public R removeAdTypeById(@ApiParam(value = "推荐位id") @PathVariable("id") String id) {
        log.info("进入service_cms, 根据id删除单个推荐位");

        // 1. 删除推荐位
        boolean result = adTypeService.removeById(id);

        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据id获取推荐位信息")
    @GetMapping("/get/{id}")
    public R getAdTypeById(@ApiParam(value = "推荐位id", required = true) @PathVariable("id") String id){
        log.info("进入service_cms, 根据id获取推荐位信息");

        AdType adType = adTypeService.getById(id);

        if (adType != null) {
            return R.ok().data("item", adType);
        } else {
            return R.error().message("数据不存在");
        }
    }

}

