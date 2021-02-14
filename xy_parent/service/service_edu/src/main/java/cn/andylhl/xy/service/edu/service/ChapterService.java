package cn.andylhl.xy.service.edu.service;

import cn.andylhl.xy.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-01-29
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 删除章节信息(该章节下的课时信息)
     * @param id
     * @return
     */
    boolean removeChapterById(String id);
}
