
package com.lyc.spark.service.uum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.mybatisplus.base.BaseService;
import com.lyc.spark.service.uum.entity.Param;
import com.lyc.spark.service.uum.vo.ParamVO;


/**
 * 服务类
 *
 * 
 */
public interface IParamService extends BaseService<Param> {

	/***
	 * 自定义分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param);

}
