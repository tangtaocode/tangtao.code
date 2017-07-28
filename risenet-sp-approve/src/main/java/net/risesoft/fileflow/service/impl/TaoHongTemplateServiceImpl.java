package net.risesoft.fileflow.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.risesoft.common.util.GuidUtil;
import net.risesoft.fileflow.entity.jpa.TaoHongTemplate;
import net.risesoft.fileflow.repository.jpa.TaoHongTemplateRepository;
import net.risesoft.fileflow.service.TaoHongTemplateService;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

@Service(value = "taoHongTemplateService")
public class TaoHongTemplateServiceImpl implements TaoHongTemplateService {

	@Autowired
	private TaoHongTemplateRepository taoHongTemplateRepository;

	@Override
	public List<TaoHongTemplate> findByTenantId(String tenantId) {
		return taoHongTemplateRepository.findByTenantId();
	}

	@Override
	public TaoHongTemplate findOne(String id) {
		return taoHongTemplateRepository.findOne(id);
	}

	@Override
	public TaoHongTemplate saveOrUpdate(TaoHongTemplate t) {
		String tenantId = ThreadLocalHolder.getTenantId();
		String userId = ThreadLocalHolder.getPerson().getID();
		String bureau_name=RisesoftUtil.getOrgUnitManager().getOrgUnit(t.getBureau_guid()).getName();
		try {
			if (StringUtils.isNotEmpty(t.getTemplate_guid())) {
				TaoHongTemplate taoHongTemplate = taoHongTemplateRepository.findOne(t.getTemplate_guid());
				taoHongTemplate.setBureau_guid(t.getBureau_guid());
				taoHongTemplate.setBureau_name(bureau_name);
				taoHongTemplate.setUserId(userId);
				taoHongTemplate.setTemplate_type(t.getTemplate_type());
				taoHongTemplate.setUploadTime(new Date());
				if(StringUtils.isNotEmpty(t.getTemplate_fileName())){
					taoHongTemplate.setTemplate_content(t.getTemplate_content());
					taoHongTemplate.setTemplate_fileName(t.getTemplate_fileName());
				}
				return taoHongTemplateRepository.save(taoHongTemplate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TaoHongTemplate newT = new TaoHongTemplate();
		newT.setTemplate_guid(GuidUtil.genGuid());
		newT.setTenantId(tenantId);
		newT.setBureau_guid(t.getBureau_guid());
		newT.setBureau_name(bureau_name);
		newT.setUserId(userId);
		newT.setTemplate_type(t.getTemplate_type());
		newT.setUploadTime(new Date());
		if(StringUtils.isNotEmpty(t.getTemplate_fileName())){
			newT.setTemplate_content(t.getTemplate_content());
			newT.setTemplate_fileName(t.getTemplate_fileName());
		}

		Integer index = taoHongTemplateRepository.getMaxTabIndex();
		if (index == null) {
			newT.setTabIndex(0);
		} else {
			newT.setTabIndex(index + 1);
		}

		return taoHongTemplateRepository.save(newT);
	}

	@Override
	public void removeTaoHongTemplate(String[] template_guids) {
		for (String template_guid : template_guids) {
			taoHongTemplateRepository.delete(template_guid);
		}
	}

	@Override
	public List<TaoHongTemplate> findByBureau_guid(String bureau_guid) {
		return taoHongTemplateRepository.findByBureau_guid(bureau_guid);
	}

	@Override
	public void removeTaoHongTemplate(String id) {
		taoHongTemplateRepository.delete(id);
	}

}
