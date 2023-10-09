package com.pzy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    String getUserAuthorityInfo(Long userId);

    //查询所有管理员
    List<SysUser> getList();

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public Page<SysUser> selectUserList(Page<SysUser> page, SysUser user);


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Integer userId);


    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public String checkUserNameUnique(String userName);


    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);


    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);


    /**
     * 修改用户头像
     *
     * @param username 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String username, String avatar);
}
