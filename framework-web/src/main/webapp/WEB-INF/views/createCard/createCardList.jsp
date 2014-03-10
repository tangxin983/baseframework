<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>制卡</title>
<script type="text/javascript">
	var id;
	$(document).ready(function() {
		$("#pwd").rules( "add", {
			 required: function(element) {
				var ret = false; 
			 	$.ajax({
		    		url: "${ctx}/createCard/checkPwd?cardTypeId=" + $("#cardTypeId").val(),
		    		async: false,
		    		success: function(resp){
		    			if(resp == "true"){
		    				ret = true;
		    			}else{
		    				ret = false;
		    			}
		    		}
		    	});
			 	return ret;
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
	
	function query(){
		ajaxPagination("${ctx}/${templateUrl}", {data : "search_code=" + $("#search_code").val()});
	}
	
	function edit() {
		updateFormNew("${ctx}/createCard/get/" + id);
	}
	
	function create() {
		createFormNew("${ctx}/createCard/create");
	}
	
	function del() {
		$("#user-view-form").submit();
	}
	
	function save() {
		if($('#inputForm').is(':visible')){
			$("#inputForm").submit();
		}else if($('#updateForm').is(':visible')){
			$("#updateForm").submit();
		}
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
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 会员卡列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_code" class="form-control" placeholder="卡号">
							</div>
							<a onclick="query()" class="btn btn-info" id="search_btn">
								<span class="glyphicon glyphicon-search">查询</span> 
							</a>
							<a onclick="create()" class="btn btn-primary"> 
								<span class="glyphicon glyphicon-plus">制卡</span> 
							</a>
							<a onclick="del()" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove">删除</span> 
							</a>
						</form>
						<br/>
						<form id="user-view-form" action="${ctx}/createCard/delete" valid="false" method="post">
							<table id="contentTable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>卡类型</th>
										<th>卡号</th>
										<th>会员</th>
										<th>累计充值（元）</th>
										<th>余额（元）</th>
										<th>积分</th>
										<th>状态</th>
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
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 制卡
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">会员卡类型:</label>
								<label class="control-label" id="cardTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">卡号:</label>
								<label class="control-label" id="code"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">内部卡号:</label>
								<label class="control-label" id="innerCode"></label>
							</div>
						</form>
						<form action="${ctx}/createCard/update" method="post" id="updateForm"
							class="form-horizontal" style="display:none">
							<input type="hidden" name="id" value="" />
							<input type="hidden" name="cardTypeId" id="update_cardTypeId" value="" />
								<div class="form-group">
									<label class="col-sm-3 control-label">会员卡类型:</label>
									<label class="control-label" name="cardTypeName" id="cardTypeName"></label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">卡号:</label>
									<label class="control-label" name="code" id="code"></label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">内部卡号:</label>
									<label class="control-label" name="innerCode" id="innerCode"></label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-9">
										<input type="password" id="update_pwd" name="updatePwd" class="form-control cardPwd" placeholder="不变请留空"/>
									</div>
								</div>
						</form>
						<form action="${ctx}/createCard/create" method="post" id="inputForm"
							class="form-horizontal" style="display:none">
								<div class="form-group">
									<label for="cardTypeId" class="col-sm-3 control-label">会员卡类型:</label>
									<div class="col-sm-9">
										<select name="cardTypeId" id="cardTypeId" class="form-control required">
											<c:forEach items="${cardTypeList}" var="cardType">
												<option value="${cardType.id}">${cardType.cardtypename}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="code" class="col-sm-3 control-label">卡号:</label>
									<div class="col-sm-9">
										<input type="text" name="code" 
											class="form-control required"
											remote="${ctx}/createCard/checkCode" />
									</div>
								</div>
								<div class="form-group">
									<label for="innerCode" class="col-sm-3 control-label">内部卡号:</label>
									<div class="col-sm-9">
										<input type="text" name="innerCode" 
											class="form-control required"
											remote="${ctx}/createCard/checkInnerCode" />
									</div>
								</div>
								<div class="form-group">
									<label for="pwd" class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-9">
										<input type="password" id="pwd" name="pwd" class="form-control cardPwd"/>
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
