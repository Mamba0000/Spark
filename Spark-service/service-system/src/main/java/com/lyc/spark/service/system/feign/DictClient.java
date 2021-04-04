
package com.lyc.spark.service.system.feign;


import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.system.entiy.Dict;
import com.lyc.spark.service.system.service.IDictService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	IDictService service;

	@Override
	@GetMapping(API_PREFIX + "/getValue")
	public CommonResult<String> getValue(String code, Integer dictKey) {
		return CommonResult.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(API_PREFIX + "/getList")
	public CommonResult<List<Dict>> getList(String code) {
		return CommonResult.data(service.getList(code));
	}

}
