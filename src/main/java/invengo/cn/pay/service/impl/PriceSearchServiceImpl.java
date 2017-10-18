package invengo.cn.pay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import invengo.cn.pay.config.AccessConfig;
import invengo.cn.pay.mapper.EquipmentMapper;
import invengo.cn.pay.mapper.StockExMapper;
import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.StockDTO;
import invengo.cn.pay.model.StockVO;
import invengo.cn.pay.model.access.PriceResponse;
import invengo.cn.pay.model.access.ProductAccess;
import invengo.cn.pay.model.access.SkuAccess;
import invengo.cn.pay.model.entity.Equipment;
import invengo.cn.pay.model.entity.StockEx;
import invengo.cn.pay.service.IPriceSearchService;
import invengo.cn.pay.utils.HttpClientUtils;
import invengo.cn.pay.utils.StockUtil;
import invengo.cn.pay.utils.YZTRequestUtils;

@Service
@Transactional
public class PriceSearchServiceImpl implements IPriceSearchService {
	private static Logger logger = Logger.getLogger(PriceSearchServiceImpl.class);
	@Autowired
	public StockExMapper stockExMapper;
	@Autowired
	private AccessConfig accessConfig;
	@Autowired
	private EquipmentMapper equipmentMapper;

	@Override
	public Double getTotalAmount(CommonDTO dto) {
		List<StockEx> stocks = new ArrayList<StockEx>();
		StockDTO stockDTO = new StockDTO();
		stockDTO.setTags(dto.getEpcs());
		stockDTO.setStatus("In");
		stocks = stockExMapper.selectByTags(stockDTO);
		stocks = StockUtil.groupBySku(stocks);
		BigDecimal price = new BigDecimal("0");
		BigDecimal qty = null;
		BigDecimal price01 = null;
		for (StockEx stock : stocks) {
			qty = new BigDecimal(stock.getQty() == null ? "0" : stock.getQty().toString());
			price01 = stock.getPrice01();
			price = price.add(price01.multiply(qty));
		}
		return price.doubleValue();
	}

	@Override
	public StockVO getSKUPrice(CommonDTO dto) {
		List<StockEx> stocks = new ArrayList<StockEx>();
		List<StockEx> allstocks = new ArrayList<StockEx>();
		StockVO revalue = new StockVO();
		StockDTO stockDTO = new StockDTO();
		stockDTO.setTags(dto.getEpcs());
		// stockDTO.setStatus("In");
		allstocks = stockExMapper.selectByTags(stockDTO);
		revalue.setAllListStock(allstocks);
		stocks = StockUtil.groupBySku(allstocks);
		if (!accessConfig.isAccessPrice()) {
			BigDecimal price = new BigDecimal("0.00");
			String qty = "0";
			BigDecimal price01 = null;
			for (StockEx stock : stocks) {
				if (stock.getSaleQty() != null && stock.getSaleQty() > 0) {
					qty = String.valueOf(stock.getQty() - stock.getSaleQty());
				} else {
					qty = String.valueOf(stock.getQty());
				}
				price01 = stock.getPrice01();
				price = price.add(price01.multiply(new BigDecimal(qty)));
			}
			// revalue.setTotalAmt(price.doubleValue());
			revalue.setTotalAmt(0.01D);
			revalue.setTotalDisAmt(0.0);
		} else {// 定制化接口查询价格
			Map<String, String> searchmap = new HashMap<String, String>();
			Equipment record = new Equipment();
			record.setWarehouseId(stocks.get(0).getWarehouseId() + "");
			record = equipmentMapper.selectByParams(record);
			// searchmap.put("merchantId",record.getWarehouseId());
			searchmap.put("merchantId", "20");
			searchmap.put("terminalId", record.getId() + "");
			searchmap.put("userId", dto.getMemberId());
			List<SkuAccess> skus = new ArrayList<SkuAccess>();
			SkuAccess sku = null;
			for (StockEx stock : stocks) {
				sku = new SkuAccess();
				sku.setMpBarcode(stock.getBarcode());
				sku.setNum(stock.getQty() - stock.getSaleQty());
				skus.add(sku);
			}
			String skujson = new Gson().toJson(skus);
			searchmap.put("skus", skujson);
			searchmap = YZTRequestUtils.createParamsHelperMap(searchmap, accessConfig);
			String paramjson = new Gson().toJson(searchmap);
			logger.info("************价格入参************" + paramjson);
			String rejson = HttpClientUtils.doBodyPost(accessConfig.getAccessApiPriceSearch(), searchmap, "UTF-8",
					HttpClientUtils.CONTENT_TYPE_FORM);
			logger.info("************价格查询结果************" + rejson);
			// 显示第三方价格
			PriceResponse priceResult = new Gson().fromJson(rejson, PriceResponse.class);
			if (priceResult != null && "0".equals(priceResult.getCode())) {
				// revalue.setTotalAmt(priceResult.getData().getTotalAmt());
				revalue.setTotalAmt(0.01D);
				revalue.setTotalDisAmt(priceResult.getData().getTotalDisAmt());
				Map<String, ProductAccess> accessmap = new HashMap<String, ProductAccess>();
				for (ProductAccess pd : priceResult.getData().getProductList()) {
					accessmap.put(pd.getBarcode(), pd);
				}
				// 重置第三方价格
				ProductAccess temp = null;
				for (StockEx stock : stocks) {
					temp = accessmap.get(stock.getBarcode());
					if (temp != null) {
						stock.setPrice01(new BigDecimal(temp.getMpPrice() + ""));
						stock.setPrice02(new BigDecimal(temp.getMpDisAmt() + ""));
					}
				}
			}
		}
		revalue.setListStock(stocks);
		return revalue;
	}

}
