package com.crm.common.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * json转换配置 返回前端long处理 避免精度问题
 * @author huhong
 * @date 2019-06-22 09:51
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 增加stringConverter，解决返回字符串是双引号的问题，一定要放在fastConverter之前
        converters.add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    /**
     * 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
     * @param
     * @return org.springframework.web.client.RestTemplate
     * @author huhong
     * @date 2019-06-22 09:57
     */
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    /**
     * HttpMessageConverters 设置为fastJson
     * @return org.springframework.boot.autoconfigure.http.HttpMessageConverters
     * @author huhong
     * @date 2019-06-22 09:57
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //fastjson 默认解析*/* 在这里设置解析只解析JSON
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        //long在前端会出现精度问题 。在这里返回前端为string
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverter<?> converter = fastConverter;

        return new HttpMessageConverters(converter);
    }
}
