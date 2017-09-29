package invengo.cn.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import invengo.cn.pay.component.PayMsgSender;
import invengo.cn.pay.mapper.EquipmentMapper;
import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.MessageType;
import invengo.cn.pay.model.access.MemberAccess;
import invengo.cn.pay.model.entity.Equipment;
import invengo.cn.pay.service.IAccessMemberService;
import invengo.cn.pay.utils.BeanUtil;
import invengo.cn.pay.utils.MemberUtil;
@Service
public class AccessMemberServiceImpl implements IAccessMemberService
{
  private static Logger logger = Logger.getLogger(AccessMemberServiceImpl.class);
  @Autowired
  private PayMsgSender payMsgSender;
  @Autowired
  private EquipmentMapper equipmentMapper;
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public DreamResponse setMember(MemberAccess dto) {

    DreamResponse revalue = new DreamResponse();
    revalue.setStatus(DreamStatus.SUCCESS);
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("status", DreamStatus.SUCCESS);
      map.put("type", MessageType.SCAN_MEMBER);
      map.put("member", dto);
      revalue.setExtData(BeanUtil.objectToMap(dto));
      Equipment record = new Equipment();
      record.setWarehouseId(dto.getMerchantId());
      record = equipmentMapper.selectByParams(record);
      MemberUtil.add(dto);
      MemberAccess  membervo = new MemberAccess();
      BeanUtils.copyProperties(dto, membervo);
      membervo.setMobile("");//过滤掉电话号码
      map.put("member", membervo);
      String json = new Gson().toJson(map);
      logger.info("**********会员扫码成功消息"+json);
      payMsgSender.sendAndroid(record.getMac(),json);
    } catch (Exception e) {
      revalue.setStatus(DreamStatus.FAIL);
      e.printStackTrace();
    }
    return revalue;
  }

}
