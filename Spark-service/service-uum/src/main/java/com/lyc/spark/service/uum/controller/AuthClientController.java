package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.AuthClient;
import com.lyc.spark.service.uum.service.IAuthClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

/**
 *  应用管理控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "应用管理", tags = "应用管理")
public class AuthClientController  {

	private IAuthClientService clientService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入client")
	public CommonResult<AuthClient> detail(AuthClient authClient) {
		AuthClient detail = clientService.getOne(Condition.getQueryWrapper(authClient));
		return CommonResult.data(detail);
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入client")
	public CommonResult<IPage<AuthClient>> list(AuthClient authClient, Query query) {
		IPage<AuthClient> pages = clientService.page(Condition.getPage(query), Condition.getQueryWrapper(authClient));
		return CommonResult.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入client")
	public CommonResult save(@Valid @RequestBody AuthClient authClient) {
		return CommonResult.status(clientService.save(authClient));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入client")
	public CommonResult update(@Valid @RequestBody AuthClient authClient) {
		return CommonResult.status(clientService.updateById(authClient));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/saveOrUpdate")
	@ApiOperation(value = "新增或修改", notes = "传入client")
	public CommonResult submit(@Valid @RequestBody AuthClient authClient) {
		return CommonResult.status(clientService.saveOrUpdate(authClient));
	}


	/**
	* 删除
	*/
	@PostMapping("/deleteLogic")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(clientService.deleteLogic(Func.toLongList(ids)));
	}

}
