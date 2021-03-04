package cn.andylhl.xy.service.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/***
 * @Title: CourseDTO
 * @Description: 课程信息DTO(服务之间数据传输)
 * @author: lhl
 * @date: 2021/3/4 9:56
 */

@Data
public class CourseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;//课程ID

    private String title;//课程标题

    private BigDecimal price;//课程销售价格，设置为0则可免费观看

    private String cover;//课程封面图片路径

    private String teacherName;//课程讲师

}
