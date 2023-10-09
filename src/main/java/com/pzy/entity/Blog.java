package com.pzy.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 博客文章表
 *
 * @author nice
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "serialid", type = IdType.AUTO)
    private Long serialid;

    /**
     * 博客id
     */
    private String id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 博客修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 博客浏览数
     */
    private Integer views;

    /**
     * 点赞次数
     */
    private Integer clickLike;

    /**
     * 发表博客描述
     */
    private String description;

    /**
     * 是否公开博客(1:为公开、0：私密)
     */
    private Integer published;

    /**
     * 博客题材（0=原创/1=翻译 /2= 转载 ）
     */
    private Integer flag;

    /**
     * 外键分类表
     */
    private Integer typeId;

    /**
     * 外键用户表
     */
    @TableField("user_id")
    private String userId;

    /**
     * tag标签
     */
    private String tags;

    /**
     * 文章状态 1: 发布 0：保存
     */
    private Integer status;


    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer delFlag;

    /**
     * 博客首图
     */
    private String cover;

    // user表
    @TableField(exist = false)
    private User user;
}
