
package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.auth.util.TokenUser;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.support.Kv;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Tenant;
import com.lyc.spark.service.uum.service.ITenantService;
import com.lyc.spark.service.uum.vo.TenantVO;
import com.lyc.spark.service.uum.wrapper.TenantWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * 
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "租户管理", tags = "租户管理")
public class TenantController  {
	private ITenantService tenantService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入tenant")
	public CommonResult<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return CommonResult.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tenantId", value = "租户ID", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tenantName", value = "租户名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "contactNumber", value = "联系电话", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "分页", notes = "传入tenant")
	public CommonResult<IPage<TenantVO>> list(@RequestParam Map<String, Object> tenant, Query query) {
		// 如果是超级用户可以查询全部 否则只能查询自己
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		TokenUser tokenUser = SecureUtil.getUser();
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query), (!tokenUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, tokenUser.getTenantId()) : queryWrapper);
		return CommonResult.data(TenantWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/addOrUpdate")
	@ApiOperation(value = "新增或修改", notes = "传入tenant")
	public CommonResult addOrUpdate(@Valid @RequestBody Tenant tenant) {
		return CommonResult.status(tenantService.saveTenant(tenant));
	}

	/**
	 * 删除
	 */
	@PostMapping("/deleteLogic")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(tenantService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 根据域名查询信息
	 *
	 * @param domain 域名
	 */
	@GetMapping("/info")
	@ApiOperation(value = "配置信息", notes = "传入domain")
	public CommonResult<Kv> info(String domain) {
		Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getDomain, domain));
		Kv kv = Kv.init();
		if (tenant != null) {
			kv.set("tenantId", tenant.getTenantId()).set("domain", tenant.getDomain());
		}
		return CommonResult.data(kv);
	}

}
