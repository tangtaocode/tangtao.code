package invengo.cn.pay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import invengo.cn.pay.mapper.OrderDetailMapper;
import invengo.cn.pay.mapper.OrderItemExMapper;
import invengo.cn.pay.mapper.OrderMapper;
import invengo.cn.pay.mapper.StockExMapper;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.StockVO;
import invengo.cn.pay.model.entity.Order;
import invengo.cn.pay.model.entity.OrderDetail;
import invengo.cn.pay.model.entity.OrderItem;
import invengo.cn.pay.model.entity.Stock;
import invengo.cn.pay.model.entity.StockEx;
import invengo.cn.pay.service.IOrderService;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
	@Autowired
	public StockExMapper stockExMapper;
	@Autowired
	public OrderMapper orderMapper;
	@Autowired
	public OrderDetailMapper orderDetailMapper;
	@Autowired
	public OrderItemExMapper orderItemExMapper;

	@Override
	public void add(Map<String, Object> map) {
		StockVO stockVo = (StockVO) map.get("stockVo");
		List<String> epclist = (List<String>) map.get("epclist");
		Order order = new Order();
		order.setAmount(new BigDecimal(stockVo.getTotalAmt()));
		order.setFavTotal(stockVo.getTotalDisAmt());
		order.setCreateDate(new Date());
		order.setEquipment(map.get("device_info") + "");
		order.setOrderNo(map.get("out_trade_no") + "");
		order.setPaymentType(map.get("payment_type") + "");
		order.setStatus(DreamStatus.CREATED);
		order.setQuantity(Long.valueOf(epclist.size() + ""));
		order.setWarehouseCode(stockVo.getListStock().get(0).getWarehouseCode());
		orderMapper.insertSelective(order);
		OrderDetail detail = null;
		OrderItem item = null;
		for (StockEx stock : stockVo.getListStock()) {
			detail = new OrderDetail();
			detail.setCreateDate(new Date());
			detail.setGoodsName(stock.getName());
			detail.setGoodsNum(stock.getBarcode());
			detail.setOrderNo(order.getOrderNo());
			detail.setQuantity(Long.valueOf(stock.getStockItems().size() + ""));
			detail.setPrice(stock.getPrice01().doubleValue());
			orderDetailMapper.insertSelective(detail);
			for (Stock itemStock : stock.getStockItems()) {
				item = new OrderItem();
				item.setCreateDate(new Date());
				item.setOrderNo(order.getOrderNo());
				item.setEpc(itemStock.getEpc());
				orderItemExMapper.insertSelective(item);
			}
		}
	}

	@Override
	public void update(Map<String, Object> map) {
		// 更新订单状态
		Order record = new Order();
		record.setOrderNo(map.get("out_trade_no") + "");
		record.setStatus(map.get("status") + "");
		record.setLastUpdate(new Date());
		orderMapper.updateByUniqueSelective(record);
		// 支付成功更新销售状态
		if (DreamStatus.SUCCESS.equals(record.getStatus())) {
			OrderItem item = new OrderItem();
			item.setOrderNo(record.getOrderNo());
			List<OrderItem> listItem = orderItemExMapper.loadByOrderNo(item);
			List<String> epclist = new ArrayList<String>();
			for (OrderItem oitem : listItem) {
				epclist.add(oitem.getEpc());
			}
			map.put("status", DreamStatus.SALE);
			map.put("list", epclist);
			stockExMapper.batchUpdateByParams(map);
		}
	}

}
