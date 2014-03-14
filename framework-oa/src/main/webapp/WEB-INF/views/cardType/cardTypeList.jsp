<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>卡类型管理</title>
<script type="text/javascript">
	var id;
	$(document).ready(function() {
		$("#input_cardtypename").rules("add", {
			remote : {
				url : "${ctx}/${module}/checkName",
				type : "POST",
				data : {
					cardtypename : function() {
						return $("#input_cardtypename").val();
					},
					cardtypeid : function() {
						return $("#cardtypeid").val();
					}
				}
			}
		});
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
			data : "search_name=" + $("#search_name").val()
		});
	}

	function create() {
		createFormNew("${ctx}/${module}/create");
		$.get("${ctx}/${module}/getUpCard", function(resp) {
			$('#input_upcardid').html(resp);
		});
	}

	function edit() {
		createFormNew("${ctx}/${module}/update");
		$.ajax({
			url : "${ctx}/cardType/getUpCard?id=" + id,
			async : false,
			success : function(resp) {
				$('#input_upcardid').html(resp);
			}
		});
		$.get("${ctx}/${module}/get/" + id, function(resp) {
			$('#inputForm').populate(resp);
		});
	}

	function del() {
		$("#user-view-form").submit();
	}

	function save() {
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
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 卡类型列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_name" class="form-control"
									placeholder="卡类型名称">
							</div>
							<a onclick="query()" class="btn btn-info"> 
								<span class="glyphicon glyphicon-search"></span> 查询
							</a> 
							<a onclick="del()" class="btn btn-danger"> 
								<span class="glyphicon glyphicon-remove"></span> 删除
							</a>
						</form>
						<br />
						<form id="user-view-form" action="${ctx}/cardType/delete"
							valid="false" method="post">
							<table id="contentTable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>卡类型名称</th>
										<th>默认折扣率</th>
										<th>办卡押金</th>
										<th>是否强制打折</th>
										<th>是否必输密码</th>
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
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 编辑卡类型
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">卡类型名称:</label> <label
									class="control-label" id="cardtypename"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">默认折扣率:</label> <label
									class="control-label" id="discount"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">办卡押金:</label> <label
									class="control-label" id="precharge"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">是否强制打折:</label> <label
									class="control-label" id="forcediscountchs"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">是否必输密码:</label> <label
									class="control-label" id="needpwdchs"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注:</label> <label
									class="control-label" id="memo"></label>
							</div>
						</form>
						<form action="${ctx}/cardType/create" method="post" id="inputForm"
							class="form-horizontal" style="display: none">
							<input type="hidden" name="id" id="cardtypeid" />
							<div class="form-group">
								<label for="cardtypename" class="col-sm-3 control-label">卡类型名称:</label>
								<div class="col-sm-9">
									<input type="text" name="cardtypename" id="input_cardtypename"
										class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label for="discount" class="col-sm-3 control-label">默认折扣率:</label>
								<div class="col-sm-9 input-group">
									<input type="text" name="discount" value="100"
										class="form-control required percent" /> <span
										class="input-group-addon">%</span>
								</div>
							</div>
							<div class="form-group">
								<label for="precharge" class="col-sm-3 control-label">办卡押金:</label>
								<div class="col-sm-9 input-group">
									<span class="input-group-addon">¥</span> <input type="text"
										name="precharge" value="0" class="form-control digits" /> <span
										class="input-group-addon">元</span>
								</div>
							</div>
							<div class="form-group">
								<label for="upcardid" class="col-sm-3 control-label">下一级卡类型:</label>
								<div class="col-sm-9">
									<select id="input_upcardid" name="upcardid"
										class="form-control">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="forcediscount" class="col-sm-3 control-label">是否强制打折:</label>
								<div class="col-sm-9">
									<select name="forcediscount" class="form-control required">
										<option value="0">不打折</option>
										<option value="1">打折</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="needpwd" class="col-sm-3 control-label">是否必输密码:</label>
								<div class="col-sm-9">
									<select name="needpwd" class="form-control required">
										<option value="1">必须</option>
										<option value="0">可不输</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="memo" class="col-sm-3 control-label">备注:</label>
								<div class="col-sm-9">
									<textarea name="memo" class="form-control"></textarea>
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
