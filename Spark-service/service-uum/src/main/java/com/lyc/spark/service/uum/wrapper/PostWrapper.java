
package com.lyc.spark.service.uum.wrapper;

import com.lyc.spark.core.mybatisplus.support.BaseEntityWrapper;
import com.lyc.spark.core.tool.BeanUtil;
import com.lyc.spark.core.tool.SpringUtil;
import com.lyc.spark.service.system.feign.IDictClient;
import com.lyc.spark.service.uum.entity.Post;
import com.lyc.spark.service.uum.vo.PostVO;
import java.util.Objects;

/**
 * 岗位表包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class PostWrapper extends BaseEntityWrapper<Post, PostVO> {

	private static IDictClient dictClient;

	static {
		// 自己从容器中获取
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static PostWrapper build() {
		return new PostWrapper();
	}

	@Override
	public PostVO entityVO(Post post) {
		PostVO postVO = Objects.requireNonNull(BeanUtil.copy(post, PostVO.class));
//		String categoryName = dictClient.getValue("post_category", post.getCategory());
//		postVO.setCategoryName(categoryName);
		return postVO;
	}

}
