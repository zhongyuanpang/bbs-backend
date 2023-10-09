package com.pzy.mapper;

import com.pzy.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
public interface TypeMapper extends BaseMapper<Type> {
//    @Select("SELECT tb_blog.*,tb_user.`nickname`,tb_user.`avatar` FROM tb_blog,tb_user WHERE tb_blog.type_id = #{id} AND tb_blog.published = 1 AND tb_blog.user_id = tb_user.id")
//    List<BlogVO> getTypesById(Long id);
}
