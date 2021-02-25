package cn.andylhl.xy.service.cms.service;

import cn.andylhl.xy.service.cms.entity.Ad;
import cn.andylhl.xy.service.cms.entity.vo.AdVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author lhl
 * @since 2021-02-24
 */
public interface AdService extends IService<Ad> {

    /**
     * 执行分页, 返回分页对象信息
     * @param page
     * @param limit
     * @return
     */
    Page<AdVO> selectPage(Long page, Long limit);

    /**
     * 删除广告图片
     * @param id
     */
    boolean removeAdImageById(String id);
}
