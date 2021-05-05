package com.lyc.spark.service.uum.util;

import com.lyc.spark.core.tool.RandomType;
import com.lyc.spark.core.tool.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class TenantIdGeneratorImpl implements TenantIdGenerator {
    public TenantIdGeneratorImpl() {
    }

    public String generate() {
        return StringUtil.random(6, RandomType.INT);
    }
}
