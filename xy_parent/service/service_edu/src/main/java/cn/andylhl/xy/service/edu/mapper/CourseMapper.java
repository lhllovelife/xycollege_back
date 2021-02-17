package cn.andylhl.xy.service.edu.mapper;

import cn.andylhl.xy.service.edu.entity.Course;
import cn.andylhl.xy.service.edu.entity.vo.CoursePublishVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseQueryVO;
import cn.andylhl.xy.service.edu.entity.vo.CourseVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 分页查询（关键词可选）
     * @param pageInfo
     * @param queryWrapper
     * @return
     */
    List<CourseVO> selectPageByCourseQueryVO(
            // mp会自动组装分页参数
            Page<CourseVO> pageInfo,
            // mp会自动组装queryWrapper
            // @Param(Constants.WRAPPER) 和 xml文件中的 ${ew.customSqlSegment} 对应
            @Param(Constants.WRAPPER) QueryWrapper<CourseQueryVO> queryWrapper);

    /**
     * 获取课程发布基本信息
     * @param id
     * @return
     */
    CoursePublishVO selectCoursePublishInfo(String id);
}
