package com.pzy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.entity.Blog;
import com.pzy.mapper.BlogMapper;
import com.pzy.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired(required = false)
    private BlogMapper blogMapper;


    @Override
    public Page<Blog> ShowHomeBlogList(Page<Blog> page) {
        return page.setRecords(blogMapper.ShowHomeBlogList(page));
    }

    /**
     * 根据博客Id获取相信内容
     * */
    @Override
    public Blog getBlogById(Integer blogId) {
        return blogMapper.getBlogById(blogId);
    }
}
