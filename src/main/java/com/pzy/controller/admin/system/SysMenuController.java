package com.pzy.controller.admin.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzy.common.constant.UserConstants;
import com.pzy.common.core.domain.controller.BaseController;
import com.pzy.common.httpresult.Result;
import com.pzy.entity.SysMenu;
import com.pzy.service.SysMenuService;
import com.pzy.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public Result list(SysMenu menu) {
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, getUserId());
        return Result.SUCCESS(menus);
    }


    /**
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public Result getInfo(@PathVariable Long menuId) {
        return Result.SUCCESS(sysMenuService.getOne(new QueryWrapper<SysMenu>().eq("menu_id", menuId)));
    }


    /**
     * 新增菜单
     */
    @PostMapping("/add")
    @PreAuthorize("@ss.hasAnyPermi('system:menu:add')")
    public Result add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(sysMenuService.checkMenuNameUnique(menu))) {
            return Result.FAIL("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return Result.FAIL("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        return Result.SUCCESS(sysMenuService.save(menu));
    }


    /**
     * 修改菜单
     */
    @PostMapping("/update")
    @PreAuthorize("@ss.hasAnyPermi('system:menu:edit')")
    public Result edit(@Validated @RequestBody SysMenu menu) {
        // 判断是菜单否已存在
        if (UserConstants.NOT_UNIQUE.equals(sysMenuService.checkMenuNameUnique(menu))) {
            return Result.FAIL("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        // 判断外链菜单是否以http(s)://开头
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return Result.FAIL("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        // 不能选择自己为上级目录
        else if (menu.getMenuId().equals(menu.getParentId())) {
            return Result.FAIL("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return Result.SUCCESS(sysMenuService.update(menu, new QueryWrapper<SysMenu>().eq("menu_id", menu.getMenuId())));
    }


    /**
     * 删除菜单
     */
    @PostMapping("/delete/{menuId}")
    @PreAuthorize("@ss.hasAnyPermi('system:menu:remove')")
    public Result remove(@PathVariable("menuId") Integer menuId) {
        if (sysMenuService.hasChildByMenuId(menuId)) {
            return Result.FAIL("存在子菜单,不允许删除");
        }
//        if (sysMenuService.checkMenuExistRole(menuId))
//        {
//            return Result.fail("菜单已分配,不允许删除");
//        }
        return Result.SUCCESS(sysMenuService.remove(new QueryWrapper<SysMenu>().eq("menu_id", menuId)));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public Result treeselect(SysMenu menu) {
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, getUserId());
        return Result.SUCCESS(sysMenuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
//    @GetMapping("/roleMenuTreeselect/{roleId}")
    @GetMapping("/roleMenuTreeselect")
    public Result roleMenuTreeselect(@RequestParam("roleId") Integer roleId) {
        List<SysMenu> menus = sysMenuService.selectMenuList(getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", sysMenuService.buildMenuTreeSelect(menus));
        return Result.SUCCESS(map);
    }


}
