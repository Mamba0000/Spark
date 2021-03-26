
package com.lyc.spark.service.uum.wrapper;

import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.service.IRoleService;
import com.lyc.spark.service.uum.vo.RoleVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	private static IRoleService roleService;

	static {
		roleService = SpringUtil.getBean(IRoleService.class);
	}

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		if (Func.equals(role.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Role parent = roleService.getById(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		return roleVO;
	}

	public List<INode> listNodeVO(List<Role> list) {
		List<INode> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
