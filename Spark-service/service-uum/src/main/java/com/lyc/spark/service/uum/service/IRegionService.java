
package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.service.uum.entity.Region;

import java.util.List;
import java.util.Map;

/**
 * 行政区划表 服务类
 *
 * 
 */
public interface IRegionService extends IService<Region> {

	/**
	 * 提交
	 *
	 * @param region
	 * @return
	 */
	boolean submit(Region region);

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	boolean removeRegion(String id);

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
