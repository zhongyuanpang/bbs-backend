package com.pzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.pzy.common.httpresult.Result;
import com.pzy.service.BlogService;
import com.pzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author Nice
 * @Date 2021/8/23 9:24
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    /**
     * @return : com.pzy.util.Result
     * 社区主页展示基本信息（用户数，文章数，评论数）
     * @author : pangzy
     * @date : 2021/8/23 10:31
     */
    @GetMapping("/homeInfo")
    public Result homeInfo() {
        Integer ucount = userService.count();
        Integer bcount = blogService.count();
        JSONObject result = new JSONObject();
        result.put("blogCount", bcount);
        result.put("userCount", ucount);
        return Result.SUCCESS(result);
    }

}
