
package com.lyc.spark.service.uum.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * GrantRoleMenu 角色配置菜单
 *
 * 
 */
@Data
public class GrantRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "roleIds集合")
	private List<Long> roleIds;

	@ApiModelProperty(value = "menuIds集合")
	private List<Long> menuIds;

}
