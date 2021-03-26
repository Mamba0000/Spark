
package com.lyc.spark.service.uum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.service.uum.entity.Region;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 Mapper 接口
 *
 * @author Chill
 */
public interface RegionMapper extends BaseMapper<Region> {

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<INode> lazyList(String parentCode, Map<String, Object> param);

	/**
	 * 懒加载列表
	 *
	 * @param parentCode
	 * @param param
	 * @return
	 */
	List<INode> lazyTree(String parentCode, Map<String, Object> param);

}
