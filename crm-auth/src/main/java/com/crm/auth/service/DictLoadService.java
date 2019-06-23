package com.crm.auth.service;

import com.crm.auth.po.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 字典初始化服务
 * @author huhong
 * @date 2019-06-23 14:13
 */
@Service
public class DictLoadService implements InitializingBean {


    public static final String ROOT_Id="1";

    private static final Logger LOGGER = LoggerFactory.getLogger(DictLoadService.class);

    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 初始设置ROOT节点
     * @author huhong
     * @date 2019-06-23 14:12
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Dict root = mongoTemplate.findById(ROOT_Id,Dict.class);
        if(root==null){
            root = new Dict();
            root.setId(ROOT_Id);
            root.setName("根节点");
            root.setCode("ROOT");
            root.setInfo("根目录");
            root.setPid(null);
            root.setPym("ROOT");
            root.setDataType("string");
            root.setType("目录");
            mongoTemplate.save(root);
            LOGGER.info("初始化dict字典根节点!");
        }

    }


}
