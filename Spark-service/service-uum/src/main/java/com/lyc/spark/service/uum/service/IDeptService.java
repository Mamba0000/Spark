
package com.lyc.spark.service.uum.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.Dept;
import com.lyc.spark.service.uum.vo.DeptVO;

import java.util.List;

/**
 * 服务类
 *
 * 
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dept
	 * @return
	 */
	IPage<DeptVO> selectDeptPage(IPage<DeptVO> page, DeptVO dept);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

}
