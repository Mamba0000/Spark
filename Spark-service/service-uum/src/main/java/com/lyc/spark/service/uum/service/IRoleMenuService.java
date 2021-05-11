
package com.lyc.spark.service.uum.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.spark.service.uum.entity.RoleMenu;

import java.util.List;

/**
 * 服务类
 *
 * 
 */
public interface IRoleMenuService extends IService<RoleMenu> {


    public List<RoleMenu> listByRoleIds(List<Long> roleIds);

}
