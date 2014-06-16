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
			$("#workflowForm").submit();
		}
	</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">请假流程办理[${entity.processStatus}]</div>
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
				<div class="form-group">
					<label class="col-md-2 control-label">当前节点：</label>
					<div class="col-md-6">
						<p class="form-control-static">${entity.processStatus}</p>
					</div>
				</div>
			</form>
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
							<a href="${ctxModule}/task" class="btn btn-default">返 回</a>
						</div>
					</div>
				</form>
			</c:if>
		</div>
	</div>
	<tags:workflowhistory entity="${entity}" />
</body>
</html>
