package com.fairychar.feign.pojo.dto;


import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户表(Customer)表实体类
 *
 * @author chiyo
 * @since 2021-05-27 20:56:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@SuppressWarnings("serial")
@ApiModel("CustomerDTO")
public class CustomerDTO implements Serializable {

    @ApiModelProperty("")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("邮箱地址")
    private String email;
    @ApiModelProperty("")
    private String password;
    @ApiModelProperty("逻辑删除标记")
    private Integer flag;
    @ApiModelProperty("乐观锁")
    private Integer version;


}
