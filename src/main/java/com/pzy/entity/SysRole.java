package com.pzy.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author pzy
 * @since 2022-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleCode;

    /**
     * 角色显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态
     */
    private String status;

    /**
     * 删除标志（0正常 1删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private boolean menuCheckStrictly;


    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Integer[] menuIds;


    public boolean isMenuCheckStrictly() {
        return menuCheckStrictly;
    }


    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Integer roleId) {
        return roleId != null && 1 == roleId;
    }

}
