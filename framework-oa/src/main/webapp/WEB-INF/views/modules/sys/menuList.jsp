<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default1" />
<title>菜单管理</title>
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#treeTable").treeTable({
			expandLevel : 2
		});
	});
</script>
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
			<div class="text-muted bootstrap-admin-box-title">菜单列表</div>
		</div>
		<div class="panel-body">
			<table id="treeTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>链接</th>
						<th>排序</th>
						<th>可见</th>
						<th>权限标识</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${entitys}" var="menu">
						<tr id="${menu.id}" pId="${menu.parentId ne '1' ? menu.parentId : '0'}">
							<td><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a
								href="${ctxModule}/update/${menu.id}">${menu.name}</a></td>
							<td>${menu.href}</td>
							<td>${menu.sort}</td>
							<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
							<td>${menu.permission}</td>
							<td>
								<a href="${ctxModule}/update/${menu.id}" class="btn btn-default" title="修改">
									<span class="glyphicon glyphicon-edit"></span>
								</a>
							    <a href="${ctxModule}/delete/${menu.id}" class="btn btn-danger" title="删除"
							    	onclick="return confirmx('要删除该菜单及所有子菜单项吗?', this.href)">
							    	<span class="glyphicon glyphicon-remove"></span>
							    </a> 
							    <a href="${ctxModule}/create?parentId=${menu.id}" class="btn btn-primary" title="添加子菜单"> 
							    	<span class="glyphicon glyphicon-plus"></span>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>
