<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>桌位管理</title>
<script type="text/javascript">
	var id;
	$(document).ready(function() {
		query();
		$("#input_seatName").rules( "add", {
			remote: {
			    url: "${ctx}/${module}/checkName",
			    type: "POST",
			    data: {                     
			        seatName: function() {
			            return $("#input_seatName").val();
			        },
			        id: function() {
			            return $("#seatId").val();
			        }
			    }
			}
		});
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
		ajaxPagination("${ctx}/${templateUrl}", {data : "search_seatName=" + $("#search_seatName").val()});
	}
	
	function create() {
		createFormNew("${ctx}/${module}/create");
	}
	
	function edit() {
		createFormNew("${ctx}/${module}/update", "${ctx}/${module}/get/" + id);
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
							<span class="glyphicon glyphicon-user"></span> 桌位列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_seatName" class="form-control" placeholder="桌位名称">
							</div>
							<a onclick="query()" class="btn btn-info" id="search_btn">
								<span class="glyphicon glyphicon-search">查询</span> 
							</a>
							<a onclick="del()" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove">删除</span> 
							</a>
						</form>
						<br/>
						<form id="user-view-form" action="${ctx}/${module}/delete" valid="false" method="post">
							<table id="contentTable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>所属区域</th>
										<th>桌位名称</th>
										<th>桌位编号</th>
										<th>建议人数</th>
										<th>类型</th>
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
							<span class="glyphicon glyphicon-user"></span> 编辑桌位
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">所属区域:</label>
								<label class="control-label" id="areaName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">桌位名称:</label>
								<label class="control-label" id="seatName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">桌位编号:</label>
								<label class="control-label" id="code"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">桌位类型:</label>
								<label class="control-label" id="seatKindName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">建议人数:</label>
								<label class="control-label" id="adviseNum"></label>
							</div>
						</form>
						<form action="${ctx}/${module}/create" method="post" id="inputForm" class="form-horizontal" style="display:none">
							<input type="hidden" name="id" id="seatId"/>
							<div class="form-group">
								<label for="areaId" class="col-sm-3 control-label">所属区域:</label>
								<div class="col-sm-9">
									<select name="areaId" class="form-control required">
										<c:forEach items="${areaList}" var="area">
											<option value="${area.id}">${area.areaName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="seatName" class="col-sm-3 control-label">桌位名称:</label>
								<div class="col-sm-9">
									<input type="text" name="seatName" id="input_seatName" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="col-sm-3 control-label">桌位编号:</label>
								<div class="col-sm-9">
									<input type="text" name="code" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="seatKind" class="col-sm-3 control-label">桌位类型:</label>
								<div class="col-sm-9">
									<select name="seatKind" class="form-control required">
										<option value="1">散座</option>
										<option value="2">包厢</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="adviseNum" class="col-sm-3 control-label">建议人数:</label>
								<div class="col-sm-9">
									<input type="text" name="adviseNum" class="form-control digits required"/>
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
