package com.tx.framework.web.webservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.tx.framework.common.util.CollectionUtils;
import com.tx.framework.web.common.persistence.entity.Attachment;
import com.tx.framework.web.common.utils.WebServiceUtil;
import com.tx.framework.web.dao.attachment.AttachmentDao;

@Service
@Transactional
public class AttachmentRestService {

	@Autowired
	private AttachmentDao attachmentDao;
	
 
	/**
	 * 获取所有附件URL
	 * @return
	 */
	public Map<String, Object> getUrls() {
		List<Attachment> attachments = attachmentDao.findAll();
		return WebServiceUtil.successMap(CollectionUtils.extractToList(attachments, "path", true));
	}
	 
}
