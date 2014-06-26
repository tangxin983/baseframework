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
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<div class="form-group">
				流程名称：
			</div>
			<div class="form-group">
				<select name="s_processDefinitionId" class="form-control">
					<option value="" ${param.s_processDefinitionId eq "" ? "selected" : ""}>全部</option>
					<c:forEach items="${processDefinitionList}" var="processDefinition">
						<option value="${processDefinition.id}" ${param.s_processDefinitionId eq processDefinition.id ? "selected" : ""}>
							${processDefinition.name}(版本:${processDefinition.version})
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				单据号：
			</div>
			<div class="form-group">
				<input name="s_businessKey" value="${param.s_businessKey}" class="form-control"/>
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
		</form>
	</nav>
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
							<th>单据号</th>
							<th>流程名称</th>
							<th>流程版本</th>
							<th>发起人</th>
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
									    ${instance.businessKey}
									</a>
								</td>
								<td>${def.name}</td>
								<td>${def.version}</td>
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
