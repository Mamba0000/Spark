
package com.lyc.spark.service.system.controller;

import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.system.entiy.Dict;
import com.lyc.spark.service.system.service.IDictService;
import com.lyc.spark.service.system.vo.DictVO;
import com.lyc.spark.service.system.wrapper.DictWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;
import java.util.Map;
import static com.lyc.spark.core.common.constant.CacheNames.DICT_LIST;
import static com.lyc.spark.core.common.constant.CacheNames.DICT_VALUE;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController {

	private IDictService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入dict")
	public CommonResult<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return CommonResult.data(DictWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})

	@ApiOperation(value = "列表", notes = "传入dict")
	public CommonResult<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		@SuppressWarnings("unchecked")
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		return CommonResult.data(DictWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取字典树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public CommonResult<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return CommonResult.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	public CommonResult submit(@RequestBody Dict dict) {
		return CommonResult.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	@ApiOperation(value = "删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(dictService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 获取字典
	 *
	 * @return
	 */
	@GetMapping("/dictionary")
	@ApiOperation(value = "获取字典", notes = "获取字典")
	public CommonResult<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return CommonResult.data(tree);
	}

}
