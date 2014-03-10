<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>卡详情一览</title>
<script type="text/javascript">
	$(document).ready(function() {
		query();
		// 点击行
		$("#list-content").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				var id = $(this).find("[name='clickId']").val();
				if (id) {
					getOperateInfo(id);
				}
			}
		});
	});

	function query() {
		ajaxPagination("${ctx}/${templateUrl}", {
			data : "search_code=" + $("#search_code").val()
		});
	}

	function getOperateInfo(id) {
		ajaxPagination("${ctx}/operateflow/template", {
			data : "search_cardid=" + id,
			tbodyId : "list-content-new",
			paginatorId : "paginationNew"
		});
	}
</script>
</head>

<body>
	<div class="data-content">
 
		<div class="row">
			<div class="col-md-7">
				<!-- 卡管理 -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 会员卡列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_code" class="form-control"
									placeholder="卡号">
							</div>
							<a onclick="query()" class="btn btn-info"> <span
								class="glyphicon glyphicon-search"></span> 查询
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
			<!-- 卡操作流水 -->
			<div class="col-md-5">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 操作详情
						</h3>
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
				</div>
			</div>
		</div>
	</div>
</body>
</html>
