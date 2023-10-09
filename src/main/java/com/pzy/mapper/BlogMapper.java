package com.pzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.entity.Blog;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 主页博客列表展示数据
     * */
    List<Blog> ShowHomeBlogList(Page<Blog> page);

    /**
     * 根据ID获取数据
     * */
    Blog getBlogById(Integer blogId);
}
