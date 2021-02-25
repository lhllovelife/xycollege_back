package cn.andylhl.xy.service.cms.service.impl;

import cn.andylhl.xy.common.base.result.R;
import cn.andylhl.xy.service.cms.entity.Ad;
import cn.andylhl.xy.service.cms.entity.vo.AdVO;
import cn.andylhl.xy.service.cms.feign.OssFileRemoteService;
import cn.andylhl.xy.service.cms.mapper.AdMapper;
import cn.andylhl.xy.service.cms.service.AdService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author lhl
 * @since 2021-02-24
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {


    @Autowired
    private OssFileRemoteService ossFileRemoteService;

    /**
     * 执行分页, 返回分页对象信息
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<AdVO> selectPage(Long page, Long limit) {

        // 1. 准备分页对象
        Page<AdVO> pageInfo = new Page<>(page, limit);

        // 2. 准备分页条件
        QueryWrapper<AdVO> queryWrapper = new QueryWrapper<>();
        // 按照排序（类别，sort字段）
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        // 执行查询，Mybatis-Plus会自动组装分页参数
        List<AdVO> adVOList = baseMapper.selecPageAdVO(pageInfo, queryWrapper);

        // Mybatis-Plus同时会自动查询当前分页下的总记录条数
        return pageInfo.setRecords(adVOList);
    }

    /**
     * 删除广告图片
     * @param id
     */
    @Override
    public boolean removeAdImageById(String id) {

        Ad ad = baseMapper.selectById(id);

        if (ad != null) {
            String imageUrl = ad.getImageUrl();
            if (!StringUtils.isEmpty(imageUrl)) {
                // 调用oss服务提供方法删除图片
                R r = ossFileRemoteService.removeFile(imageUrl);
                return r.getSuccess();
            }
        }

        return false;
    }

    /**
     * 根据推荐位id查询广告推荐
     * @param adTypeId
     * @return
     */
    @Override
    public List<Ad> getAdListByAdTypeId(String adTypeId) {

        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", adTypeId);
        // 排序，先按照sort字段，再按照id字段
        queryWrapper.orderByAsc("sort", "id");
        return baseMapper.selectList(queryWrapper);
    }
}
