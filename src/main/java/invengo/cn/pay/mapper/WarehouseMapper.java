package invengo.cn.pay.mapper;

import invengo.cn.pay.model.entity.Warehouse;

public interface WarehouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    Warehouse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);
}