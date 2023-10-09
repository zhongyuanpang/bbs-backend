package com.pzy;
import com.pzy.mapper.BlogMapper;
import com.pzy.mapper.UserMapper;
import com.pzy.common.utils.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class BbsApplicationTests {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private BlogMapper blogMapper;
    @Test
    void contextLoads() throws ParseException {
//        String password = "pangzy1013";
//        System.out.println(SecurityUtils.encryptPassword(password));
//        System.out.println(SecurityUtils.matchesPassword(password,"$2a$10$I5Y9aWjYA45EnqW2XZ0o3.UoNnAP6Ecbj.ZCfI7.9V2jM7pTU/Ruu"));

//        Date d=new Date();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        String ds=df.format(d);


        String ds="2022-02-14";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(ds);

        System.err.println(date);


    }

    }
