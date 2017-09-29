package invengo.cn.pay.mapper;

import invengo.cn.pay.model.entity.Equipment;

public interface EquipmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Equipment record);

    int insertSelective(Equipment record);

    Equipment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Equipment record);

    int updateByPrimaryKey(Equipment record);
    
    Equipment selectByParams(Equipment record);
}