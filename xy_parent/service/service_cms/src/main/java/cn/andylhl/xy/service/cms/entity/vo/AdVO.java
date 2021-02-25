package cn.andylhl.xy.service.cms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: AdVO
 * @Description: 广告推荐对象（用于后台前端展示）
 * @author: lhl
 * @date: 2021/2/24 22:47
 */
@Data
public class AdVO implements Serializable {

    private static final long serialVersionUID=1L;

    // 主键id
    private String id;

    // 标题
    private String title;

    // 排序
    private Integer sort;

    // 类型
    private String type;

}