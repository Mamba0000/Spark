package com.lyc.spark.service.uum.vo;

import com.lyc.spark.service.uum.entity.Tenant;
import com.lyc.spark.service.uum.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TenantVO对象", description = "TenantVO对象")
public class TenantVO extends Tenant {
    /**
     * 租户状态
     */
    private String statusName;

}
