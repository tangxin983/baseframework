<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程列表</title>
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
			<div class="text-muted bootstrap-admin-box-title">流程列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${ctxModule}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>键值</th>
							<th>版本</th>
							<th>XML</th>
							<th>图片</th>
							<th>部署时间</th>
							<th>是否挂起</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="object">
							<c:set var="process" value="${object[0]}" />
							<c:set var="deployment" value="${object[1]}" />
							<tr>
								<td>${process.name}</td>
								<td>${process.key}</td>
								<td>${process.version}</td>
								<td><a target="_blank" href='${ctx}/workflow/resource/read?processId=${process.id}&type=xml'>${process.resourceName}</a></td>
								<td><a target="_blank" href='${ctx}/workflow/resource/read?processId=${process.id}&type=image'>${process.diagramResourceName}</a></td>
								<td><fmt:formatDate value="${deployment.deploymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${process.suspended} |
									<c:if test="${process.suspended}">
										<a href="${ctx}/workflow/process/active/${process.id}">激活</a>
									</c:if>
									<c:if test="${!process.suspended}">
										<a href="${ctx}/workflow/process/suspend/${process.id}">挂起</a>
									</c:if>
								</td>
								<td>
			                        <a href='${ctx}/workflow/process/delete/${process.deploymentId}'>删除</a>
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
