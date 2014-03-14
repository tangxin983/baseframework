<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>菜谱内容</title>
<link href="${ctx}/static/css/zTreeStyle/zTreeStyle.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx}/static/script/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script>
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
	});

	function query() {
		var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree")
				.getSelectedNodes()[0];
		if (!selectedNode) {
			ajaxPagination("${ctx}/${module}/thumbnail", {
				data : "search_menuName=" + $("#search_menuName").val()
			});
		} else {
			if (selectedNode.level == 0) {
				ajaxPagination("${ctx}/${module}/thumbnail", {
					data : "search_menuName=" + $("#search_menuName").val()
				});
			} else {
				ajaxPagination("${ctx}/${module}/thumbnail", {
					data : "search_menuName=" + $("#search_menuName").val()
							+ "&search_menuTypeId=" + selectedNode.id
				});
			}
		}
	}
</script>
</head>

<body>
	<%@ include file="/WEB-INF/views/modal/attachmentModal.jsp"%>
	
	<div class="data-content">

		<c:if test="${not empty message}">
			<div class="alert alert-success fade in">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<span class="glyphicon glyphicon-ok"></span> ${message}
			</div>
		</c:if>

		<div class="row">
			<div class="col-lg-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 菜品分类
						</h3>
					</div>
					<div class="panel-body">
						<ul id="menuTypeTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-lg-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 菜品列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_menuName" class="form-control"
									placeholder="菜品名称">
							</div>
							<a onclick="query()" class="btn btn-primary"> <span
								class="glyphicon glyphicon-search">查询</span>
							</a>
						</form>
						<br />
						<div id="list-content">
						</div>
						<div>
							<ul id='pagination' class='pull-right'></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
