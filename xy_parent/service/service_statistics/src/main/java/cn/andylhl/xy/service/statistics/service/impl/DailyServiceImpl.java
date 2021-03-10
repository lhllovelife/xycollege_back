package cn.andylhl.xy.service.statistics.service.impl;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.statistics.entity.Daily;
import cn.andylhl.xy.service.statistics.feign.UcenterRemoteService;
import cn.andylhl.xy.service.statistics.mapper.DailyMapper;
import cn.andylhl.xy.service.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-03-10
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterRemoteService ucenterRemoteService;

    /**
     * 根据日期生成统计记录
     */
    @Override
    public void createStatisticsByDay(String day) {

        // 生成记录之前先查询当天记录是否已经生成，若有则删除
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);

        // 获取每日注册人数
        R r = ucenterRemoteService.countRegisterNum(day);
        Integer registerNum = (Integer) r.getData().get("registerNum");
        Integer loginNum = RandomUtils.nextInt(20, 30);
        Integer videoViewNum = RandomUtils.nextInt(20, 30);
        Integer courseNum = RandomUtils.nextInt(20, 30);

        // 封装数据
        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    /**
     * 根据起止日期获取图表数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> showChart(String begin, String end) {

        Map<String, Object> map = new HashMap<>();

        Map<String, Object> registerMap = this.selectStatisticsByType(begin, end, "register_num");
        Map<String, Object> loginMap = this.selectStatisticsByType(begin, end, "login_num");
        Map<String, Object> videoViewMap = this.selectStatisticsByType(begin, end, "video_view_num");
        Map<String, Object> courseMap = this.selectStatisticsByType(begin, end, "course_num");

        map.put("register", registerMap);
        map.put("login", loginMap);
        map.put("videoView", videoViewMap);
        map.put("course", courseMap);

        return map;
    }

    private Map<String, Object> selectStatisticsByType(String begin, String end, String type) {

        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("date_calculated", type)
                .between("date_calculated", begin, end)
                .orderByAsc("date_calculated");
        List<Map<String, Object>> queryData = baseMapper.selectMaps(queryWrapper);
        // 遍历结果数据，将日期和统计数，分别放在两个集合中
        List<String> xData = new ArrayList<>(); // 日期列表
        List<Integer> yData = new ArrayList<>(); // 数据列表

        for (Map<String, Object> data : queryData) {
            String x = (String) data.get("date_calculated");
            Integer y = (Integer) data.get(type);
            xData.add(x);
            yData.add(y);
        }
        map.put("xData", xData);
        map.put("yData", yData);

        return map;
    }

}
