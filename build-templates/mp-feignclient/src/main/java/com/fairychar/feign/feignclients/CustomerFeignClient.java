package com.fairychar.feign.feignclients;


import com.fairychar.bag.pojo.vo.HttpResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import com.fairychar.feign.pojo.dto.CustomerDTO;
import com.fairychar.feign.pojo.query.CustomerQuery;
import com.fairychar.feign.feignclients.fallback.CustomerFallbackFactory;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

/**
 * 用户表(Customer)表服务接口类
 *
 * @author chiyo
 * @since 2021-05-27 20:56:54
 */
@RequestMapping("customer")
@Api(tags = "用户表FeignClient")
@FeignClient(value = "mp-feignclient", fallbackFactory = CustomerFallbackFactory.class)
public interface CustomerFeignClient {

    @PostMapping("count")
    @ApiOperation(value = "条件查询匹配数据总数", notes = "string类型模糊匹配,非string为全等匹配")
    ResponseEntity<HttpResult<Integer>> count(@RequestBody CustomerQuery query);


    @PostMapping("match")
    @ApiOperation(value = "条件查询所有数据", notes = "string类型模糊匹配,非string为全等匹配")
    ResponseEntity<HttpResult<List<CustomerDTO>>> matchAll(@RequestBody CustomerQuery query);


    @PostMapping("pageMatch")
    @ApiOperation(value = "分页条件查询所有数据", notes = "string类型模糊匹配,非string为全等匹配")
    ResponseEntity<HttpResult<IPage<CustomerDTO>>> pageMatch(@RequestBody CustomerQuery query, @ApiParam("分页参数") Page page);

    /**
     * 分页查询所有数据(全等匹配)
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return 所有数据
     */
    @PostMapping("page")
    @ApiOperation(value = "分页查询所有数据", notes = "字段全等匹配方式查询")
    ResponseEntity<HttpResult<IPage<CustomerDTO>>> findAll(@RequestBody CustomerQuery query, @ApiParam("分页对象") Page page);

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过主键查询单条数据")
    ResponseEntity<HttpResult<CustomerDTO>> findOne(@PathVariable Serializable id);

    /**
     * 新增数据
     *
     * @param query 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "新增数据")
    ResponseEntity<HttpResult> insert(@RequestBody CustomerQuery query);

    /**
     * 批量新增数据
     *
     * @param queries 实体对象集合
     * @return 新增结果
     */
    @PostMapping("saveBatch")
    @ApiOperation(value = "批量新增数据")
    ResponseEntity<HttpResult> batchInsert(@RequestBody List<CustomerQuery> queries);

    /**
     * 修改数据
     *
     * @param query 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation(value = "修改数据")
    ResponseEntity<HttpResult> updateById(@RequestBody CustomerQuery query);

    /**
     * 删除数据
     *
     * @param id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除数据")
    ResponseEntity<HttpResult<Boolean>> delete(@PathVariable("id") Serializable id);
}


