package net.risesoft.fileflow.service.impl;

import java.util.List;

import net.risesoft.common.util.GuidUtil;
import net.risesoft.common.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.TaoHongTemplateType;
import net.risesoft.fileflow.repository.jpa.TaoHongTemplateTypeRepository;
import net.risesoft.fileflow.service.TaoHongTemplateTypeService;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "taoHongTemplateTypeService")
public class TaoHongTemplateTypeServiceImpl implements TaoHongTemplateTypeService {

	@Autowired
	private TaoHongTemplateTypeRepository taoHongTemplateTypeRepository;

	@Override
	public List<TaoHongTemplateType> findByTenantId() {
		return taoHongTemplateTypeRepository.findByTenantId();
	}

	@Override
	public void removeTaoHongTemplateType(String[] ids) {
		for (String id : ids) {
			taoHongTemplateTypeRepository.delete(id);
		}
	}

	@Override
	public TaoHongTemplateType findOne(String id) {
		return taoHongTemplateTypeRepository.findOne(id);
	}

	@Override
	public TaoHongTemplateType saveOrUpdate(TaoHongTemplateType t) {
		if (StringUtils.isNotEmpty(t.getId())) {
			TaoHongTemplateType tht = taoHongTemplateTypeRepository.findOne(t.getId());
			tht.setTypeName(t.getTypeName());
			return taoHongTemplateTypeRepository.save(tht);
		}

		TaoHongTemplateType thtNew = new TaoHongTemplateType();
		thtNew.setId(GuidUtil.genGuid());
		thtNew.setTenantId(ThreadLocalHolder.getTenantId());
		thtNew.setTypeName(t.getTypeName());

		Integer tabIndex = taoHongTemplateTypeRepository.getMaxTabIndex();
		if (tabIndex == null) {
			thtNew.setTabIndex(0);
		} else {
			thtNew.setTabIndex(tabIndex + 1);
		}
		return taoHongTemplateTypeRepository.save(thtNew);
	}

	@Override
	public void saveOrder(String[] idAndTabIndexs) {
		for(String idAndTabIndex:idAndTabIndexs){
			String id=idAndTabIndex.split(SysVariables.COLON)[0];
			Integer tabIndex=Integer.parseInt(idAndTabIndex.split(SysVariables.COLON)[1]);
			taoHongTemplateTypeRepository.update4Order(tabIndex, id);
		}
	}

}
