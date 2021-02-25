package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.entity.vo.TeacherQueryVO;
import cn.andylhl.xy.service.edu.feign.OssFileRemoteService;
import cn.andylhl.xy.service.edu.mapper.CourseMapper;
import cn.andylhl.xy.service.edu.mapper.TeacherMapper;
import cn.andylhl.xy.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private OssFileRemoteService ossFileRemoteService;

    @Autowired
    private CourseMapper courseMapper;

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

        // 补充：排序，按照sort字段排序
        queryWrapper.orderByAsc("sort");

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

    /**
     * 根据左关键词查询讲师名字列表
     * @param key
     * @return
     */
    @Override
    public List<Map<String, Object>> getNameListByKey(String key) {
        // 1. 封装查询条件(SELECT name FROM edu_teacher WHERE is_deleted=0 AND (name LIKE ?))
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.
                select("name").
                likeRight("name", key);
        // 2. 执行查询，每条查询结果是一个map集合
        List<Map<String, Object>> nameList = baseMapper.selectMaps(queryWrapper);

        return nameList;
    }

    /**
     * 根据id删除讲师头像
     * @param id
     */
    @Override
    public Boolean removeAvatarById(String id) {
        // 1. 根据id查询要删除讲师信息
        Teacher teacher = baseMapper.selectById(id);
        if (teacher != null) {
            String url = teacher.getAvatar();
            if (!StringUtils.isEmpty(url)) {
                R r = ossFileRemoteService.removeFile(url);
                // 将远程调用结果正确是否进行返回
                return r.getSuccess();
            }
        }
        // 不满足条件则返回false
        return false;
    }

    /**
     * 根据id获取讲师及其主讲课程信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getTeacherInfoandCourseInfoById(String id) {

        Map<String, Object> map = new HashMap<>();
        // 获取讲师信息
        Teacher teacher = baseMapper.selectById(id);
        // 获取该讲师主讲的课程信息集合
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        List<Course> courseList = courseMapper.selectList(queryWrapper);

        map.put("teacher", teacher);
        map.put("courseList", courseList);

        return map;
    }

    /**
     * 获取首页4个热门讲师（sort字段排序的前4个）
     * @return
     */
    @Override
    public List<Teacher> getHotTeacherList() {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.last("limit 0, 4");

        return baseMapper.selectList(queryWrapper);
    }
}
