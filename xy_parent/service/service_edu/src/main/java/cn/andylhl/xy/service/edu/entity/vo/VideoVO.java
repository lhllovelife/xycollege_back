package cn.andylhl.xy.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/***
 * @Title: VideoVO
 * @Description: 课时信息vo
 * @author: lhl
 * @date: 2021/2/16 16:24
 */

@Data
public class VideoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 课时id
    private String id;
    // 课时标题
    private String title;
    // 是否免费
    private Boolean free;
    // 排序
    private Integer sort;

    // 所属章节id
    private String chapterId;

    // 云端视频资源（视频在阿里云视频点播平台的id）
    private String videoSourceId;

}
