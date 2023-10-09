package com.pzy.controller;


import com.pzy.common.httpresult.Result;
import com.pzy.entity.Type;
import com.pzy.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * @return : com.pzy.util.Result
     * 分类列表
     * @author : pangzy
     * @date : 2021/8/23 10:40
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getTypes() {
        List<Type> type = typeService.list();
        return Result.SUCCESS(type);
    }

    /**
     * @return : com.pzy.util.Result
     * 新增分类
     * @author : pangzy
     * @date : 2021/8/23 10:40
     */
    @PatchMapping("/addTypes")
    public Result addTypes(@RequestParam("name") String name, @RequestParam("icon") String icon, Type type) {
        System.out.println(name + "  " + icon + " ");
        System.out.println(type);
        typeService.save(type);
        return Result.SUCCESS(type);
    }

/**
 * @author : pangzy
 * @date : 2021/8/23 10:42
 * @return : com.pzy.util.Result
 * 根据id查找分类所包含的所有文章
 */
//    @GetMapping("/getTypesById")
//    public Result getTypesById(@RequestParam("id") Long id){
//        System.out.println(id);
//        List<BlogVO> list = typeService.getTypesById(id);
//        return Result.succ(list);
//    }

}
