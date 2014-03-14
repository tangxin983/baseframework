package com.tx.framework.web.manage.service.operateflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.web.common.persistence.entity.OperateFlow;
import com.tx.framework.web.common.service.BaseService;
import com.tx.framework.web.dao.operateflow.OperateFlowDao;



@Service
@Transactional
public class OperateFlowService extends BaseService<OperateFlow> {

	private OperateFlowDao operateFlowDao;

	@Autowired
	public void setOperateFlowDao(OperateFlowDao operateFlowDao) {
		super.setDao(operateFlowDao);
		this.operateFlowDao = operateFlowDao;
	}

	 
}
