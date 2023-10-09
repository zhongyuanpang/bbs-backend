package com.pzy.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    List<Long> getNavMenuIds(Long userId);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Integer userId);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(Page<SysUser> page, @Param("user") SysUser sysUser);


    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    /**
     * 修改用户头像
     *
     * @param username 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(@Param("userName") String username, @Param("avatar") String avatar);
}
