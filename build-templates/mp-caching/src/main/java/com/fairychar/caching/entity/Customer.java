package com.fairychar.caching.entity;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 用户表(Customer)表实体类
 *
 * @author chiyo
 * @since 2021-05-28 15:47:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("serial")
@TableName("customer")
@ApiModel("Customer")
public class Customer extends Model<Customer> {
    @ApiModelProperty("")
    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;
    @ApiModelProperty("名称")
    @TableField(value = "`name`")
    private String name;
    @ApiModelProperty("邮箱地址")
    @TableField(value = "`email`")
    private String email;
    @ApiModelProperty("")
    @TableField(value = "`password`")
    private String password;
    @ApiModelProperty("逻辑删除标记")
    @TableField(value = "`flag`")
    private Integer flag;
    @ApiModelProperty("乐观锁")
    @Version()
    private Integer version;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    /**
     * id -
     */
    public final static String ID = "id";

    /**
     * name - 名称
     */
    public final static String NAME = "name";

    /**
     * email - 邮箱地址
     */
    public final static String EMAIL = "email";

    /**
     * password -
     */
    public final static String PASSWORD = "password";

    /**
     * flag - 逻辑删除标记
     */
    public final static String FLAG = "flag";

    /**
     * version - 乐观锁
     */
    public final static String VERSION = "version";

}
