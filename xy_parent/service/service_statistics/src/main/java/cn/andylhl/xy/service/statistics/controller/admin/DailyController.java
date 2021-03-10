package cn.andylhl.xy.service.statistics.controller.admin;


import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.statistics.service.DailyService;
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
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lhl
 * @since 2021-03-10
 */
@Slf4j
@Api(tags = "统计数据管理")
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("生成统计记录")
    @PostMapping("/create/{day}")
    public R createStatisticsByDay(
            @ApiParam(value = "统计日期", required = true)
            @PathVariable(value = "day", required = true) String day) {

        log.info("进入service_statistics, 根据日期生成统计记录");

        dailyService.createStatisticsByDay(day);

        return R.ok().message("数据统计生成成功");
    }

    @ApiOperation("根据起止日期获取图表数据")
    @GetMapping("/show-chart/{begin}/{end}")
    public R showChart(
            @ApiParam(value = "开始日期", required = true)
            @PathVariable(value = "begin", required = true) String begin,

            @ApiParam(value = "结束日期", required = true)
            @PathVariable(value = "end", required = true) String end) {

        log.info("进入service_statistics, 根据日期生成统计记录");

        Map<String, Object> map = dailyService.showChart(begin, end);

        return R.ok().data("chartData", map);
    }
}

