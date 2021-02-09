package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

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
}
