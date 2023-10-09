package com.pzy.aop;
import java.lang.annotation.*;

/**
 * @description: 自定义操作日志注解
 * @author: 庞中原
 * @date: 2022/5/22 16:31
 */
@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface OperationLogAnnotation {
    String operModul() default ""; // 操作模块

    String operType() default "";  // 操作类型
}
