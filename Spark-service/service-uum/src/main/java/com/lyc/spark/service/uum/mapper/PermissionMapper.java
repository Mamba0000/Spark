package com.lyc.spark.service.uum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.spark.service.uum.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 获取用户所有可访问权限
     */
    List<Permission> getResourceList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取权限
     */
    List<Permission> getResourceListByRoleId(@Param("roleId") Long roleId);

}
