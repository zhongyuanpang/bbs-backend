package com.pzy.controller.admin.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.common.constant.UserConstants;
import com.pzy.common.core.domain.controller.BaseController;
import com.pzy.common.core.domain.model.LoginUser;
import com.pzy.common.httpresult.Result;
import com.pzy.common.web.service.SysPermissionService;
import com.pzy.common.web.service.TokenService;
import com.pzy.entity.SysRole;
import com.pzy.entity.SysRoleMenu;
import com.pzy.service.SysRoleMenuService;
import com.pzy.service.SysRoleService;
import com.pzy.service.SysUserService;
import com.pzy.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService rolesService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;


    /**
     * @param id
     * @return 根据id查询对应对应角色信息
     * @author pzy
     * <br/>date 2021-12-13
     */
    @GetMapping("/info/{roleId}")
    public Result info(@PathVariable("roleId") Long id) {
        return Result.SUCCESS(rolesService.getById(id));
    }


    /**
     * 新增角色
     */
    @PostMapping("/add")
    @PreAuthorize("@ss.hasAnyPermi('system:role:add')")
    public Result add(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(rolesService.checkRoleNameUnique(role))) {
            return Result.FAIL("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(rolesService.checkRoleCodeUnique(role))) {
            return Result.FAIL("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(getUsername());
        System.out.println("创建"+role);
        return Result.SUCCESS(rolesService.insertRole(role));
    }


    /**
     * 修改保存角色
     */
    @PostMapping("/update")
    @PreAuthorize("@ss.hasAnyPermi('system:role:edit')")
    public Result edit(@Validated @RequestBody SysRole role) {
        rolesService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(rolesService.checkRoleNameUnique(role))) {
            return Result.FAIL("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(rolesService.checkRoleCodeUnique(role))) {
            return Result.FAIL("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
//        role.setUpdateBy(getUsername());

        if (rolesService.updateRole(role) > 0) {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin())
            {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return Result.SUCCESS(null);
        }
        return Result.FAIL("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }


    /**
     * @param page
     * @param limit
     * @param roles
     * @return 分页获取角色列表
     * @author pzy
     * <br/>date 2021-12-14
     */
    @GetMapping("/list")
    public Result roleList(@RequestParam(value = "pageNum", defaultValue = "1") int page,
                           @RequestParam(value = "pageSize", defaultValue = "10") int limit, SysRole roles) {

        Page<SysRole> list = rolesService.selectRoleList(new Page<>(page, limit), roles);
        return Result.SUCCESS(list);
    }


    @PostMapping("/delete")
    @PreAuthorize("@ss.hasAnyPermi('system:role:remove')")
    @Transactional
    public Result deletedRole(@RequestBody Long[] ids) {
        if (ArrayUtils.contains(ids, 1L)) {
            return Result.FAIL("不可删除管理员角色");
        }
        rolesService.removeByIds(Arrays.asList(ids));
        //删除中间表
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id", ids));
        return Result.SUCCESS("");
    }


    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    @PreAuthorize("@ss.hasAnyPermi('system:role:changeStatus')")
    public Result changeStatus(@RequestBody SysRole role)
    {
        rolesService.checkRoleAllowed(role);
//        role.setUpdateBy(getUsername());
        role.setStatus(role.getStatus());
        rolesService.updateById(role);
        return Result.SUCCESS("");
    }
}
