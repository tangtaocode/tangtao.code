package net.risesoft.approve.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/eform")
public class ShowEformController {
	@Value("${showEformUrl}")
	private String showEformUrl;

	
	@RequestMapping(value = "/show", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> certShow() {
	
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("showEformUrl", showEformUrl);
		return map;
	}	
}
