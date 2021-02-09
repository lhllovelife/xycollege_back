package cn.andylhl.xy.service.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/***
 * @Title: ExcelSubjectData
 * @Description: 课程分类excel实体类
 * @author: lhl
 * @date: 2021/2/9 22:34
 */

@Data
public class ExcelSubjectData {

    @ExcelProperty("一级分类")
    private String levelOneTitle;

    @ExcelProperty("二级分类")
    private String levelTwoTitle;

}
