
package com.lyc.spark.service.system.feign;

import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.service.system.entiy.Dict;
import org.springframework.stereotype.Component;
import java.nio.charset.CoderResult;
import java.util.List;

/**
 * Feign失败配置
 *
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public CommonResult<String> getValue(String code, Integer dictKey) {
		return CommonResult.fail("获取数据失败");
	}

	@Override
	public CommonResult<List<Dict>> getList(String code) {
		return CommonResult.fail("获取数据失败");
	}
}
