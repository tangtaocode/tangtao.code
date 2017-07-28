package net.risesoft.api;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@WebService
@Path("/approveManager")
public interface ApproveManager {
	
	/**
	 * 待办列表
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	@POST
	@Path(value="/todoListByUserId")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> todoListByUserId(
			@WebParam(name="userId") @FormParam("userId") String userId);
	
	/**
	 * 业务量统计
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	@POST
	@Path(value="/lineChart")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> lineChart(
			@WebParam(name="userId") @FormParam("userId") String userId,
			@WebParam(name="tenantId") @FormParam("tenantId") String tenantId);
	
}
