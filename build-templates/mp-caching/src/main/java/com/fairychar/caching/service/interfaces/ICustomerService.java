package com.fairychar.caching.service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fairychar.caching.entity.Customer;
import com.fairychar.caching.pojo.dto.CustomerDTO;
import com.fairychar.caching.pojo.query.CustomerQuery;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;

import com.fairychar.caching.service.CustomerService;
import org.springframework.cache.annotation.*;

/**
 * 用户表(Customer)表服务接口
 *
 * @author chiyo
 * @since 2021-05-28 16:59:11
 */
@CacheConfig(cacheNames = CustomerService.PREFIX)
public interface ICustomerService extends IService<Customer> {
    /**
     * 条件匹配查询Customer所有数据
     *
     * @param customerQuery {@link CustomerQuery}查询条件
     * @return 查询结果 {@link CustomerDTO}
     */
    List<CustomerDTO> queryAll(CustomerQuery customerQuery);

    /**
     * 条件匹配分页查询Customer所有数据
     *
     * @param page          分页参数
     * @param customerQuery {@link CustomerQuery}查询条件
     * @return 查询结果 {@link CustomerDTO}
     */
    IPage<CustomerDTO> pageAll(Page page, CustomerQuery customerQuery);

    /**
     * 插入
     *
     * @param customerQuery {@link CustomerQuery}插入query
     * @return 是否成功
     */
    @Caching(put = {
            @CachePut(key ="#customerQuery.id", unless = "#result!=null", condition = "#customerQuery!=null && #customerQuery.id!=null")
    })
    CustomerDTO save(CustomerQuery customerQuery);

    /**
     * 更新
     *
     * @param customerQuery {@link CustomerQuery}更新query
     * @return 是否成功
     */
    @Caching(put = {
            @CachePut(key = "#customerQuery.id", unless = "#result!=null", condition = "#customerQuery!=null && #customerQuery.id!=null")
    })
    CustomerDTO updateById(CustomerQuery customerQuery);

    /**
     * 分页查询(全等匹配)
     *
     * @param page          分页对象
     * @param customerQuery {@link CustomerQuery}查询条件
     * @return 查询结果 {@link CustomerDTO}
     */
    IPage<CustomerDTO> page(Page page, CustomerQuery customerQuery);

    /**
     * 根据id查询一个对象
     *
     * @param id id
     * @return 查询结果 {@link CustomerDTO}
     */
    @Caching(cacheable = {
            @Cacheable(key = "#id", unless = "#result==null", condition = "#id!=null")
    })
    CustomerDTO findOne(Serializable id);

    /**
     * 条件查询总数
     *
     * @param customerQuery {@link CustomerQuery}查询条件
     * @return 总数
     */
    int count(CustomerQuery customerQuery);

    /**
     * 根据id删除一条数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Caching(evict = {
            @CacheEvict(key = "#id", condition = "#id!=null")
    })
    boolean removeById(Serializable id);

    /**
     * 批量新增
     *
     * @param batch 新增数据
     * @return 是否成功
     */
    boolean saveBatch(List<CustomerQuery> batch);

}
