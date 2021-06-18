package com.fairychar.caching.service.structure;


import com.fairychar.caching.entity.Customer;
import com.fairychar.caching.pojo.dto.CustomerDTO;
import com.fairychar.caching.pojo.query.CustomerQuery;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户表(Customer)表数据库实体转换器
 *
 * @author chiyo
 * @since 2021-05-28 15:47:51
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerStructure {
    /**
     * entities转dtos
     *
     * @param entities {@link Customer}
     * @return dto对象 {@link CustomerDTO}
     */
    List<CustomerDTO> entitiesToDtos(List<Customer> entities);

    /**
     * entity转dto
     *
     * @param entity {@link Customer}
     * @return dto对象 {@link CustomerDTO}
     */
    CustomerDTO entityToDto(Customer entity);

    /**
     * queries转entities
     *
     * @param queries {@link CustomerQuery}
     * @return entity对象 {@link Customer}
     */
    List<Customer> queriesToEntities(List<CustomerQuery> queries);

    /**
     * query转entity
     *
     * @param query {@link CustomerQuery}
     * @return entity对象 {@link Customer}
     */
    Customer queryToEntity(CustomerQuery query);

}
