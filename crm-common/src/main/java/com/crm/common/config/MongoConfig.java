package com.crm.common.config;

import com.crm.common.config.converter.BigDecimalToDecimal128Converter;
import com.crm.common.config.converter.Decimal128ToBigDecimalConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb 数据库配置
 * @author huhong
 * @date 2019-06-21 19:31
 */
@Configuration
public class MongoConfig{


    /**
     * mongo配置调整
     * @param factory factory
     * @param context context
     * @param beanFactory beanFactory
     * @return org.springframework.data.mongodb.core.convert.MappingMongoConverter
     * @author huhong
     * @date 2019-06-22 09:44
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {
        }

        //数据库不保存_class
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        //BigDecimal 转换为  Decimal128 因为默认BigDecimal在数据库保存为string
        mappingConverter.setCustomConversions(mongoCustomConversions());

        return mappingConverter;
    }

    /**
     * 添加数据库
     * @return org.springframework.data.mongodb.core.convert.MongoCustomConversions
     * @author huhong
     * @date 2019-06-21 19:37
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new BigDecimalToDecimal128Converter());
        converterList.add(new Decimal128ToBigDecimalConverter());
        return new MongoCustomConversions(converterList);
    }

}
