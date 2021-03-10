package cn.andylhl.xy.service.statistics.service;

import cn.andylhl.xy.service.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-03-10
 */
public interface DailyService extends IService<Daily> {

    /**
     * 根据日期生成统计记录
     */
    void createStatisticsByDay(String day);

    /**
     * 根据起止日期获取图表数据
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> showChart(String begin, String end);
}
