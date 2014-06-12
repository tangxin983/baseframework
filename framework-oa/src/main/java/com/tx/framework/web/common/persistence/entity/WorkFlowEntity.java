package com.tx.framework.web.common.persistence.entity;

import javax.persistence.Column;

/**
 * 工作流业务实体需继承此类
 * 
 * @author tangx
 * 
 */
@SuppressWarnings("serial")
public class WorkFlowEntity extends BaseEntity {

	@Column(name = "process_instance_id")
	protected String processInstanceId;
	
	@Column(name = "process_status")
	private String processStatus;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	
}
