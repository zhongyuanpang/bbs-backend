package com.pzy.service.impl;

import com.pzy.entity.Type;
import com.pzy.mapper.TypeMapper;
import com.pzy.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-07-07
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired(required = false)
    private TypeMapper typeMapper;

//    @Override
//    public List<BlogVO> getTypesById(Long id) {
//        return typeMapper.getTypesById(id);
//    }
}
