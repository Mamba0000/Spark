
package com.lyc.spark.service.uum.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.entity.Permission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PermissionVO对象", description = "PermissionVOO对象")
public class PermissionVO extends Permission implements INode {
	private static final long serialVersionUID = 1L;

//	/**
//	 * 主键ID
//	 */
//	@JsonSerialize(using = ToStringSerializer.class)
//	private Long id;
//
//	/**
//	 * 父节点ID
//	 */
//	@JsonSerialize(using = ToStringSerializer.class)
//	private Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	/**
	 * 是否选中
	 */
	private boolean isCheck;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	@Override
	public Boolean getHasChildren() {
		return !this.children.isEmpty();
	}

}
