package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Subject;
import cn.andylhl.xy.service.edu.entity.excel.ExcelSubjectData;
import cn.andylhl.xy.service.edu.listener.ExcelSubjectDataListener;
import cn.andylhl.xy.service.edu.mapper.SubjectMapper;
import cn.andylhl.xy.service.edu.service.SubjectService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
@Slf4j
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * Excel批量导入课程分类数据
     * @param inputStream
     */
    @Override
    public void batchImport(InputStream inputStream) {
        // 使用监听器读取数据
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS)
                .sheet()
                .doRead();
    }
}
