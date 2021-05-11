
package com.lyc.spark.service.uum.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.service.uum.entity.Menu;
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
@ApiModel(value = "MenuVO对象", description = "MenuVO对象")
public class MenuVO extends Menu implements INode {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

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

	/**
	 * 上级菜单
	 */
	private String parentName;

	/**
	 * 菜单类型
	 */
	private String categoryName;

	/**
	 * 按钮功能
	 */
	private String actionName;

	/**
	 * 是否新窗口打开
	 */
	private String isOpenName;


	public static int compare(MenuVO o1, MenuVO o2) {
		return o1.getSort() - o2.getSort();
	}
}
