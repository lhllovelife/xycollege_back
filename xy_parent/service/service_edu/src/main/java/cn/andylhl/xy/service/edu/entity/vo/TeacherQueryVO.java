package cn.andylhl.xy.service.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/***
 * @Title: TeacherQueryVO
 * @Description: 讲师查询关键词
 * @author: lhl
 * @date: 2021/1/30 19:02
 */

@ApiModel("讲师查询关键词对象")
@Data
public class TeacherQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名", example = "张")
    private String name;

    @ApiModelProperty(value = "讲师头衔", notes = "1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "添加时间（开始日期）", example = "2010-01-01")
    private String joinDateBegin;

    @ApiModelProperty(value = "添加时间（截止日期）", example = "2012-01-01")
    private String joinDateEnd;

}
