package cn.andylhl.xy.service.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/***
 * @Title: CourseQueryVO
 * @Description: 课程相关查询关键词
 * @author: lhl
 * @date: 2021/2/12 22:34
 */

@ApiModel("课程查询关键词对象")
@Data
public class CourseQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("讲师id")
    private String teacherId;

    @ApiModelProperty("一级分类id")
    private String subjectParentId;

    @ApiModelProperty("二级分类id")
    private String subjectId;

}
