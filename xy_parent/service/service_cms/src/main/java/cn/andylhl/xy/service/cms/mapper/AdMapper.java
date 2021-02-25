package cn.andylhl.xy.service.cms.mapper;

import cn.andylhl.xy.service.cms.entity.Ad;
import cn.andylhl.xy.service.cms.entity.vo.AdVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 广告推荐 Mapper 接口
 * </p>
 *
 * @author lhl
 * @since 2021-02-24
 */
public interface AdMapper extends BaseMapper<Ad> {

    /**
     * @param pageInfo
     * @param queryWrapper
     * @return
     */
    List<AdVO> selecPageAdVO(
            // mp会自动组装分页参数
            Page<AdVO> pageInfo,
            // mp会自动组装queryWrapper
            // @Param(Constants.WRAPPER) 和 xml文件中的 ${ew.customSqlSegment} 对应
            @Param(Constants.WRAPPER) QueryWrapper<AdVO> queryWrapper);
}
