package cn.andylhl.xy.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * @Title: ChapterVO
 * @Description: 章节信息VO
 * @author: lhl
 * @date: 2021/2/16 16:27
 */

@Data
public class ChapterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 章节id
    private String id;

    // 章节标题
    private String title;

    // 排序
    private Integer sort;

    // 该章节下的所有课时信息列表
    private List<VideoVO> children = new ArrayList<>();



}
