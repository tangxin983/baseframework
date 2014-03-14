package com.tx.framework.web.dao.attachment;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import com.tx.framework.web.common.persistence.entity.Attachment;



@MyBatisDao
public interface AttachmentDao extends BaseDao<Attachment>{
  
	void deleteByServiceId(String serviceId);
}
