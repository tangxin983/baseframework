<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>卡充值</title>
<script type="text/javascript">
	var id;
	$(document).ready(function() {
		query();
		// 点击行
		$("#list-content").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				id = $(this).find("[name='clickId']").val();
				if (id) {
					viewFormNew("${ctx}/${module}/get/" + id);
					ajaxPagination("${ctx}/operateflow/template", 
							{data : "search_cardid=" + id + "&search_action=" + 11, tbodyId: "list-content-new", paginatorId: "paginationNew"});
					$("#editBtn").removeClass("disabled");
				}
			}
		});
	});
	
	function query(){
		ajaxPagination("${ctx}/${templateUrl}", 
				{data : "search_code=" + $("#search_code").val()});
	}
	
	function chargeCard() {
		updateFormNew("${ctx}/chargeCard/get/" + id);
	}
	
	function save() {
		$("#updateForm").submit();
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
			<div class="col-md-6">
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
						</form>
						<br/>
						<table id="contentTable" class="table table-bordered table-hover">
							<thead>
								<tr>
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
						<br />
						<div>
							<ul id='pagination' class='pull-right'></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 卡充值
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-3 control-label">会员卡类型:</label>	
								<label class="control-label" id="cardTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">卡号:</label>
								<label class="control-label" id="code"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">会员:</label>
								<label class="control-label" id="customerName"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">累计充值:</label>
								<label class="control-label" id="realBalance"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">余额:</label>
								<label class="control-label" id="balance"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">制卡日期:</label>
								<label class="control-label" id="beginDate"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">充值记录:</label>
							</div>
							<div class="panel-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>操作时间</th>
											<th>操作类型</th>
											<th>金额（元）</th>
											<th>操作人</th>
										</tr>
									</thead>
									<tbody id="list-content-new">
									</tbody>
								</table>
								<br />
								<div>
									<ul id='paginationNew' class='pull-right'></ul>
								</div>
							</div>
						</form>
						<form action="${ctx}/chargeCard/update" method="post" id="updateForm"
							class="form-horizontal" style="display:none">
							<input type="hidden" name="id" id="cardId" value="" />
							<div class="form-group">
								<label class="col-sm-3 control-label">会员卡类型:</label>	
								<label class="control-label" id="cardTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">卡号:</label>
								<label class="control-label" id="code"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">会员:</label>
								<label class="control-label" id="customerName"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">累计充值:</label>
								<label class="control-label" id="realBalance"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">余额:</label>
								<label class="control-label" id="balance"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">制卡日期:</label>
								<label class="control-label" id="beginDate"></label>
							</div> 
							<div class="form-group">
								<label for="charge" class="col-sm-3 control-label">充值金额:</label>
								<div class="col-sm-9 input-group">
									<span class="input-group-addon">¥</span>
								  	<input type="text" id="charge" name="charge" value="0"
										class="form-control number required" />
									<span class="input-group-addon">元</span>
								</div>
							</div>
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a id="editBtn" onclick="chargeCard()" class="btn btn-default disabled">
							<span class="glyphicon glyphicon-edit"></span> 充值
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
