<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<tags:message content="${message}" />
	
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">待办列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${ctxModule}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>假种</th>
							<th>申请人</th>
							<th>申请时间</th>
							<th>请假开始</th>
							<th>请假结束</th>
							<th>当前节点</th>
							<th>流程状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="leave">
							<tr>
								<td>${leave.leaveType}</td>
								<td>${leave.applyUser}</td>
								<td><fmt:formatDate value="${leave.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${leave.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${leave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>
									<a target="_blank" href="${ctx}/diagram-viewer/index.html?processDefinitionId=${leave.processInstance.processDefinitionId}&processInstanceId=${leave.processInstance.id}">${leave.task.name}
								</td>
								<td>${leave.processInstance.suspended ? "挂起" : "正常"}</td>
								<td>
									<c:if test="${empty leave.task.assignee}">
										<a href="${ctxModule}/task/claim/${leave.task.id}" class="btn btn-primary">
											<span class="glyphicon glyphicon-info-sign"></span> 签收
										</a>
									</c:if>
									<c:if test="${not empty leave.task.assignee}">
										<a href="${ctxModule}/handle/${leave.id}" class="btn btn-primary"> 
									    	<span class="glyphicon glyphicon-edit"></span> 办理
										</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			<tags:pagination page="${page}" />
		</div>
	</div>
</body>
</html>
