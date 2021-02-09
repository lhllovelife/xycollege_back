package cn.andylhl.xy.service.edu.listener;

import cn.andylhl.xy.service.edu.entity.Subject;
import cn.andylhl.xy.service.edu.entity.excel.ExcelSubjectData;
import cn.andylhl.xy.service.edu.mapper.SubjectMapper;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @Title: ExcelSubjectDataListener
 * @Description: 课程分类监听器
 * @author: lhl
 * @date: 2021/2/9 22:37
 */

@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 所有参数都有的构造函数
@Slf4j
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;

    /**
     * 每解析一条数据后，调用一次
     * @param data
     * @param context
     */
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext context) {
        log.error("解析一条数据, {}", data);
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();
        // 1. 判断该条记录，一级标题是否重复
        Subject levelOneSubject = this.getLevelOneByTitle(levelOneTitle);
        if (levelOneSubject == null) {
            // 准备一级标题数据
            levelOneSubject = new Subject();
            levelOneSubject.setParentId("0");
            levelOneSubject.setTitle(levelOneTitle);
            subjectMapper.insert(levelOneSubject);
        }
        // 2. 判断该条记录，二级标题是否重复
        String parentId = levelOneSubject.getId();
        Subject levelTwoSubject = getLevelTwoByTitle(levelTwoTitle, parentId);
        if (levelTwoSubject == null) {
            // 准备二级标题数据
            levelTwoSubject = new Subject();
            levelTwoSubject.setParentId(parentId);
            levelTwoSubject.setTitle(levelTwoTitle);
            subjectMapper.insert(levelTwoSubject);
        }

    }



    /**
     * 所有数据解析完成过后调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.error("数据解析完成");
    }

    /**
     * 根据标题名称, 判断一级标题是否重复
     * @param levelOneTitle
     * @return
     */
    private Subject getLevelOneByTitle(String levelOneTitle) {
        // 1. 准备查询条件 (SELECT id,title,parent_id,sort,gmt_create,gmt_modified FROM edu_subject WHERE (title = ? AND parent_id = ?) )
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", levelOneTitle);
        queryWrapper.eq("parent_id", "0");
        return subjectMapper.selectOne(queryWrapper);
    }

    /**
     * 根据标题名称和所对应一级标题的id, 判断一级标题是否重复
     * @param levelTwoTitle
     * @param parentId
     * @return
     */
    private Subject getLevelTwoByTitle(String levelTwoTitle, String parentId) {
        // 1. 准备查询条件(SELECT id,title,parent_id,sort,gmt_create,gmt_modified FROM edu_subject WHERE (title = ? AND parent_id = ?) )
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", levelTwoTitle);
        queryWrapper.eq("parent_id", parentId);
        return subjectMapper.selectOne(queryWrapper);
    }
}
