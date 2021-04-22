
package com.lyc.spark.service.uum.wrapper;



import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.system.feign.IDictClient;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.service.IUserService;
import com.lyc.spark.service.uum.vo.UserVO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;

	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoles(user).stream().map(item->item.getRoleAlias()).collect(Collectors.toList());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		CommonResult<String> dict = dictClient.getValue("sex", Func.toInt(user.getSex()));
		if (dict.isSuccess()) {
			userVO.setSexName(dict.getData());
		}
		return userVO;
	}

}
