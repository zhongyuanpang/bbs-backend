package com.pzy.service.impl;

import com.pzy.entity.Logoperation;
import com.pzy.mapper.LogoperationMapper;
import com.pzy.service.LogoperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author pzy
 * @since 2022-05-22
 */
@Service
public class LogoperationServiceImpl extends ServiceImpl<LogoperationMapper, Logoperation> implements LogoperationService {

}
