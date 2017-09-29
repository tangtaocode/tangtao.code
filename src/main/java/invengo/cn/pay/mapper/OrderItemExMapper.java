package invengo.cn.pay.mapper;

import java.util.List;

import invengo.cn.pay.model.entity.OrderItem;

public interface OrderItemExMapper extends OrderItemMapper
{
    List<OrderItem> loadByOrderNo(OrderItem record);
}
