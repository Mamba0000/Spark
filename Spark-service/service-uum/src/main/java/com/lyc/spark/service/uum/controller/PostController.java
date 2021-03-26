
package com.lyc.spark.service.uum.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyc.spark.core.auth.util.BladeUser;
import com.lyc.spark.core.auth.util.SecureUtil;
import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.support.Condition;
import com.lyc.spark.core.mybatisplus.support.Query;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.Post;
import com.lyc.spark.service.uum.service.IPostService;
import com.lyc.spark.service.uum.vo.PostVO;
import com.lyc.spark.service.uum.wrapper.PostWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * 岗位表 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/post")
@Api(value = "岗位表", tags = "岗位表接口")
public class PostController {

	private IPostService postService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "详情", notes = "传入post")
	public CommonResult<PostVO> detail(Post post) {
		Post detail = postService.getOne(Condition.getQueryWrapper(post));
		return CommonResult.data(PostWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 岗位表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "分页", notes = "传入post")
	public CommonResult<IPage<PostVO>> list(Post post, Query query) {
		IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
		return CommonResult.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * 自定义分页 岗位表
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "传入post")
	public CommonResult<IPage<PostVO>> page(PostVO post, Query query) {
		IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
		return CommonResult.data(pages);
	}

	/**
	 * 新增 岗位表
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增", notes = "传入post")
	public CommonResult save(@Valid @RequestBody Post post) {
		return CommonResult.status(postService.save(post));
	}

	/**
	 * 修改 岗位表
	 */
	@PostMapping("/update")
	@ApiOperation(value = "修改", notes = "传入post")
	public CommonResult update(@Valid @RequestBody Post post) {
		return CommonResult.status(postService.updateById(post));
	}

	/**
	 * 新增或修改 岗位表
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "新增或修改", notes = "传入post")
	public CommonResult submit(@Valid @RequestBody Post post) {
		post.setTenantId(SecureUtil.getTenantId());
		return CommonResult.status(postService.saveOrUpdate(post));
	}


	/**
	 * 删除 岗位表
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public CommonResult remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return CommonResult.status(postService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperation(value = "下拉数据源", notes = "传入post")
	public CommonResult<List<Post>> select(String tenantId, BladeUser bladeUser) {
		List<Post> list = postService.list(Wrappers.<Post>query().lambda().eq(Post::getTenantId, Func.toStr(tenantId, bladeUser.getTenantId())));
		return CommonResult.data(list);
	}

}
