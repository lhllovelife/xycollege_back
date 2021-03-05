package cn.andylhl.xy.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/***
 * @Title: CourseCollectVO
 * @Description: 课程收藏信息表单数据
 * @author: lhl
 * @date: 2021/3/5 10:30
 */

@Data
public class CourseCollectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 课程收藏id
    private String id;

    // 会员id
    private String memberId;

    // 课程id
    private String courseId;

    // 课程标题
    private String title;

    // 课程销售价格，设置为0则可免费观看
    private BigDecimal price;

    // 总课时
    private Integer lessonNum;

    // 课程封面图片路径
    private String cover;

    // 讲师姓名
    private String teacherName;

    // 课程收藏时间
    private Date gmtCreate;

}
