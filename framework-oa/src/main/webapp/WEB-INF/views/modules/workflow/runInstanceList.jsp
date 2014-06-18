<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>运行流程</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<tags:message content="${message}" />

	<!-- search form -->
	<!-- 
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<div class="form-group">
				<input name="s_processInstanceId" value="${param.s_processInstanceId}" class="form-control" placeholder="processInstanceId">
			</div>
			<div class="form-group">
				<input name="s_startTime" value="${param.s_startTime}" class="form-control" placeholder="startTime">
			</div>
			<div class="form-group">
				<input name="s_endTime" value="${param.s_endTime}" class="form-control" placeholder="endTime">
			</div>
			<div class="form-group">
				<input name="s_leaveType" value="${param.s_leaveType}" class="form-control" placeholder="leaveType">
			</div>
			<div class="form-group">
				<input name="s_reason" value="${param.s_reason}" class="form-control" placeholder="reason">
			</div>
			<div class="form-group">
				<input name="s_applyTime" value="${param.s_applyTime}" class="form-control" placeholder="applyTime">
			</div>
			<div class="form-group">
				<input name="s_realityStartTime" value="${param.s_realityStartTime}" class="form-control" placeholder="realityStartTime">
			</div>
			<div class="form-group">
				<input name="s_realityEndTime" value="${param.s_realityEndTime}" class="form-control" placeholder="realityEndTime">
			</div>
			<div class="form-group">
				<input name="s_processStatus" value="${param.s_processStatus}" class="form-control" placeholder="processStatus">
			</div>
			<div class="form-group">
				<input name="s_createBy" value="${param.s_createBy}" class="form-control" placeholder="createBy">
			</div>
			<div class="form-group">
				<input name="s_createDate" value="${param.s_createDate}" class="form-control" placeholder="createDate">
			</div>
			<div class="form-group">
				<input name="s_updateBy" value="${param.s_updateBy}" class="form-control" placeholder="updateBy">
			</div>
			<div class="form-group">
				<input name="s_updateDate" value="${param.s_updateDate}" class="form-control" placeholder="updateDate">
			</div>
			<div class="form-group">
				<input name="s_remarks" value="${param.s_remarks}" class="form-control" placeholder="remarks">
			</div>
			<div class="form-group">
				<input name="s_delFlag" value="${param.s_delFlag}" class="form-control" placeholder="delFlag">
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<a href="${ctxModule}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加请假
			</a>
			<shiro:hasPermission name="oa:leave:delete">
			<a onclick="multiDel()" class="btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span> 删除
			</a>
			</shiro:hasPermission>
		</form>
	</nav>
	 -->
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">运行流程</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>流程实例ID</th>
							<th>流程名称</th>
							<th>流程发起人</th>
							<th>当前节点</th>
							<th>当前处理人</th>
							<th>是否挂起</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="object">
							<c:set var="def" value="${object[0]}" />
							<c:set var="instance" value="${object[1]}" />
							<c:set var="hisInstance" value="${object[2]}" />
							<c:set var="task" value="${object[3]}" />
							<tr>
								<td>
									<a href="${ctx}/oa/${def.key}/detail/${instance.businessKey}"> 
									    ${instance.processInstanceId}
									</a>
								</td>
								<td>${def.name}</td>
								<td>${fns:getUserById(hisInstance.startUserId).name}</td>
								<td>
									<a target="_blank" href="${ctx}/diagram-viewer/index.html?processDefinitionId=${instance.processDefinitionId}&processInstanceId=${instance.id}">${task.name}
								</td>
								<td>${fns:getUserById(task.assignee).name}</td>
								<td>${instance.suspended}</td>
								<td>
									<c:if test="${instance.suspended}">
										<a href="${ctx}/workflow/instance/active/${instance.processInstanceId}">激活</a>
									</c:if>
									<c:if test="${!instance.suspended}">
										<a href="${ctx}/workflow/instance/suspend/${instance.processInstanceId}">挂起</a>
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
