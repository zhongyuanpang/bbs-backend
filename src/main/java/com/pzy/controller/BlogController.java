package com.pzy.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzy.aop.OperationLogAnnotation;
import com.pzy.common.core.domain.controller.BaseController;
import com.pzy.common.httpresult.Result;
import com.pzy.common.httpresult.ResultCode;
import com.pzy.entity.Blog;
import com.pzy.mapper.BlogMapper;
import com.pzy.mapper.UserMapper;
import com.pzy.service.BlogService;
import com.pzy.service.TagService;
import com.pzy.common.utils.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.pzy.common.utils.UploadGiteeImgBed;

import java.io.IOException;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/blog")
public class BlogController extends BaseController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired(required = false)
    private BlogMapper blogMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * @param blog
     * @description: 创建文章
     * @return: Result
     * @author: 庞中原
     * @date: 2022/4/5 21:19
     */
    @PostMapping("/create")
    public Result saveBlog(@Validated @RequestBody Blog blog) {
        int insert = blogMapper.insert(blog);
        System.out.println(insert);
        return Result.SUCCESS();
    }

    /**
     * @return : com.pzy.util.Result
     * @description: 根据分页获取文章
     * @author : 庞中原
     * @date : 2021/8/16 17:41
     */
    @OperationLogAnnotation(operModul = "博客表",operType = "获取分页文章")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result blogList() {
        Page<Blog> list = blogService.ShowHomeBlogList(getPage());
        return Result.SUCCESS(ResultCode.SUCCESS,list);
    }

    /**
     * @param blogId
     * @description: 根据ID获取对应博客
     * @return: Result
     * @author: 庞中原
     * @date: 2022/4/5 21:11
     */
    @RequestMapping(value = "/getBlog/{blogId}",method = RequestMethod.GET)
    public Result getBlogById(@PathVariable Integer blogId) {
        Blog blog = blogService.getBlogById(blogId);
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(blog.getContent()));
        return Result.SUCCESS(blog);
    }

    /**
     * @return : com.pzy.util.Result
     * 删除博客 Long id（博客ID）
     * @author : pangzy
     * @date : 2021/9/16 13:33
     */
    @RequestMapping(value = "/deleteBlog",method = RequestMethod.GET)
    public Result deleteBlog(@RequestParam("id") Long id) {
        boolean b = blogService.removeById(id);
        if (b) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        return Result.SUCCESS();
    }

    /**
     * @return : com.pzy.util.Result
     * 博客详情
     * @author : pangzy
     * @date : 2021/9/16 13:34
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result detail(@RequestParam("id") String id) {
        Blog blog = blogService.getOne(new QueryWrapper<Blog>().eq("id", id));
        Blog b = new Blog();
        BeanUtil.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        return Result.SUCCESS(b);
    }

    /**
     * @return : com.pzy.util.Result
     * 博客图片上传
     * @author : pangzy
     * @date : 2021/9/16 13:38
     */
    @PostMapping("/uploadImg")
//    @CrossOrigin(origins = {"*","null"})
    public Result upload(@RequestParam("files") MultipartFile file) throws IOException {
        String originalFilname = file.getOriginalFilename();
        if (originalFilname == null) {
            System.out.println("上传文件为空");
        }
        //创建新的路径
        String targetURL = UploadGiteeImgBed.createUploadFileUrl(originalFilname);

        //请求体封装
        Map<String, Object> uploadBodyMap = UploadGiteeImgBed.getUploadBodyMap(file.getBytes());
        //借助HttpUtil工具类发送POST请求
        String JSONResult = HttpUtil.post(targetURL, uploadBodyMap);
        //解析响应JSON字符串
        JSONObject jsonObj = JSONUtil.parseObj(JSONResult);
//        cn.hutool.json.JSONObject jsonObj = JSONUtil.parseObj(JSONResult);

        JSONObject content = JSONUtil.parseObj(jsonObj.getObj("content"));
//        cn.hutool.json.JSONObject content = JSONUtil.parseObj(jsonObj.getObj("content"));

        String url = (String) content.getObj("download_url");
        return Result.SUCCESS(content.getObj("download_url"));
    }
}


