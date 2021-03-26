
package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.mybatisplus.base.BaseService;
import com.lyc.spark.service.uum.entity.Post;
import com.lyc.spark.service.uum.vo.PostVO;

import java.util.List;

/**
 * 岗位表 服务类
 *
 * @author Chill
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param post
	 * @return
	 */
	IPage<PostVO> selectPostPage(IPage<PostVO> page, PostVO post);

	/**
	 * 获取岗位ID
	 *
	 * @param tenantId
	 * @param postNames
	 * @return
	 */
	String getPostIds(String tenantId, String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param postIds
	 * @return
	 */
	List<String> getPostNames(String postIds);

}
