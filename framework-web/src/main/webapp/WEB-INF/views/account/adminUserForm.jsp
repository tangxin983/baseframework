<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myfn" uri="/myfn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>编辑档案</title>
<script>
	$(document).ready(
			function() {
				$("#selectAll").click(
						function() {
							var childrens = $(this).parents("table").find(
									"tbody input[type='checkbox']");
							childrens.prop("checked", $(this).is(":checked"));
						});
			});
</script>
</head>

<body>
	<div class="data-content">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-user"></span> 编辑档案
				</h3>
			</div>
			<form action="${ctx}/user/update" method="post" id="inputForm">
				<input type="hidden" name="id" value="${user.id}"/>
				<div class="panel-body">
					<div class="form-group">
						<div class="row">
							<div class="col-lg-6">
								<label>登录帐号:</label> 
								<input type="text" value="${user.loginName}" class="form-control" disabled=""/>
							</div>
							<div class="col-lg-6">
								<label for="name">员工姓名:</label> 
								<input type="text" id="name" name="name" value="${user.name}" class="form-control required" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-lg-6">
								<label for="plainPassword">密码:</label> 
								<input type="password" id="plainPassword" name="plainPassword"
									class="form-control" placeholder="不变则留空"/>
							</div>
							<div class="col-lg-6">
								<label for="confirmPassword">确认密码:</label> <input
									type="password" id="confirmPassword" name="confirmPassword"
									class="form-control" equalTo="#plainPassword" />
							</div>
						</div>
					</div>
					<shiro:user>
						<div class="form-group">
							<div class="row">
								<div class="col-lg-12">
									<!-- 表格 开始 -->
									<div class="panel panel-default">

										<div class="panel-heading">
											<h3 class="panel-title">
												<span class="glyphicon glyphicon-link"></span> 所属角色
											</h3>
										</div>

										<table class="table table-bordered table-hover">
											<thead>
												<tr>
													<th><input type="checkbox" id="selectAll" /></th>
													<th>角色名</th>
													<th>备注</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${roles}" var="role">
													<tr>
														<td><input type="checkbox" value="${role.id}" name="roleId" <c:if test="${myfn:contains(selectedRoles, role.id)}">checked</c:if> /></td>
														<td>${role.roleName}</td>
														<td>${role.remark}</td>
														<td>${role.state}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</shiro:user>
				</div>
				<div class="panel-footer">
					<button type="submit" class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span> 保存
					</button>
					<button type="button" class="btn btn-default"
						onclick="history.back();">
						<span class="glyphicon glyphicon-backward"></span> 返回
					</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
