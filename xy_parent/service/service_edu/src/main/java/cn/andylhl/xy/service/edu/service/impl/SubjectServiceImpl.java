package cn.andylhl.xy.service.edu.service.impl;

import cn.andylhl.xy.service.edu.entity.Subject;
import cn.andylhl.xy.service.edu.entity.excel.ExcelSubjectData;
import cn.andylhl.xy.service.edu.entity.vo.SubjectVO;
import cn.andylhl.xy.service.edu.listener.ExcelSubjectDataListener;
import cn.andylhl.xy.service.edu.mapper.SubjectMapper;
import cn.andylhl.xy.service.edu.service.SubjectService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.xml.soap.SAAJResult;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取课程分类的嵌套数据列表
     * @return
     */
    @Override
    public List<SubjectVO> getSubjectNestedList() {
        // 1. 一次性查询所有课程分类数据, 返回对象为SubjectVO
        List<SubjectVO> subjectVOList = new ArrayList<>();
        List<Subject> subjectList = baseMapper.selectList(null);
        for (Subject subject : subjectList) {
            SubjectVO subjectVO = new SubjectVO();
            BeanUtils.copyProperties(subject, subjectVO);
            subjectVOList.add(subjectVO);
        }

        // 2. 组装结点数据
        // 构建一个map集合，使每个节点对象的地址与该对象的id一一对应
        Map<String, SubjectVO> idSubjectVoRelationMap = new HashMap<>();
        for (SubjectVO subjectVO : subjectVOList) {
            idSubjectVoRelationMap.put(subjectVO.getId(), subjectVO);
        }

        // 根节点
        SubjectVO root = new SubjectVO();
        // 3. 为每个结点进行父子匹配
        for (SubjectVO subjectVO : subjectVOList) {
            if ("0".equals(subjectVO.getParentId())) {
                root.getChildren().add(subjectVO);
            } else {
                String parentId = subjectVO.getParentId();
                SubjectVO parentSubjectVO = idSubjectVoRelationMap.get(parentId);
                parentSubjectVO.getChildren().add(subjectVO);
            }
        }

        return root.getChildren();
    }
}
