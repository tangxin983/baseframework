<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>挂失与解挂</title>
<script type="text/javascript">
	var id;
	$(document).ready(function() {
		query();
		// 点击行
		$("#list-content").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				id = $(this).find("[name='clickId']").val();
				if (id) {
					view(id);
				}
			}
		});
	});
	
	function query(){
		ajaxPagination("${ctx}/${templateUrl}", 
				{data : "search_code=" + $("#search_code").val()});
	}
 
	function lost() {
		bootbox.confirm("确定要挂失？", function(result) {
			if(result){
				$('#cardStatus').val('2');
				$('#viewForm').submit();
			}
		});
	}
	
	function hang() {
		bootbox.confirm("确定要解挂？", function(result) {
			if(result){
				$('#cardStatus').val('1');
				$('#viewForm').submit();
			}
		});
	}
	
	function view(id){
		viewFormNew();
		$.get("${ctx}/${module}/get/" + id, function(resp){
			$('#viewForm').populate(resp);
			if(resp.status == "1"){
				$("#hangBtn").addClass("disabled");
				$("#lostBtn").removeClass("disabled");
			}else if(resp.status == "2"){
				$("#lostBtn").addClass("disabled");
				$("#hangBtn").removeClass("disabled");
			}
		});
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
						</form>
						<br />
						<table class="table table-bordered table-hover">
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
			<div class="col-md-5">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 挂失与解挂
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" id="viewForm" valid="false" action="${ctx}/lostCard/update" method="post">
							<input type="hidden" name="id" />
							<input type="hidden" name="status" id="cardStatus"/>
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
								<label class="col-sm-3 control-label">积分:</label>
								<label class="control-label" id="degree"></label>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">制卡日期:</label>
								<label class="control-label" id="beginDate"></label>
							</div> 
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a id="lostBtn" onclick="lost()" class="btn btn-default disabled"> 
							<span class="glyphicon glyphicon-info-sign"></span> 挂失
						</a> 
						<a id="hangBtn" onclick="hang()" class="btn btn-default disabled">
							<span class="glyphicon glyphicon-info-sign"></span> 解挂
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
