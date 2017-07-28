package net.risesoft.fileflow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.fileflow.entity.jpa.RejectReason;
import net.risesoft.fileflow.service.RejectReasonService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sp/rejectReason")
public class SpRejectReasonController {

	@Resource(name="rejectReasonService")
	private RejectReasonService rejectReasonService;
	
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate")
	public Map<String,Object> saveOrUpdate(RejectReason rejectReason){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			rejectReasonService.save(rejectReason);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
}
