<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>栏目管理</title>
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#treeTable").treeTable({
			expandLevel : 3
		});
	});
</script>
</head>
<body>
	<tags:message content="${message}" />

	<!-- search form -->
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<div class="form-group">
				<input name="s_siteId" value="${param.s_siteId}"
					class="form-control" placeholder="siteId">
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<shiro:hasPermission name="cms:category:create">
			</shiro:hasPermission>
			<a href="${ctxModule}/create" class="btn btn-primary"> <span
				class="glyphicon glyphicon-plus"></span> 添加栏目
			</a>
		</form>
	</nav>

	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">栏目列表</div>
		</div>
		<div class="panel-body">
			<table id="treeTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>栏目名称</th>
						<th>栏目模型</th>
						<th>排序</th>
						<th>导航菜单</th>
						<th>栏目列表</th>
						<th>展现方式</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${entitys}" var="category">
						<tr id="${category.id}"
							pId="${category.parentId ne '1' ? category.parentId : '0'}">
							<td><a href="${ctxModule}/update/${category.id}" title="修改">
									${category.name} </a></td>
							<td>${fns:getDictLabel('cms_module', category.module, '公共模型')}</td>
							<td>${category.sort}</td>
							<td>${fns:getDictLabel('show_hide', category.inMenu, '隐藏')}</td>
							<td>${fns:getDictLabel('show_hide', category.inList, '隐藏')}</td>
							<td>${fns:getDictLabel('cms_show_mode',category.showModes, '默认展现方式')}</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
