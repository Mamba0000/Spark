package com.lyc.spark.service.uum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_role_menu_relation")
@ApiModel(value = "RoleMenuRelation对象", description = "角色菜单关系表")
public class RoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
