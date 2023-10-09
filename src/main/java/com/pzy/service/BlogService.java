package com.pzy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 * @author nice
 * @since 2021-07-07
 */
public interface BlogService extends IService<Blog> {
    /**
     * @description:  主表博客展示列表
     * @param page
     * @return: Page<Blog>
     * @author: 庞中原
     * @date: 2022/5/22 14:30
     */
    Page<Blog> ShowHomeBlogList(Page<Blog> page);

    /**
     * @description:  根据id获取一篇文章
     * @param blogId
     * @return: Blog
     * @author: 庞中原
     * @date: 2022/5/22 14:30
     */
    Blog getBlogById(Integer blogId);
}
