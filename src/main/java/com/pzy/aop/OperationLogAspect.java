package com.pzy.aop;


import com.pzy.common.httpresult.Result;
import com.pzy.common.utils.JwtUtil;
import com.pzy.dto.UserDto;
import com.pzy.entity.Logoperation;
import com.pzy.mapper.LogoperationMapper;
import com.pzy.service.LogoperationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//@Aspect
//@Component
public class OperationLogAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LogoperationService logoperationService;

    @Autowired(required = false)
    private LogoperationMapper logoperationMapper;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.pzy.aop.OperationLogAnnotation)")
    public void operLogPoinCut() {
    }

    @After("operLogPoinCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
// 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        HySysUser sysUser = redisUtils.getUserByToken(request.getHeader("token"));
        //业务操作
        String ip = null;//IP地址
        String address = null;//计算机名称
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
            address = addr.getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterReturning(returning  /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result  方法返回值
     * @throws Throwable
     */ = "results", value = "operLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint, Object results) throws Throwable {
        String ip = null;//IP地址
        String address = null;//计算机名称
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
            address = addr.getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String token = request.getHeader("Authorization");
        UserDto userDto = jwtUtil.getClaimByToken(token);
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //将返回值转换成result对象
            Result result = (Result) results;

            Logoperation logoperation = new Logoperation();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                //操作模块
                logoperation.setModel(annotation.operModul());
                //操作类型
                logoperation.setType(annotation.operType());
                //操作时间
                logoperation.setDate(Timestamp.valueOf(sdf.format(new Date())));
                //操作用户
                logoperation.setCreateUser(userDto.getUsername());
                //操作IP
                logoperation.setComputerIp(ip);
                //计算机名称
                logoperation.setComputer(address);
                //返回值
                logoperation.setMessage(result.getMessage());

                logoperationService.save(logoperation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
