
package com.lyc.spark.service.uum.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.service.uum.entity.Tenant;
import java.util.List;

/**
 *  Mapper 接口
 *
 */
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);
}
