
package com.lyc.spark.service.uum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyc.spark.core.mybatisplus.entity.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 实体类
 *
 * 
 */
@Data
@TableName("spark_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 编号
	 */
	private String code;
	/**
	 * 账号
	 */
	@NotBlank(message = "账号不能为空")
	private String account;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 真名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 邮箱
	 */
	@Email(message = "必须为邮箱格式")
	private String email;
	/**
	 * 手机
	 */
	@NotBlank(message = "联系电话不能为空")
	// 目前正则表达式只验证了是否是数字
	@Pattern(regexp = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")
	private String phone;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 性别
	 *
	 */
	private Integer sex;

	/**
	 * 部门id
	 */
	@NotBlank(message = "部门不能为空")
	private String deptId;
	/**
	 * 岗位id
	 */
	@NotBlank(message = "岗位不能为空")
	private String postId;

}
