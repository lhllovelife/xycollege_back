package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.entity.vo.TeacherQueryVO;
import cn.andylhl.xy.service.edu.mapper.TeacherMapper;
import cn.andylhl.xy.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    /**
     * 执行分页, 返回分页对象信息
     * @param page
     * @param limit
     * @param teacherQueryVO
     * @return
     */
    @Override
    public Page<Teacher> selectPage(Long page, Long limit, TeacherQueryVO teacherQueryVO) {

        // 0. 用于准备查询条件 (WHERE is_deleted=0 AND (name LIKE ? AND level = ? AND join_date >= ? AND join_date <= ?) LIMIT ?,? )
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // 1. 封装分页对象(传入参数 页码和每页显示记录条数)
        Page<Teacher> pageParam = new Page<>(page, limit);
        // 2. 判断查询条件对象是否为空
        if (teacherQueryVO == null) {
            // 无查询条件，直接进行分页查询
            return baseMapper.selectPage(pageParam, queryWrapper);
        }
        // 3. 依次根据提供的teacherQueryVO关键词准备查询条件
        String name = teacherQueryVO.getName();
        Integer level = teacherQueryVO.getLevel();
        String joinDateBegin = teacherQueryVO.getJoinDateBegin();
        String joinDateEnd = teacherQueryVO.getJoinDateEnd();
        // ⑴ 判断讲师姓名是否为空, 不为空并且不为空字符串时，封装到查询对象中
        if (!StringUtils.isEmpty(name)) {
            // name字段 + '%'
            // 查询字段左侧也是用%, 会使索引失效
            queryWrapper.likeRight("name", name);
        }
        // ⑵ 讲师头衔 头衔 1高级讲师 2首席讲师
        if (level != null) {
            queryWrapper.eq("level", level);
        }
        // ⑶ 入驻时间（开始范围）
        if (!StringUtils.isEmpty(joinDateBegin)) {
            queryWrapper.ge("join_date", joinDateBegin);
        }
        // ⑷ 入驻时间（结束范围）
        if (!StringUtils.isEmpty(joinDateEnd)) {
            queryWrapper.le("join_date", joinDateEnd);
        }

        // 执行到此处，查询条件已经封装完毕，进行分页查询（带有查询条件）

        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
