
package com.lyc.spark.service.uum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyc.spark.core.mybatisplus.entity.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表实体类
 *
 */
@Data
@TableName("spark_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Post对象", description = "岗位表")
public class Post extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer category;
	/**
	 * 岗位编号
	 */
	@ApiModelProperty(value = "岗位编号")
	private String postCode;
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "岗位名称")
	private String postName;
	/**
	 * 岗位排序
	 */
	@ApiModelProperty(value = "岗位排序")
	private Integer sort;
	/**
	 * 岗位描述
	 */
	@ApiModelProperty(value = "岗位描述")
	private String remark;


}
