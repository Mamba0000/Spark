
package com.lyc.spark.service.uum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.service.uum.dto.MenuDTO;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.vo.MenuVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * 
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	List<MenuVO> selectMenuPage(IPage page, MenuVO menu);

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantTree();

	/**
	 * 授权树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantTreeByRole(List<Long> roleId);

	/**
	 * 所有菜单
	 *
	 * @return
	 */
	List<Menu> allMenu();

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> roleMenu(List<Long> roleId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> routes(List<Long> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> buttons(List<Long> roleId);

	/**
	 * 获取配置的角色权限
	 *
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);
}
