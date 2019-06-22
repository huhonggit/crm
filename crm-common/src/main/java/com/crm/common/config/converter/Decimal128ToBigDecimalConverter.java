package com.crm.common.config.converter;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.math.BigDecimal;

/**
 * BigDecimal 转换器
 *
 * @author huhong
 * @date 2019-06-21 19:34
 */
@ReadingConverter
@WritingConverter
public class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {

    public BigDecimal convert(Decimal128 decimal128) {
        return decimal128.bigDecimalValue();
    }


}