package com.pzy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文章分类表
 * @author nice
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "serialid", type = IdType.AUTO)
    private Long serialid;

    /**
     * 分类id
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类icon
     */
    private String icon;
}
