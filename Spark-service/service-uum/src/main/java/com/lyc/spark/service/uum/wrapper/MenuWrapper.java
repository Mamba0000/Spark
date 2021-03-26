
package com.lyc.spark.service.uum.wrapper;

import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.node.ForestNodeMerger;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.system.feign.IDictClient;
import com.lyc.spark.service.uum.entity.Menu;
import com.lyc.spark.service.uum.service.IMenuService;
import com.lyc.spark.service.uum.vo.MenuVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;

	private static IDictClient dictClient;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = menuService.getById(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		CommonResult<String> d1 = dictClient.getValue("menu_category", Func.toInt(menuVO.getCategory()));
		CommonResult<String> d2 = dictClient.getValue("button_func", Func.toInt(menuVO.getAction()));
		CommonResult<String> d3 = dictClient.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
		if (CommonResult.isSuccess(d1)) {
			menuVO.setCategoryName(d1.getData());
		}
		if (CommonResult.isSuccess(d2)) {
			menuVO.setActionName(d2.getData());
		}
		if (CommonResult.isSuccess(d3)) {
			menuVO.setIsOpenName(d3.getData());
		}
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
