<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>商品管理</title>
<link href="${ctx}/static/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/script/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var id;
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
		$("#input_menuName").rules( "add", {
			remote: {
			    url: "${ctx}/${module}/checkName",
			    type: "POST",
			    data: {                     
			    	menuName: function() {
			            return $("#input_menuName").val();
			        },
			        id: function() {
			            return $("#menuId").val();
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
 
	function create() {
		createFormNew("${ctx}/${module}/create");
	}

	function edit() {
		createFormNew("${ctx}/${module}/update");
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
							<a onclick="del()" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove">删除</span> 
							</a>
						</form>
						<br />
						<div class="col-md-3">
							<ul id="menuTypeTree" class="ztree"></ul>
						</div>
						<div class="col-md-9">
							<form id="user-view-form" action="${ctx}/${module}/delete" valid="false" method="post">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th><input type="checkbox" id="selectAll"></th>
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
							<span class="glyphicon glyphicon-user"></span> 编辑商品
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
								<label class="col-sm-3 control-label">商品编码:</label> 
								<label class="control-label" id="code"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">烧菜耗时:</label> 
								<label class="control-label" id="consume"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">商品单价:</label> 
								<label class="control-label" id="price"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">结账单位:</label> 
								<label class="control-label" id="payAccount"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">是否打折:</label> 
								<label class="control-label" id="ratioName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">退货验证:</label> 
								<label class="control-label" id="backAuthName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">收银改价:</label> 
								<label class="control-label" id="changePriceName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">商品介绍:</label> 
								<label class="control-label" id="memo"></label>
							</div>
						</form>
						<form action="${ctx}/menu/create" method="post" id="inputForm"
							class="form-horizontal" style="display: none">
							<input type="hidden" name="id" id="menuId"/>
							<div class="form-group">
								<label for="menuTypeId" class="col-sm-3 control-label">商品分类:</label>
								<div class="col-sm-9">
									<select name="menuTypeId" class="form-control required">
										<option value="">请选择</option>
										<c:forEach items="${menuTypeList}" var="menuType">
											<option value="${menuType.id}">${menuType.menuTypeName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="menuName" class="col-sm-3 control-label">商品名称:</label>
								<div class="col-sm-9">
									<input type="text" name="menuName" id="input_menuName" class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="col-sm-3 control-label">商品编码:</label>
								<div class="col-sm-9">
									<input type="text" name="code" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="consume" class="col-sm-3 control-label">烧菜耗时:</label>
								<div class="col-sm-9 input-group">
									<input type="text" name="consume" class="form-control digits" />
									<span class="input-group-addon">分钟</span>
								</div>
							</div>
							<div class="form-group">
								<label for="price" class="col-sm-3 control-label">商品单价:</label>
								<div class="col-sm-9 input-group">
									<span class="input-group-addon">¥</span>
								  	<input type="text" name="price" value="0" class="form-control number required"/>
									<span class="input-group-addon">元</span>
								</div>
							</div>
							<div class="form-group">
								<label for="payAccount" class="col-sm-3 control-label">结账单位:</label>
								<div class="col-sm-9">
									<input type="text" name="payAccount" class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label for="ratio" class="col-sm-3 control-label">是否打折:</label>
								<div class="col-sm-9">
									<select name="ratio" class="form-control required">
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="backAuth" class="col-sm-3 control-label">退货验证:</label>
								<div class="col-sm-9">
									<select name="backAuth" class="form-control required">
										<option value="1">需要验证</option>
										<option value="0">不需验证</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="changePrice" class="col-sm-3 control-label">收银改价:</label>
								<div class="col-sm-9">
									<select name="changePrice" class="form-control required">
										<option value="1">允许</option>
										<option value="0">不允许</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="memo" class="col-sm-3 control-label">商品介绍:</label>
					   			<div class="col-sm-9">
									<textarea name="memo" rows="5" class="form-control" maxlength="200"></textarea>
								</div>
				   			</div>
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a onclick="create()" class="btn btn-primary"> 
							<span class="glyphicon glyphicon-plus"></span> 新增
						</a> 
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
