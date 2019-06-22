package com.crm.auth.service;

import com.alibaba.fastjson.JSON;
import com.crm.auth.po.SysUser;
import com.crm.common.enums.StatusType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 系统用户服务类
 * @author huhong
 * @date 2019-06-21 19:23
 */
@Service
public class SysUserService implements InitializingBean {


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public void afterPropertiesSet() throws Exception {
        SysUser user = new SysUser();
        user.setName("sd");
        user.setStatus(StatusType.enable);

        redisTemplate.opsForValue().set("a",user );


        SysUser a = (SysUser) redisTemplate.opsForValue().get("a");
        System.out.println("测试");
        System.out.println(JSON.toJSONString(a));

        Test test = new Test();
        test.setTt(new BigDecimal("22.345"));

        mongoTemplate.insert(test);
        System.out.println("2323131");

    }
}
