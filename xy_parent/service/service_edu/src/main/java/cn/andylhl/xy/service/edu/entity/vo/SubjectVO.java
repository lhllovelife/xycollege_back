package cn.andylhl.xy.service.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * @Title: SubjectVO
 * @Description: 用于展示课程列表
 * @author: lhl
 * @date: 2021/2/10 9:53
 */

@Data
public class SubjectVO {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "孩子结点")
    private List<SubjectVO> children = new ArrayList<>();

}
