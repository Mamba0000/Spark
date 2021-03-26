
package com.lyc.spark.service.uum.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.node.INode;
import com.lyc.spark.service.uum.entity.Region;
import com.lyc.spark.service.uum.service.IRegionService;
import com.lyc.spark.service.uum.vo.RegionVO;
import com.lyc.spark.service.uum.wrapper.RegionWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/region")
@Api(value = "行政区划表", tags = "行政区划表接口")
public class RegionController  {

	private final IRegionService regionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入region")
	public CommonResult<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return CommonResult.data(RegionWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 行政区划表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入region")
	public CommonResult<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return CommonResult.data(pages);
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public CommonResult<List<INode>> lazyList(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<INode> list = regionService.lazyList(parentCode, menu);
		return CommonResult.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-tree")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "区划编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "name", value = "区划名称", paramType = "query", dataType = "string")
	})
	@ApiOperation(value = "懒加载列表", notes = "传入menu")
	public CommonResult<List<INode>> lazyTree(String parentCode, @ApiIgnore @RequestParam Map<String, Object> menu) {
		List<INode> list = regionService.lazyTree(parentCode, menu);
		return CommonResult.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 新增 行政区划表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入region")
	public CommonResult save(@Valid @RequestBody Region region) {
		return CommonResult.status(regionService.save(region));
	}

	/**
	 * 修改 行政区划表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入region")
	public CommonResult update(@Valid @RequestBody Region region) {
		return CommonResult.status(regionService.updateById(region));
	}

	/**
	 * 新增或修改 行政区划表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入region")
	public CommonResult submit(@Valid @RequestBody Region region) {
		return CommonResult.status(regionService.submit(region));
	}


	/**
	 * 删除 行政区划表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "删除", notes = "传入主键")
	public CommonResult remove(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		return CommonResult.status(regionService.removeRegion(id));
	}

	/**
	 * 行政区划下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperation(value = "下拉数据源", notes = "传入tenant")
	public CommonResult<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return CommonResult.data(list);
	}


}
