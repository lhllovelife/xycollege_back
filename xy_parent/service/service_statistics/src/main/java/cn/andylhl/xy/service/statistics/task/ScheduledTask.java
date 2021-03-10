package cn.andylhl.xy.service.statistics.task;

import cn.andylhl.xy.service.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * @Title: ScheduledTask
 * @Description: 定时任务
 * @author: lhl
 * @date: 2021/3/10 17:54
 */

@Slf4j
@Component
@EnableScheduling // 集成SpringTask
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    /**
     * 每3s执行一次
     */
//    @Scheduled(cron = "0/3 * * * * ? ")
//    public void test() {
//        log.info("执行");
//    }

    // @Scheduled(cron = "0 0,52 19 * * ? ")
    @Scheduled(cron = "0 0 1 * * ?") // //注意只支持6位表达式
    public void createStatisticsEveryDay(){

        // 获取当前日期的上一天
        String day = new DateTime().minusDays(1).toString("yyyy-MM-dd");

        // 根据日期生成统计记录
        dailyService.createStatisticsByDay(day);
        log.info("createStatisticsEveryDay 定时统计完毕");
    }

}
