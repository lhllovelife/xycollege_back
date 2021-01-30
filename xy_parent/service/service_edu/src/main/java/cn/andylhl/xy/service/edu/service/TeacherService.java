package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Teacher;
import cn.andylhl.xy.service.edu.entity.vo.TeacherQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 执行分页, 返回分页对象信息
     * @param page
     * @param limit
     * @param teacherQueryVO
     * @return
     */
    Page<Teacher> selectPage(Long page, Long limit, TeacherQueryVO teacherQueryVO);
}
