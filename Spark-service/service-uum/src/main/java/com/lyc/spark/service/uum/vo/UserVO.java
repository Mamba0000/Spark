package com.lyc.spark.service.uum.vo;

import com.lyc.spark.service.uum.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "UserVO对象")
public class UserVO extends User {



    /**
     * 角色名
     */
    private String roleName;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 岗位名
     */
    private String postName;

    /**
     * 性别
     */
    private String sexName;


}
