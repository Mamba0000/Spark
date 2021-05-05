package com.lyc.spark.service.uum.wrapper;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.system.feign.IDictClient;
import com.lyc.spark.service.uum.entity.Tenant;
import com.lyc.spark.service.uum.service.IUserService;
import com.lyc.spark.service.uum.vo.TenantVO;
/**
 * 包装类,返回视图层所需的字段
 *
 */
public class TenantWrapper extends BaseEntityWrapper<Tenant, TenantVO> {
	private static IUserService userService;
	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static TenantWrapper build() {
		return new TenantWrapper();
	}

	@Override
	public TenantVO entityVO(Tenant tenant) {
		TenantVO tenantVO = BeanUtil.copy(tenant, TenantVO.class);
		CommonResult<String> dictStatus = dictClient.getValue("status", Func.toInt(tenant.getStatus()));
		if (dictStatus.isSuccess()) {
			tenantVO.setStatusName(dictStatus.getData());
		}
		return tenantVO;
	}

}
