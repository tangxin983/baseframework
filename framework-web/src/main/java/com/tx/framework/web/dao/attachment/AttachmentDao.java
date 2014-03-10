package com.tx.framework.web.dao.attachment;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.Attachment;



@MyBatisRepository
public interface AttachmentDao extends BaseDao<Attachment>{
  
	void deleteByServiceId(String serviceId);
}
