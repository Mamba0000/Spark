
package com.lyc.spark.service.uum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.service.uum.entity.Param;
import com.lyc.spark.service.uum.vo.ParamVO;


import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface ParamMapper extends BaseMapper<Param> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	List<ParamVO> selectParamPage(IPage page, ParamVO param);

}
