
package com.lyc.spark.service.system.feign;



import com.lyc.spark.common.constant.ApplicationConstant;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.system.entiy.Dict;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 */
@FeignClient(
	value = ApplicationConstant.APPLICATION_SPARK_SERVICE_SYSTEM,
	fallback = IDictClientFallback.class
)
public interface IDictClient {

	String API_PREFIX = "/dict";

	/**
	 * 获取字典表对应值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getValue")
	CommonResult<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") Integer dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	@GetMapping(API_PREFIX + "/getList")
	CommonResult<List<Dict>> getList(@RequestParam("code") String code);

}
