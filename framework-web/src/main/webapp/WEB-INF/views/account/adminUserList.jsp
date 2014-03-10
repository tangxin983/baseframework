<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>员工管理</title>
	<script type="text/javascript">

		$(document).ready(function(){
			
			$("#selectAll").click(function(){
				var checkbox = $(this),
				children = checkbox.parents("table").find("tbody input[type='checkbox']");
				
				children.prop("checked",checkbox.is(":checked"));
			});
			$("#user-view-form").submit(function(e){
				
			    return $(this).find("input:checked").length > 0 && confirm("确定要删除吗？");
			});
		});
		
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
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-user"></span> 员工管理
				</h3>
			</div>
			<div class="panel-body">
				<form class="form-inline" action="#" valid="false" method="post">
					<div class="form-group">
						<input type="text" name="search_name" class="form-control"
							value="${param.search_name}" placeholder="员工姓名">
					</div>
					<button type="submit" class="btn btn-info" id="search_btn">
						<span class="glyphicon glyphicon-search"></span> 查询
				</form>
			</div>
			<form id="user-view-form" action="${ctx}/user/delete" valid="false" method="post">
				<table id="contentTable" class="table table-bordered table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<th>登录账号</th>
							<th>员工姓名</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="list-content">
						<c:if test="${empty users.result}">
							<tr>
								<td colspan="5" align="center">找不到要查询的记录</td>
							</tr>
						</c:if>
						<c:if test="${!empty users.result}">
							<c:forEach items="${users.result}" var="user">
								<tr>
									<td><input type="checkbox" name="ids" value="${user.id}"></td>
									<td>${user.loginName}</td>
									<td>${user.name}</td>
									<td><fmt:formatDate value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
									<td>
										<a href="${ctx}/user/update/${user.id}" class="btn btn-default"> <span
											class="glyphicon glyphicon-edit">修改</span>
										</a> 
										<a href="${ctx}/user/delete/${user.id}" class="btn btn-danger">
											<span class="glyphicon glyphicon-remove">删除</span>
										</a>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>

				<div class="panel-footer">
					<button type="submit" class="btn btn-danger">
						<span class="glyphicon glyphicon-remove"></span> 删除
					</button>

					<a href="${ctx}/user/create" class="btn btn-primary"> <span
						class="glyphicon glyphicon-plus"></span> 添加
					</a>

					<tags:tradPagination page="${users}" />
				</div>

			</form>
		</div>
	</div>
</body>
</html>
