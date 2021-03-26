package com.lyc.spark.service.uum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * <p>
 * 用户和角色关系表
 * </p>
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("spark_user_role")
@ApiModel(value = "UserRoleRelation对象", description = "用户和角色关系表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户ID
    private Long userId;

    // 角色ID
    private Long roleId;


}
