<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>角色管理</title>
<script type="text/javascript">
	var id;//当前选中行id
	$(document).ready(function() {
		query();
	});

	function query() {
		ajaxPagination("${ctx}/${templateUrl}", {
			data : "search_remark=" + $("#search_remark").val()
		});
	}
	
	function del() {
		$("#user-view-form").submit();
	}
</script>
</head>

<body>
	<div class="data-content">

		<c:if test="${not empty message}">
			<div class="alert alert-success fade in">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<span class="glyphicon glyphicon-ok"></span> ${message}
			</div>
		</c:if>

		<div class="panel panel-default">
			<div class="panel-heading">角色列表</div>
			<div class="panel-body">
				<form class="form-inline" valid="false">
					<div class="form-group">
						<input type="text" id="search_remark" class="form-control" placeholder="角色名">
					</div>
					<a onclick="query()" class="btn btn-info" id="search_btn"> <span
						class="glyphicon glyphicon-search"></span> 查询
					</a> 
					<a href="${ctx}/role/create" class="btn btn-primary"> 
						<span class="glyphicon glyphicon-plus"></span> 新增
					</a>
					<a onclick="del()" class="btn btn-danger"> <span
						class="glyphicon glyphicon-remove"></span> 删除
					</a>
				</form>
				<br />
				<form id="user-view-form" action="${ctx}/${module}/delete"
					valid="false" method="post">
					<table id="contentTable" class="table table-bordered table-hover">
						<thead>
							<tr>
								<th><input type="checkbox" id="selectAll"></th>
								<th>角色中文名</th>
								<th>角色英文名</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="list-content" clickable="true">
						</tbody>
					</table>
				</form>
				<br />
				<div>
					<ul id='pagination' class='pull-right'></ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
