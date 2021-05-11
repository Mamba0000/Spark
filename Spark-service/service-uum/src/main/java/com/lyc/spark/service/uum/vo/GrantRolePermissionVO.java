package com.lyc.spark.service.uum.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 *
 * GrantRolePermission 角色配置权限
 *
 */
@Data
public class GrantRolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "roleIds集合")
	private List<Long> roleIds;

	@ApiModelProperty(value = "permissionIds集合")
	private List<Long> permissionIds;

}
