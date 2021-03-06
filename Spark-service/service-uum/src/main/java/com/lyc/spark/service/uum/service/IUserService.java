
package com.lyc.spark.service.uum.service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.mybatisplus.base.BaseService;
import com.lyc.spark.service.uum.entity.Permission;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.entity.UserInfo;
import java.util.List;

/**
 * 服务类
 *
 * 
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增或修改用户
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user);

	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param password
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, String password);


	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleName(String roleIds);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptName(String deptIds);


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

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);
}
