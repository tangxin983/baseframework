<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>用户管理</title>
</head>

<body>

	<tags:message content="${message}" />

	<!-- button -->	
	<div class="btn-toolbar">
		<div class="btn-group">
			<a href="${ctxModule}/create" class="btn btn-primary">
				<span class="glyphicon glyphicon-plus"></span> 新增
			</a>
		</div>
	</div>
	<br/>
	
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">用户列表</div>
		</div>
		<div class="panel-body">
			<table id="treeTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>登录名</th>
						<th>姓名</th>
						<th>电话</th>
						<th>手机</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.result}" var="entity">
						<tr>
							<td>${entity.loginName}</td>
							<td>${entity.name}</td>
							<td>${entity.phone}</td>
							<td>${entity.mobile}</td>
							<td>
								<a href="${ctxModule}/update/${entity.id}" class="btn btn-default" title="修改">
									<span class="glyphicon glyphicon-edit"></span>
								</a>
							    <a href="${ctxModule}/delete/${entity.id}" class="btn btn-danger" title="删除"
							    	onclick="return confirmx('要删除该角色吗?', this.href)">
							    	<span class="glyphicon glyphicon-remove"></span>
							    </a> 
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<tags:pagination page="${page}" />
		</div>
	</div>

</body>
</html>
