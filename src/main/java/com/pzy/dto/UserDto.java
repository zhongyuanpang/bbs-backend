package com.pzy.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户返回数据dto
 *
 * @Author Nice
 * @Date 2021/7/8 11:21
 */
@Data
public class UserDto implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户登录账号
     */

    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 用户更新时间
     */
    private Date updateTime;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户手机号
     */
    private Long phone;

    /**
     * 个人签名
     */
    private String signature;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户背景图片
     */
    private String background;

}
