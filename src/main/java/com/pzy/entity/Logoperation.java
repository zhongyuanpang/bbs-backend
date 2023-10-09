package com.pzy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author pzy
 * @since 2022-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Logoperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 计算机名
     */
    private String computer;

    /**
     * 计算机ip地址
     */
    private String computerIp;

    /**
     * 用户名
     */
    private String createUser;

    /**
     * 时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date date;

    /**
     * 模块
     */
    private String model;

    /**
     * 单号
     */
    private String nos;

    /**
     * 类别
     */
    private String type;

    /**
     * 返回值信息
     */
    private String message;


}
