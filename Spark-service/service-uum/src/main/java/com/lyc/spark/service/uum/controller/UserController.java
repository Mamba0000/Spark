package com.lyc.spark.service.uum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.lyc.spark.core.common.api.CommonResult;
import com.lyc.spark.core.mybatisplus.page.CommonPage;
import com.lyc.spark.service.uum.entity.Role;
import com.lyc.spark.service.uum.entity.User;
import com.lyc.spark.service.uum.service.impl.RoleService;
import com.lyc.spark.service.uum.service.impl.UserService;
import com.lyc.spark.service.uum.vo.UpdateUserPasswordParam;
import com.lyc.spark.service.uum.vo.UserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@Api(tags = "UserController", description = "用户相关")
@RequestMapping("/user")
public class UserController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<User> register(@Validated @RequestBody UserParam userParam) {
        User user = userService.register(userParam);
        if (user == null) {
            return CommonResult.fail();
        }
        return CommonResult.success(user);
    }

//    @ApiOperation(value = "登录以后返回token")
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult login(@Validated @RequestBody UserLoginParam userLoginParam) {
//        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
//        if (token == null) {
//            return CommonResult.validateFail("用户名或密码错误");
//        }
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", token);
//        tokenMap.put("tokenHead", tokenHead);
//        return CommonResult.success(tokenMap);
//    }




    @ApiOperation(value = "selectTest")
    @RequestMapping(value = "/selectTest", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult selectTest() {
        Page page = new Page();
        page.setSize(2);
        page.setCurrent(1);

        User user = new User();
        user.setId(100L);
        return CommonResult.success(userService.selectMyUsers(page, user));
    }

//    @ApiOperation(value = "刷新token")
//    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult refreshToken(HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        String refreshToken = userService.refreshToken(token);
//        if (refreshToken == null) {
//            return CommonResult.failed("token已经过期！");
//        }
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", refreshToken);
//        tokenMap.put("tokenHead", tokenHead);
//        return CommonResult.success(tokenMap);
//    }

//    @ApiOperation(value = "获取当前登录用户信息")
//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult getAdminInfo(@RequestParam(value = "token", required = false) String token) {
//
//        String username = JWTUtil.getUsername(token);
//
//        User user = userService.geUserByName(username);
//        Map<String, Object> data = new HashMap<>();
//        data.put("username", user.getUsername());
//        data.put("menus", roleService.getMenuList(user.getId()));
//        data.put("icon", user.getIcon());
//        List<Role> roleList = userService.getRoleList(user.getId());
//        if (CollUtil.isNotEmpty(roleList)) {
//            List<String> roles = roleList.stream().map(Role::getName).collect(Collectors.toList());
//            data.put("roles", roles);
//        }
//        return CommonResult.success(data);
//    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
//    @RequiresPermissions("//")
    public CommonResult<CommonPage<User>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<User> adminList = userService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<User> getItem(@PathVariable Long id) {
        User admin = userService.getById(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody User admin) {
        boolean success = userService.update(id, admin);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.fail();
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateUserPasswordParam updatePasswordParam) {
        int status = userService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.fail("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.fail("找不到该用户");
        } else if (status == -3) {
            return CommonResult.fail("旧密码错误");
        } else {
            return CommonResult.fail();
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
//    @RequiresPermissions("user:delete")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = userService.delete(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.fail();
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        User user = new User();
        user.setStatus(status);
        boolean success = userService.update(id, user);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.fail();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.fail();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
//    @RequiresPermissions("user:select")
    public CommonResult<List<Role>> getRoleList(@PathVariable Long adminId) {
        List<Role> roleList = userService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

}
