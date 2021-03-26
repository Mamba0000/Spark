
package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lyc.spark.core.mybatisplus.base.BaseServiceImpl;
import com.lyc.spark.service.uum.entity.Param;
import com.lyc.spark.service.uum.mapper.ParamMapper;
import com.lyc.spark.service.uum.service.IParamService;
import com.lyc.spark.service.uum.vo.ParamVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public IPage<ParamVO> selectParamPage(IPage<ParamVO> page, ParamVO param) {
		return page.setRecords(baseMapper.selectParamPage(page, param));
	}

}
