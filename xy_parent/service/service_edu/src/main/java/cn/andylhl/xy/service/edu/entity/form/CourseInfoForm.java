package cn.andylhl.xy.service.edu.entity.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * @Title: CourseInfoForm
 * @Description: 表单基本信息
 * @author: lhl
 * @date: 2021/2/10 21:08
 */

@ApiModel("课程基本信息")
@Data
public class CourseInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    // 课程状态 Draft未发布  Normal已发布
//    public static final String COURSE_DRAFT = "Draft";
//    public static final String COURSE_NORMAL = "Normal";

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;


    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;


    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;


    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;


    @ApiModelProperty(value = "课程简介")
    private String description;

}
