package cn.andylhl.xy.service.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/***
 * @Title: CourseVO
 * @Description: 课程信息对象(分页展示)
 * @author: lhl
 * @date: 2021/2/12 22:41
 */

@ApiModel("课程信息对象-分页展示")
@Data
public class CourseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程id")
    private String id;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("一级分类标题")
    private String subjectParentTitle;

    @ApiModelProperty("二级分类标题")
    private String subjectTitle;

    @ApiModelProperty("讲师姓名")
    private String teacherName;

    @ApiModelProperty("总课时")
    private Integer lessonNum;

    @ApiModelProperty("课程销售价格，设置为0则可免费观看")
    private String price;

    @ApiModelProperty("课程封面url地址")
    private String cover;

    @ApiModelProperty("销售数量")
    private Long buyCount;

    @ApiModelProperty("浏览数量")
    private Long viewCount;

    @ApiModelProperty("课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty("创建时间")
    private String gmtCreate;

}
