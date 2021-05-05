
package com.lyc.spark.service.uum.wrapper;

import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.service.uum.entity.Region;
import com.lyc.spark.service.uum.service.IRegionService;
import com.lyc.spark.service.uum.vo.RegionVO;
import com.lyc.spark.core.tool.SpringUtil;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 * 
 */
public class RegionWrapper extends BaseEntityWrapper<Region, RegionVO> {

	private static IRegionService regionService;

	static {
		regionService = SpringUtil.getBean(IRegionService.class);
	}

	public static RegionWrapper build() {
		return new RegionWrapper();
	}

	@Override
	public RegionVO entityVO(Region region) {
		RegionVO regionVO = Objects.requireNonNull(BeanUtil.copy(region, RegionVO.class));
		Region parentRegion = regionService.getById(region.getParentCode());
		regionVO.setParentName(parentRegion.getName());
		return regionVO;
	}

	public List<INode> listNodeLazyVO(List<INode> list) {
		return ForestNodeMerger.merge(list);
	}

}
