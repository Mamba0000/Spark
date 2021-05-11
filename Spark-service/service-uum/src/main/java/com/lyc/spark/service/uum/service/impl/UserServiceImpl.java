
package com.lyc.spark.service.uum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.lyc.spark.common.constant.CommonConstant;
import com.lyc.spark.core.common.exception.ServiceException;
import com.lyc.spark.core.mybatisplus.base.BaseServiceImpl;
import com.lyc.spark.core.tool.DateUtil;
import com.lyc.spark.core.tool.DigestUtil;
import com.lyc.spark.core.tool.Func;
import com.lyc.spark.service.uum.entity.*;
import com.lyc.spark.service.uum.mapper.UserMapper;
import com.lyc.spark.service.uum.service.ITenantService;
import com.lyc.spark.service.uum.service.IUserService;
import com.lyc.spark.service.uum.service.IRolePermissionService;
import com.lyc.spark.service.uum.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
    private static final String GUEST_NAME = "guest";
    private static final String MINUS_ONE = "-1";

    @Autowired
    @Lazy
    public ITenantService tenantService;

//	private IUserOauthService userOauthService;

    //
    @Autowired
    public IUserRoleService userRoleRelationService;

    @Autowired
    public IRolePermissionService rolePermissionRelationService;

    @Override
    public boolean submit(User user) {
        if (Func.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtil.encrypt(user.getPassword()));
        }
        Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, user.getTenantId()).eq(User::getAccount, user.getAccount()));
        if (cnt > 0) {
            throw new ApiException("当前用户已存在!");
        }
        return saveOrUpdate(user);
    }

    @Override
    public IPage<User> selectUserPage(IPage<User> page, User user) {
        return page.setRecords(baseMapper.selectUserPage(page, user));
    }

    @Override
    public UserInfo userInfo(Long userId) {
        UserInfo userInfo = new UserInfo();
        User user = baseMapper.selectById(userId);
        userInfo.setUser(user);
        if (Func.isNotEmpty(user)) {
//            List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
//            userInfo.setRoles(roleAlias);
        }
        return userInfo;
    }

    @Override
    public UserInfo userInfo(String tenantId, String account, String password) {
        UserInfo userInfo = new UserInfo();
        User user = baseMapper.getUser(tenantId, account, password);
        userInfo.setUser(user);
        if (Func.isNotEmpty(user)) {

            userInfo.setRoles(getRoles(user)
                    .stream()
                    .map(item -> item.getRoleName())
                    .collect(Collectors.toList()));

            // 这里还有点毒 会有一条空数据 先过滤处理下 没时间去弄了
            userInfo.setPermissions(getPermissions(user).stream()
                    .filter(item->  (Func.isNotEmpty(item) && Func.isNotEmpty(item.getValue())) )
                    .map(item -> item.getValue())
                    .collect(Collectors.toList()));
        }

        return userInfo;
    }

    @Override
    public boolean grant(String userIds, String roleIds) {
        User user = new User();
//        user.setRoleId(roleIds);
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public boolean resetPassword(String userIds) {
        User user = new User();
        user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
        user.setUpdateTime(DateUtil.now());
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
        User user = getById(userId);
        if (!newPassword.equals(newPassword1)) {
            throw new ServiceException("请输入正确的确认密码!");
        }
        if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
            throw new ServiceException("原密码不正确!");
        }
        return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
    }

    @Override
    public List<String> getRoleName(String roleIds) {
        return baseMapper.getRoleName(Func.toStrArray(roleIds));
    }

    @Override
    public List<String> getDeptName(String deptIds) {
        return baseMapper.getDeptName(Func.toStrArray(deptIds));
    }

    // 获取用户角色集合
    @Override
    public List<Role> getRoles(User user) {
        return baseMapper.getRoles(user);
    }

    // 获取用户权限集合
    @Override
    public List<Permission> getPermissions(User user) {
        List<Permission>  permissions = baseMapper.getPermissionsByRoleIds(getRoles(user).stream().map(item -> item.getId()).toArray(Long[]::new));
        return  permissions;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerGuest(User user, Long oauthId) {
        Tenant tenant = tenantService.getByTenantId(user.getTenantId());
        ;
        if (tenant == null || tenant.getId() == null) {
            throw new ApiException("租户信息错误!");
        }
//		UserOauth userOauth = userOauthService.getById(oauthId);
//		if (userOauth == null || userOauth.getId() == null) {
//			throw new ApiException("第三方登陆信息错误!");
//		}
        user.setRealName(user.getNickname());
//		user.setAvatar(userOauth.getAvatar());
//        user.setRoleId(MINUS_ONE);
        user.setDeptId(MINUS_ONE);
        user.setPostId(MINUS_ONE);
        boolean userTemp = this.submit(user);
//		userOauth.setUserId(user.getId());
//		userOauth.setTenantId(user.getTenantId());
//		boolean oauthTemp = userOauthService.updateById(userOauth);
        return (userTemp);
    }

}
