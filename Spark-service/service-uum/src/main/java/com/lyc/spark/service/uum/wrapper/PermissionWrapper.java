
package com.lyc.spark.service.uum.wrapper;

import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.system.feign.IDictClient;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.service.IMenuService;
import com.lyc.spark.service.uum.vo.MenuVO;
import com.lyc.spark.service.uum.vo.PermissionVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * 
 */
public class PermissionWrapper extends BaseEntityWrapper<Permission, PermissionVO> {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static PermissionWrapper build() {
		return new PermissionWrapper();
	}

	@Override
	public PermissionVO entityVO(Permission permission) {
		PermissionVO permissionVO = BeanUtil.copy(permission, PermissionVO.class);
//		CommonResult<String> d3 = dictClient.getValue("yes_no", Func.toInt(permissionVO.getIsOpen()));
//		if (CommonResult.isSuccess(d3)) {
//			permissionVO.setIsOpenName(d3.getData());
//		}
		return permissionVO;
	}


	public List<PermissionVO> listNodeVO(List<Permission> list) {
		List<PermissionVO> collect = list.stream().map(permission -> BeanUtil.copy(permission, PermissionVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
