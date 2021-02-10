package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Subject;
import cn.andylhl.xy.service.edu.entity.vo.SubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface SubjectService extends IService<Subject> {

    /**
     * Excel批量导入课程分类数据
     * @param inputStream
     */
    void batchImport(InputStream inputStream);

    /**
     * 获取课程分类的嵌套数据列表
     * @return
     */
    List<SubjectVO> getSubjectNestedList();
}
