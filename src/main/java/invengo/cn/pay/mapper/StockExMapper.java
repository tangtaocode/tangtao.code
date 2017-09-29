package invengo.cn.pay.mapper;



import java.util.List;
import java.util.Map;

import invengo.cn.pay.model.StockDTO;
import invengo.cn.pay.model.entity.Stock;
import invengo.cn.pay.model.entity.StockEx;

public interface StockExMapper extends StockMapper {

  List<StockEx> selectByTags(StockDTO stockDTO);
  
  StockEx selectByParams(Stock stockRecord);

  int batchUpdateByParams(Map<String, Object> param);
  
}