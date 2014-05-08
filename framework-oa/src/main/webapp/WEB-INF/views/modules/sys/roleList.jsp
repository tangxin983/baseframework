<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>角色管理</title>
</head>

<body>

	<tags:message content="${message}" />
 
	<!-- search form -->
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left">
			<div class="form-group">
				<input name="s_name" value="${param.s_name}" class="form-control" placeholder="角色名称">
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<a href="${ctxModule}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加角色
			</a>
		</form>
	</nav>

	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">角色列表</div>
		</div>
		<div class="panel-body">
			<table id="treeTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>角色名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.result}" var="entity">
						<tr>
							<td>${entity.name}</td>
							<td><a href="${ctxModule}/update/${entity.id}"
								class="btn btn-default" title="修改"> <span
									class="glyphicon glyphicon-edit"></span>
							</a> <a href="${ctxModule}/delete/${entity.id}"
								class="btn btn-danger" title="删除"
								onclick="return confirmx('要删除该角色吗?', this.href)"> <span
									class="glyphicon glyphicon-remove"></span>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<tags:pagination page="${page}" />
		</div>
	</div>

</body>
</html>
