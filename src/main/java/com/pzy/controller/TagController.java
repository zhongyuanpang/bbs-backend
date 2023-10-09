package com.pzy.controller;


import com.pzy.common.httpresult.Result;
import com.pzy.entity.Tag;
import com.pzy.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "获取标签列表", httpMethod = "GET")
    @RequestMapping(value = "/tagsList", method = RequestMethod.GET)
    public Result tagsList() {
        List<Tag> list = tagService.list();
        return Result.SUCCESS(list);
    }

    @ApiOperation(value = "新增标签", httpMethod = "POST")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(@RequestBody Tag tag) {
        tagService.save(tag);
        return Result.SUCCESS();
    }
}
