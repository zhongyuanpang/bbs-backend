package com.pzy.service.impl;

import com.pzy.entity.UserTag;
import com.pzy.mapper.UserTagMapper;
import com.pzy.service.UserTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户标签表 服务实现类
 * </p>
 *
 * @author pzy
 * @since 2022-05-22
 */
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements UserTagService {

}
