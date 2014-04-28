<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta name="decorator" content="default1" />
<title>用户管理</title>
<script type="text/javascript">
	var id;//当前选中行id
	$(document).ready(function() {
		query();
		// 点击行
		$("#list-content").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				id = $(this).find("[name='clickId']").val();
				if (id) {
					viewFormNew("${ctx}/${module}/get/" + id);
					$("#editBtn").removeClass("disabled");
				}
			}
		});
	});

	function query() {
		ajaxPagination("${ctx}/${templateUrl}", {
			data : "search_userName=" + $("#search_userName").val()
		});
	}

	function create() {
		createFormNew("${ctx}/${module}/createUser");
		$("#createDiv").show();
		$("#updateDiv").hide();
		$("#updateLoginName").prop('disabled', false);
		$('.selectpicker').selectpicker('val', '');
	}

	function edit() {
		createFormNew("${ctx}/${module}/updateUser"); 
		$("#createDiv").hide();
		$("#updateDiv").show();
		$("#updateLoginName").prop('disabled', true);
		$.get("${ctx}/${module}/get/" + id, function(resp){
			$('#inputForm').populate(resp);
			$('.selectpicker').selectpicker('val', resp.roleIds.split(","));
		});
	}

	function del() {
		$("#user-view-form").submit();
	}
	
	function save(){
		$("#inputForm").submit();
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

		<div class="row">
			<div class="col-md-7">
				<div class="panel panel-default">
					<div class="panel-heading">用户列表</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_userName" class="form-control"
									placeholder="员工姓名">
							</div>
							<a onclick="query()" class="btn btn-info" id="search_btn"> <span
								class="glyphicon glyphicon-search"></span> 查询
							</a>
							<a onclick="del()" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove"></span> 删除
							</a>
						</form>
						<br />
						<form id="user-view-form" action="${ctx}/${module}/delete"
							valid="false" method="post">
							<table id="contentTable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>登录账号</th>
										<th>员工姓名</th>
										<th>所属角色</th>
										<th>创建时间</th>
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
			<div class="col-md-5">
				<div class="panel panel-default">
					<div class="panel-heading">编辑</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">登录帐号:</label> <label
									class="control-label" id="loginName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">员工姓名:</label> <label
									class="control-label" id="userName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">所属角色:</label> <label
									class="control-label" id="roleNames"></label>
							</div>
							<!-- 
							<div class="form-group">
								<label class="col-sm-3 control-label">状态:</label> <label
									class="control-label" id="userState"></label>
							</div>
							 -->
							<div class="form-group">
								<label class="col-sm-3 control-label">创建时间:</label> <label
									class="control-label" id="registerDate"></label>
							</div>
						</form>
						<form action="${ctx}/${module}/createUser" method="post"
							id="inputForm" class="form-horizontal" style="display: none">
							<input type="hidden" name="id" />
							<div class="form-group">
								<label class="col-sm-3 control-label">登录帐号:</label>
								<div class="col-sm-9">
									<input type="text" name="loginName" id="updateLoginName"
										class="form-control required"
										remote="${ctx}/user/checkLoginName" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">员工姓名:</label>
								<div class="col-sm-9">
									<input type="text" name="userName" class="form-control required" />
								</div>
							</div>
							<div id="createDiv">
								<div class="form-group">
									<label class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-9">
										<input type="password" id="plainPassword" name="plainPassword"
											class="form-control required" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">确认密码:</label>
									<div class="col-sm-9">
										<input type="password" id="confirmPassword"
											name="confirmPassword" class="form-control required"
											equalTo="#plainPassword" />
									</div>
								</div>
							</div>
							<div id="updateDiv">
								<div class="form-group">
									<label class="col-sm-3 control-label">重置密码:</label>
									<div class="col-sm-9">
										<input type="password" name="updatePlainPassword" placeholder="不变请留空"
											class="form-control" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">所属角色:</label>
								<div class="col-sm-9">
									<select name="roleIds" class="selectpicker form-control"
										title="请选择角色" multiple>
										<c:forEach items="${roles}" var="role">
											<option value="${role.id}">${role.remark}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a onclick="create()" class="btn btn-primary"> <span
							class="glyphicon glyphicon-plus"></span> 新增
						</a> <a id="editBtn" onclick="edit()" class="btn btn-default disabled">
							<span class="glyphicon glyphicon-edit"></span> 修改
						</a>
					</div>
					<div id="createfooter" class="panel-footer" style="display: none">
						<a onclick="save()" class="btn btn-success"> <span
							class="glyphicon glyphicon-ok"></span> 保存
						</a> <a onclick="returnToViewForm()" class="btn btn-default"> <span
							class="glyphicon glyphicon-backward"></span> 返回
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
