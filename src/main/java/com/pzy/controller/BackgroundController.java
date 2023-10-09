package com.pzy.controller;

import com.pzy.common.httpresult.Result;
import com.pzy.common.utils.RandomBgUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Nice
 * @Date 2021/8/14 8:56
 */
@RestController
public class BackgroundController {
    @GetMapping("/getBC")
    public Result getBC(){
        String imgUrl = RandomBgUtils.randomBackground();
        return Result.SUCCESS(imgUrl);
    }
}
