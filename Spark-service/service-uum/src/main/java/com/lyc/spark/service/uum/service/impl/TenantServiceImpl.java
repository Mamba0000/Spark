package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyc.spark.core.common.exception.ServiceException;
import com.lyc.spark.core.constant.BladeConstant;
import com.lyc.spark.core.mybatisplus.base.BaseServiceImpl;
import com.lyc.spark.core.tool.DigestUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.*;
import com.lyc.spark.service.uum.mapper.DeptMapper;
import com.lyc.spark.service.uum.mapper.RoleMapper;
import com.lyc.spark.service.uum.mapper.RoleUserMapper;
import com.lyc.spark.service.uum.mapper.TenantMapper;
import com.lyc.spark.service.uum.service.IPostService;
import com.lyc.spark.service.uum.service.ITenantService;
import com.lyc.spark.service.uum.service.IUserService;
import com.lyc.spark.service.uum.util.TenantIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * 
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<TenantMapper, Tenant> implements ITenantService {
	@Autowired
	public TenantIdGenerator tenantIdGenerator;
	@Autowired
	public  RoleMapper roleMapper;
	@Autowired
	public RoleUserMapper roleUserMapper;

	@Autowired
	public  DeptMapper deptMapper;

	@Autowired
	public  IPostService postService;
	@Autowired
	@Lazy
	public  IUserService userService;

	@Override
	public IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant) {
		return page.setRecords(baseMapper.selectTenantPage(page, tenant));
	}

	@Override
	public Tenant getByTenantId(String tenantId) {
		return getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantId, tenantId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveTenant(Tenant tenant) {
		if (Func.isEmpty(tenant.getId())) {
			List<Tenant> tenants = baseMapper.selectList(Wrappers.<Tenant>query().lambda().eq(Tenant::getIsDeleted, BladeConstant.DB_NOT_DELETED));
			List<String> codes = tenants.stream().map(Tenant::getTenantId).collect(Collectors.toList());
			String tenantId = getTenantId(codes);
			tenant.setTenantId(tenantId);
			// 新建租户对应的默认角色
			Role role = new Role();
			role.setTenantId(tenantId);
			role.setParentId(0L);
			role.setRoleName("管理员");
			role.setRoleAlias("admin");
			role.setSort(2);
			role.setIsDeleted(0);
			roleMapper.insert(role);
			// 新建租户对应的默认部门
			Dept dept = new Dept();
			dept.setTenantId(tenantId);
			dept.setParentId(0L);
			dept.setDeptName(tenant.getTenantName());
			dept.setFullName(tenant.getTenantName());
			dept.setSort(2);
			dept.setIsDeleted(0);
			deptMapper.insert(dept);
			// 新建租户对应的默认岗位
			Post post = new Post();
			post.setTenantId(tenantId);
			post.setCategory(1);
			post.setPostCode("ceo");
			post.setPostName("首席执行官");
			post.setSort(1);
			postService.save(post);
			// 新建租户对应的默认管理用户
			User user = new User();
			user.setTenantId(tenantId);
			user.setNickname("admin");
			user.setRealName("admin");
			user.setAccount("admin");
			user.setPassword(DigestUtil.encrypt("admin"));
//			user.setRoleId(String.valueOf(role.getId()));
			user.setDeptId(String.valueOf(dept.getId()));
			user.setPostId(String.valueOf(post.getId()));
			user.setBirthday(new Date());
			user.setSex(1);
			user.setIsDeleted(BladeConstant.DB_NOT_DELETED);
			// 添加租户
			boolean temp = super.saveOrUpdate(tenant);
			if (!userService.save(user)) {
				throw new ServiceException("注册失败");
			}
			// 给用户默认添加角色
			int rest = roleUserMapper.insert(UserRole.builder().userId(user.getId()).roleId(role.getId()).build());
			if(rest <= 0) {
				throw new ServiceException("注册失败");
			}
			return temp;
		}
		return super.saveOrUpdate(tenant);
	}

	private String getTenantId(List<String> codes) {
		String code = tenantIdGenerator.generate();
		if (codes.contains(code)) {
			return getTenantId(codes);
		}
		return code;
	}
}
