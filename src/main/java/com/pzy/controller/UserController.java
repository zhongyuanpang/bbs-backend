package com.pzy.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pzy.common.core.domain.model.LoginTbBody;
import com.pzy.common.core.domain.model.RegisterTbBody;
import com.pzy.common.httpresult.Result;
import com.pzy.dto.UserDto;
import com.pzy.entity.Blog;
import com.pzy.entity.User;
import com.pzy.service.BlogService;
import com.pzy.service.UserService;
import com.pzy.common.utils.*;

import com.pzy.common.utils.email.EmailSendUtil;
import com.pzy.common.utils.uuid.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailSendUtil emailSendUtil;

    @Autowired
    private RedisUtil redisUtill;

    /**
     * @return : java.lang.Object
     * 发送邮箱验证码
     * @author : pangzy
     * @date : 2021/7/3 14:02
     */
    @PostMapping(value = "/sendCode")
    public Result sendEmail(@RequestParam("email") String receiver) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", receiver));
        if (user != null) {
            return Result.FAIL("该邮箱已注册");
        } else {
            String code = CodeGenerateUtil.generateVerCode();
            redisUtill.set(receiver, code, 5000);
            try {
                // 调用发送邮件
                emailSendUtil.sendEmailVerCode(receiver, code);
                return Result.SUCCESS("code发送成功");
            } catch (Exception e) {
                return Result.FAIL("验证码发送失败");
            }
        }
    }

    /**
     * @return : com.pzy.util.Result
     * 用户注册传来得code码比对，正确的话则注册成功，否则验证码错误，
     * 并判断用户是否存在，
     * @author : pangzy
     * @date : 2021/7/3 14:02
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterTbBody registerTbBody, User user) {
//        User username = userService.getOne(new QueryWrapper<User>().eq("username", registerTbBody.getUsername()));
        // 判断验证码是否正确
        if (redisUtill.get(registerTbBody.getUsername()).equals(registerTbBody.getCode())) {
            user.setId(IdUtils.fastUUID());
            user.setUsername(registerTbBody.getUsername());
            user.setNickname(registerTbBody.getNickname());
            user.setPassword(SecurityUtils.encryptPassword(registerTbBody.getPassword()));
            //默认背景图片
            user.setEmail(registerTbBody.getUsername());
            user.setBackground("http://p8.qhimg.com/bdm/1024_768_85/t01a9e376952238ea53.jpg");
            // 判断邮箱是否为qq邮箱，设置用户头像为qq头像
            if (registerTbBody.getUsername().trim().toLowerCase().contains("@qq.com")) {
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(registerTbBody.getUsername());
                user.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + m.replaceAll("").trim() + "&s=100");
            }
            boolean save = userService.save(user);
            if (!save) {
                return Result.FAIL("注册失败!");
            }
            return Result.SUCCESS("注册成功");
        } else {
            return Result.FAIL("验证码错误!");
        }
    }

    /**
     * @return : com.pzy.util.Result
     * 用户登录，判断用户是否已存在及密码是否正确
     * @author : pangzy
     * @date : 2021/7/6 18:22
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginTbBody loginTbBody, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginTbBody.getUsername()));
        if (user != null) {
            if (SecurityUtils.matchesPassword(loginTbBody.getPassword(), user.getPassword())) {
                UserDto userDto = new UserDto();
                //将user的复值给UserDto一样的字段
                BeanUtils.copyProperties(user, userDto);
//                System.out.println(JSON);
                String jwt = jwtUtil.generateToken(userDto);
                response.setHeader("Authorization", jwt);
                response.setHeader("Access-control-Expose-Headers", "Authorization");
//                //UserDto 用来存储返回给前端的部分信息，不包含重要信息
                return Result.SUCCESS(jwt);
            } else {
                return Result.FAIL("密码不正确");
            }
        } else {
            return Result.FAIL("该用户不存在");
        }
    }
//
//
///**
// * @author : pangzy
// * @date : 2021/7/8 18:03
// * @return : com.pzy.util.Result
// * 退出登录
// */
//    //该注解作用为，必须登录才能进行访问
//    @RequiresAuthentication
//    @GetMapping("/logout")
//    public Result logout(){
//        SecurityUtils.getSubject().logout();
//        return Result.succ(null);
//    }


    @GetMapping("/getUser/{id}")
    public Result getUser(@PathVariable String id) {
        System.out.println(id);
        User user = userService.getById(id);
        System.out.println(user);
        return Result.SUCCESS(user);
    }


    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user) {
        return Result.SUCCESS(user);
    }

    /**
     * @return : com.pzy.util.Result
     * 修改个人页面背景图片
     * @author : pangzy
     * @date : 2021/8/18 17:00
     */
    @GetMapping("/editBG")
    public Result editBG(@RequestParam("bg") String bg, @RequestParam("id") String id) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("background", bg);
        boolean result = userService.update(null, wrapper);
        if (result) {
            User user = userService.getById(id);
            return Result.SUCCESS(user);
        } else {
            return Result.FAIL("更新失败");
        }
    }

    /**
     * @return : com.pzy.util.Result
     * 根据ID获取用户基本信息
     * @author : pangzy
     * @date : 2021/8/23 14:39
     */
    @GetMapping("/getUserBaseInfo/{userId}")
    public Result getUserBaseInfo(@PathVariable String userId) {
        JSONObject result = new JSONObject();
        User user = userService.getOne(new QueryWrapper<User>().eq("user_id", userId));
        UserDto userDto = new UserDto();
        BeanUtil.copyProperties(user,userDto);
        Integer count = blogService.count(new QueryWrapper<Blog>().eq("user_id", userId));
        result.put("user", userDto);
        result.put("blogCount", count);
        return Result.SUCCESS(result);
    }

    /**
     * @return : com.pzy.util.Result
     * 根据ID获取用户作品 已发布及草稿
     * @author : pangzy
     * @date : 2021/10/14 16:21
     */
    public Result getUserWorks(@RequestParam("uid") String id) {
        JSONObject result = new JSONObject();
//        QueryWrapper wrapper =
        return Result.SUCCESS("");
    }

}
