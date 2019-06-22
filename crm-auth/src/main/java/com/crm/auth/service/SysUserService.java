package com.crm.auth.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户服务类
 * @author huhong
 * @date 2019-06-21 19:23
 */
@Service
public class SysUserService {


    @Resource
    private MongoTemplate mongoTemplate;

}
