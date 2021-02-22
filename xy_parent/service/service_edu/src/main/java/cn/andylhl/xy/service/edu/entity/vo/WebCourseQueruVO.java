package cn.andylhl.xy.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: WebCourseQueruVO
 * @Description: 网站端课程查询条件
 * @author: lhl
 * @date: 2021/2/21 17:19
 */

@Data
public class WebCourseQueruVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer PRICE_SORT_DESC = 101;
    public static final Integer PRICE_SORT_ASC = 102;

    // 一级分类id
    private String subjectParentId;

    // 二级分类id
    private String subjectId;

    // 购买量
    private String buyCountSort;

    // 创建时间
    private String gmtCreateSort;

    // 价格
    private String priceSort;

    // 按照价格升序还是降序
    // 33 降序
    // 133 升序
    private Integer priceSortType;

}
