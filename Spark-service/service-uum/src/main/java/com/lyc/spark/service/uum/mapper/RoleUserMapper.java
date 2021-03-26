package com.lyc.spark.service.uum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户和角色关系表 Mapper 接口
 * </p>
 */
@Mapper
public interface RoleUserMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户角色
     * @param user
     * @return
     */
    List<Role> getRoles(User user);

    /**
     * 获取用户权限
     * @param user
     * @return
     */
    List<Permission> getPermissions(User user);



}
