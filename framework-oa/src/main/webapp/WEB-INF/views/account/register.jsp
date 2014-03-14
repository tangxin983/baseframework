<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>员工注册</title>

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
					<span class="glyphicon glyphicon-user"></span> 员工注册
				</h3>
			</div>
			<form action="${ctx}/user/create" method="post" id="inputForm">
				<div class="panel-body">
					<div class="form-group">
						<div class="row">
							<div class="col-lg-6">
								<label for="loginName">登录帐号:</label> <input type="text"
									id="loginName" name="loginName" class="form-control required" 
									remote="${ctx}/register/checkLoginName"/>
							</div>
							<div class="col-lg-6">
								<label for="name">员工姓名:</label> <input type="text" id="name"
									name="name" class="form-control required" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-lg-6">
								<label for="plainPassword">密码:</label> <input type="password"
									id="plainPassword" name="plainPassword"
									class="form-control required" />
							</div>
							<div class="col-lg-6">
								<label for="confirmPassword">确认密码:</label> <input
									type="password" id="confirmPassword" name="confirmPassword"
									class="form-control required" equalTo="#plainPassword" />
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
														<td><input type="checkbox" value="${role.id}"
															name="roleId" /></td>
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
