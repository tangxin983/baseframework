<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假流程办理</title>
	<meta name="decorator" content="default"/>
	<!-- 这里引入额外的css和js 
	<link rel="stylesheet" type="text/css" href="" />
	<script type="text/javascript" src=""></script>
	-->
	<script type="text/javascript">
		function auditPass(isPass) {
			$("#pass").val(isPass);
			// 流程提交提示
			bootbox.confirm("确定要提交吗？", function(result) {
		    	if (result) 
		    		$("#workflowForm").submit();
		    });
		}
	</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">单据详情</div>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" valid="false">
				<div class="form-group">
					<label class="col-md-2 control-label">请假类型：</label>
					<div class="col-md-6">
						<p class="form-control-static">${entity.leaveType}</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">开始时间：</label>
					<div class="col-md-6">
						<p class="form-control-static">
							<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">结束时间：</label>
					<div class="col-md-6">
						<p class="form-control-static">
							<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">请假原因：</label>
					<div class="col-md-6">
						<p class="form-control-static">${entity.reason}</p>
					</div>
				</div>
			</form>
		</div>
	</div>
	<c:if test="${!isView}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div class="text-muted bootstrap-admin-box-title">${entity.processStatus}</div>
			</div>
			<div class="panel-body">
				<!-- 部门领导审批 -->
				<c:if test="${entity.task.taskDefinitionKey eq 'deptLeaderAudit'}">
					<form id="workflowForm" action="${ctxModule}/deptLeaderAudit" method="post" class="form-horizontal">
						<input type="hidden" name="id" value="${entity.id}">
						<input type="hidden" name="pass" id="pass" value="${entity.pass}">
						<div class="form-group">
							<label class="col-md-2 control-label">审批备注：</label>
							<div class="col-md-6">
								<textarea name="auditRemark" rows="5" maxlength="200" class="form-control required">
								</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<input class="btn btn-primary" type="button" value="同意" onclick="auditPass(true);"/> 
								<input class="btn btn-warning" type="button" value="驳回" onclick="auditPass(false);"/>
								<input class="btn btn-default" type="button" value="返 回" onclick="history.go(-1)"/>
							</div>
						</div>
					</form>
				</c:if>
				<c:if test="${entity.task.taskDefinitionKey eq 'modifyApply'}">
					<form id="workflowForm" action="${ctxModule}/modifyApply" method="post" class="form-horizontal">
						<input type="hidden" name="id" value="${entity.id}">
						<input type="hidden" name="pass" id="pass" value="${entity.pass}">
						<div class="form-group">
							<label class="col-md-2 control-label">请假类型：</label>
							<div class="col-md-6">
								<input name="leaveType" maxlength="50" class="form-control required"
									value="${entity.leaveType}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">开始时间：</label>
							<div class="col-md-6">
								<input name="startTime" readonly="readonly" maxlength="20" class="form-control Wdate required"
									value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">结束时间：</label>
							<div class="col-md-6">
								<input name="endTime" readonly="readonly" maxlength="20" class="form-control Wdate required"
									value="<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">请假原因：</label>
							<div class="col-md-6">
								<textarea name="reason" rows="5" maxlength="255" class="form-control required">
								${entity.reason} 
								</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<input class="btn btn-primary" type="button" value="重新申请" onclick="auditPass(true);"/> 
								<input class="btn btn-warning" type="button" value="取消申请" onclick="auditPass(false);"/>
								<input class="btn btn-default" type="button" value="返 回" onclick="history.go(-1)"/>
							</div>
						</div>
					</form>
				</c:if>
				<!-- 人事审批 -->
				<c:if test="${entity.task.taskDefinitionKey eq 'hrAudit'}">
					<form id="workflowForm" action="${ctxModule}/hrAudit" method="post" class="form-horizontal">
						<input type="hidden" name="id" value="${entity.id}">
						<input type="hidden" name="pass" id="pass" value="${entity.pass}">
						<div class="form-group">
							<label class="col-md-2 control-label">审批备注：</label>
							<div class="col-md-6">
								<textarea name="auditRemark" rows="5" maxlength="200" class="form-control required">
								</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<input class="btn btn-primary" type="button" value="同意" onclick="auditPass(true);"/> 
								<input class="btn btn-warning" type="button" value="驳回" onclick="auditPass(false);"/>
								<input class="btn btn-default" type="button" value="返 回" onclick="history.go(-1)"/>
							</div>
						</div>
					</form>
				</c:if>
				<!-- 销假 -->
				<c:if test="${entity.task.taskDefinitionKey eq 'reportBack'}">
					<form id="workflowForm" action="${ctxModule}/reportBack" method="post" class="form-horizontal">
						<input type="hidden" name="id" value="${entity.id}">
						<div class="form-group">
							<label class="col-md-2 control-label">实际开始时间：</label>
							<div class="col-md-6">
								<input name="realityStartTime" readonly="readonly" maxlength="20" class="form-control Wdate required"
									value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">实际结束时间：</label>
							<div class="col-md-6">
								<input name="realityEndTime" readonly="readonly" maxlength="20" class="form-control Wdate required"
									value="<fmt:formatDate value="${entity.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-offset-2 col-md-10">
								<input class="btn btn-primary" type="submit" value="保存" />
								<input class="btn btn-default" type="button" value="返 回" onclick="history.go(-1)"/>
							</div>
						</div>
					</form>
				</c:if> 
			</div>
		</div>
	</c:if>
	<tags:workflowhistory entity="${entity}" />
</body>
</html>
