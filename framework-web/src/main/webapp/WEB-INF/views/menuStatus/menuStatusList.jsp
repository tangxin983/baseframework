<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>商品分类</title>
<link href="${ctx}/static/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/script/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var treeSetting = {
		view : {
			selectedMulti : false,
		},
		async : {
			enable : true,
			url : treeUrl
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClick,
			onAsyncSuccess : onAsyncSuccess
		}
	};

	function treeUrl(treeId, treeNode) {
		return "${ctx}/menuType/tree";
	}

	function onAsyncSuccess(event, treeId, msg) {
		$.fn.zTree.getZTreeObj("menuTypeTree").expandAll(true);
		query();
	}

	function onClick(event, treeId, treeNode, clickFlag) {
		query();
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#menuTypeTree"), treeSetting);
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
		var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
		if(!selectedNode){
			ajaxPagination("${ctx}/${templateUrl}", 
					{data : "search_menuName=" + $("#search_menuName").val()});
		}else{
			if(selectedNode.level == 0){
				ajaxPagination("${ctx}/${templateUrl}", 
						{data : "search_menuName=" + $("#search_menuName").val()});
			}else{
				ajaxPagination("${ctx}/${templateUrl}", 
						{data : "search_menuName=" + $("#search_menuName").val() + "&search_menuTypeId=" + selectedNode.id});
			}
		}
	}
 
	function edit() {
		createFormNew();
		$.get("${ctx}/${module}/get/" + id, function(resp){
			$('#inputForm').populate(resp);
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
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 商品列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_menuName" class="form-control" placeholder="名称">
							</div>
							<a onclick="query()" class="btn btn-primary"> 
								<span class="glyphicon glyphicon-search">查询</span> 
							</a>
						</form>
						<br/>
						<div class="col-md-3">
							<ul id="menuTypeTree" class="ztree"></ul>
						</div>
						<div class="col-md-9">
							<form id="user-view-form" action="${ctx}/${module}/delete" valid="false" method="post">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>商品</th>
											<th>价格（元）</th>
											<th>单位</th>
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
			</div>
			
			<div class="col-md-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 编辑状态
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">商品分类:</label> 
								<label class="control-label" id="menuTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">商品名称:</label> 
								<label class="control-label" id="menuName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">商品状态:</label> 
								<label class="control-label" id="statusName"></label>
							</div>
						</form>
						<form action="${ctx}/menuStatus/update" method="post" id="inputForm"
							class="form-horizontal" style="display: none">
							<input type="hidden" name="id" />
							<div class="form-group">
								<label class="col-sm-3 control-label">商品分类:</label> 
								<label class="control-label" id="menuTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">商品名称:</label> 
								<label class="control-label" id="menuName"></label>
							</div>
							<div class="form-group">
								<label for="status" class="col-sm-3 control-label">商品状态:</label>
								<div class="col-sm-9">
									<select name="status" class="form-control required">
										<option value="1">在售</option>
										<option value="2">停售</option>
										<option value="3">沽清</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a id="editBtn" onclick="edit()" class="btn btn-default disabled">
							<span class="glyphicon glyphicon-edit"></span> 修改
						</a>
					</div>
					<div id="createfooter" class="panel-footer" style="display: none">
						<a onclick="save()" class="btn btn-success"> 
							<span class="glyphicon glyphicon-ok"></span> 保存
						</a> 
						<a onclick="returnToViewForm()" class="btn btn-default"> 
							<span class="glyphicon glyphicon-backward"></span> 返回
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
