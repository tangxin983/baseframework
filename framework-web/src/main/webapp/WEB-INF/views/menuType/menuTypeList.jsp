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
		view: {
			selectedMulti: false,
		},
		async: {
			enable: true,
			url: treeUrl
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick,
			onAsyncSuccess: onAsyncSuccess
		}
	};

    function treeUrl(treeId, treeNode) {
		return "${ctx}/menuType/tree";
	}

    function onAsyncSuccess(event, treeId, msg) {
    	$.fn.zTree.getZTreeObj("menuTypeTree").expandAll(true);
	}
    
    function onClick(event, treeId, treeNode, clickFlag) {
    	//根节点则不允许修改
    	if(treeNode.level == 0){
    		$("#editBtn").addClass('disabled');
    	}else{
    		$("#editBtn").removeClass('disabled');
    	}
    	view(treeNode.id);
	}
    
    $(document).ready(function(){
		$.fn.zTree.init($("#menuTypeTree"), treeSetting);
		$("#input_menuTypeName").rules( "add", {
			remote: {
			    url: "${ctx}/menuType/checkName",
			    type: "POST",
			    data: {                     
			    	menuTypeName: function() {
			            return $("#input_menuTypeName").val();
			        },
			        id: function() {
			            return $("#menuTypeId").val();
			        }
			    }
			}
		});
	});
	
	function view(id) {
		viewFormNew("${ctx}/menuType/get/" + id);
	}
	
	function create() {
		createFormNew("${ctx}/menuType/create");
	}
	
	function edit() {
		var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
		createFormNew("${ctx}/menuType/update");
		$.get("${ctx}/menuType/get/" + selectedNode.id, function(resp){
			$('#inputForm').populate(resp);
		}); 
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
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 商品分类
						</h3>
					</div>
					<div class="panel-body">
						<ul id="menuTypeTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 编辑商品分类
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">上级分类:</label>
								<label class="control-label" id="parentTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">分类名称:</label>
								<label class="control-label" id="menuTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">分类编码:</label>
								<label class="control-label" id="code"></label>
							</div>
						</form>
						<form action="${ctx}/menuType/create" method="post" id="inputForm"
							class="form-horizontal" style="display:none">
							<input type="hidden" name="id" id="menuTypeId"/>
							<div class="form-group">
								<label for="parentId" class="col-sm-3 control-label">上级分类:</label>
								<div class="col-sm-9">
									<select name="parentId" class="form-control required">
										<c:forEach items="${menuTypeList}" var="menuType">
											<option value="${menuType.id}">${menuType.menuTypeName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="menuTypeName" class="col-sm-3 control-label">分类名称:</label>
								<div class="col-sm-9">
									<input type="text" name="menuTypeName" id="input_menuTypeName" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="code" class="col-sm-3 control-label">分类编码:</label>
								<div class="col-sm-9">
								  	<input type="text" name="code" class="form-control"/>
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
