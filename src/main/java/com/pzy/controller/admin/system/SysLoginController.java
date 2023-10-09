package com.pzy.controller.admin.system;


import com.pzy.common.core.domain.model.LoginBody;
import com.pzy.common.httpresult.Result;
import com.pzy.common.web.service.SysLoginService;
import com.pzy.common.web.service.SysPermissionService;
import com.pzy.entity.SysMenu;
import com.pzy.entity.SysUser;
import com.pzy.service.SysMenuService;
import com.pzy.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginBody loginBody, HttpServletResponse response)
    {
        String token = loginService.login(loginBody.getUsername(),loginBody.getPassword());
        return Result.SUCCESS(token);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();

        //  角色集合
        Set<String> roles = permissionService.getRolePermission(user);

        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);

        return Result.SUCCESS(ajax);
    }



    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public Result getRouters()
    {
        Integer userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return Result.SUCCESS(menuService.buildMenus(menus));
    }
}
