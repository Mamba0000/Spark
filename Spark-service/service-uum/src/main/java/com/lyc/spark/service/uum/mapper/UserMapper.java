
package com.lyc.spark.service.uum.mapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Mapper 接口
 *
 * 
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	List<User> selectUserPage(IPage page, User user);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	User getUser(String tenantId, String account, String password);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleName(String[] ids);

	/**
	 * 获取角色别名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAlias(String[] ids);

	/**
	 * 获取用户角色
	 * @param user
	 * @return
	 */
	List<Role> getRoles(User user);




	/**
//	 * 获取用户权限
//	 * @param user
//	 * @return
//	 */
	List<Permission> getPermissionsByRoleIds(Long[] ids);


	/**
	 * 获取部门名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getDeptName(String[] ids);



}
