package cn.andylhl.xy.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * @Title: WebCourseVO
 * @Description: 课程详情VO
 * @author: lhl
 * @date: 2021/2/22 15:55
 */
@Data
public class WebCourseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 课程id
    private String id;

    // 课程标题
    private String title;

    // 价格
    private BigDecimal price;

    // 课时数量
    private Integer lessonNum;

    // 课程封面
    private String cover;

    // 购买数量
    private Long buyCount;

    // 浏览量
    private Long viewCount;

    // 课程描述
    private String description;

    // 讲师id
    private String teacherId;

    // 讲师姓名
    private String teacherName;

    // 讲师简介
    private String intro;

    // 讲师头像地址
    private String avatar;

    // 一级分类id
    private String subjectLevelOneId;

    // 一级分类标题
    private String subjectLevelOne;

    // 二级分类id
    private String subjectLevelTwoId;

    // 二级分类标题
    private String subjectLevelTwo;
}
