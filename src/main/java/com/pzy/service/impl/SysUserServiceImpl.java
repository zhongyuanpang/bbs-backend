package com.pzy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.common.constant.UserConstants;
import com.pzy.common.exception.ServiceException;
import com.pzy.entity.SysMenu;
import com.pzy.entity.SysRole;
import com.pzy.entity.SysUser;
import com.pzy.mapper.SysUserMapper;
import com.pzy.service.SysMenuService;
import com.pzy.service.SysRoleService;
import com.pzy.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzy.common.utils.RedisUtil;
import com.pzy.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysRoleService sysRolesService;


    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUserName())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUserName());

        } else {
            // 获取角色编码
            List<SysRole> roles = sysRolesService.list(new QueryWrapper<SysRole>()
                    .inSql("role_id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getRoleCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            // 获取菜单操作编码
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }

            redisUtil.set("GrantedAuthority:" + sysUser.getUserName(), authority, 60 * 60);
        }

        return authority;
    }


    @Override
    public List<SysUser> getList() {
        return sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("deleted",0));
    }


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public Page<SysUser> selectUserList(Page<SysUser> page, SysUser user)
    {
        return page.setRecords(sysUserMapper.selectUserList(page,user));
    }


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Integer userId)
    {
        return sysUserMapper.selectUserById(userId);
    }


    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName)
    {
        int count = sysUserMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }


    /**
     * 修改用户头像
     *
     * @param username 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String username, String avatar)
    {
        return sysUserMapper.updateUserAvatar(username, avatar);
    }
}
