package com.tx.framework.web.manage.service.operateflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tx.framework.web.common.base.BaseService;
import com.tx.framework.web.dao.operateflow.OperateFlowDao;
import com.tx.framework.web.entity.OperateFlow;



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
