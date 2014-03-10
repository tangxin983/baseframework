<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>排版</title>
<link href="${ctx}/static/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/script/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.fileupload.js"></script>
<!--[if gte IE 6]>
<script src="${ctx}/static/script/imgmap/excanvas.js" type="text/javascript"></script>
<![endif]-->
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
	
	var selectedPageNo;

	function treeUrl(treeId, treeNode) {
		return "${ctx}/menuType/tree?noRoot=1";
	}

	function onAsyncSuccess(event, treeId, msg) {
		$.fn.zTree.getZTreeObj("menuTypeTree").expandAll(true);
	}

	function onClick(event, treeId, treeNode, clickFlag) {
		$('#addPage').removeClass("disabled");
		$('.fileinput').fileinput('clear');
		disableEdit();
		var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
		$.post("${ctx}/menuLayout/getPageContent",{search_menuTypeId:selectedNode.id},function(result){
		    $("#page-content").html(result);
		});
		gui_loadImage("");
	}
	
	function disableEdit(){
		$(".btn-file").addClass("disabled");
		$("#saveBtn").addClass("disabled");
		$("#dd_zoom").prop('disabled', true);
		$('#uploadFile').prop('disabled', true);
	}
	
	function enableEdit(){
		$(".btn-file").removeClass("disabled");
		$("#saveBtn").removeClass("disabled");
		$("#dd_zoom").prop('disabled', false);
		$('#uploadFile').prop('disabled', false);
	}
	
	function saveImgMap() {
		$('#inputForm').ajaxSubmit(function() {
			bootbox.alert("保存成功");
        });
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#menuTypeTree"), treeSetting);
		$('#uploadFile').fileupload({
			add : function(e, data) {
				var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
				data.url = '${ctx}/attachment/upload/layout/' + $('#menuLayoutId').val() + '/' + selectedPageNo;
				data.submit();
			},
			done : function(e, data) {
				if(data.result && data.result != ""){
					$('#metaFileInfo').val(data.result);
					var infos = data.result.split('|');
					gui_loadImage("${ctx}/upload/layout/" + infos[4] + "/" + infos[0]);
				}
			}
		});
		// 添加页
		$("#addPage").on("click", function () {
			var rowCount = $('#page-content tr').length + 1;
			var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
			$.post("${ctx}/menuLayout/createLayout",{menuTypeId:selectedNode.id},function(result){
			    var newRow = $("<tr>");
		        var cols = "<td>第" + rowCount + "页</td>";
		        cols += "<td><input name='layoutId' type='hidden' value='" + result.layoutId + 
		        	"'><input name='pageNo' type='hidden' value='" + result.pageNo + 
		        	"'><a class='btn btn-danger' name='pageDel'><span class='glyphicon glyphicon-remove'></span></a></td>";
		        newRow.append(cols);
		        $('#page-content').append(newRow);
			});
	    });
		// 删除页
		$("#page-content").on("click", "[name='pageDel']", function () {
			var thisTr = $(this).closest("tr");
			bootbox.confirm("此页排版图片和热区将被删除，是否继续？", function(result) {
				if(result){
					$.get("${ctx}/menuLayout/deleteLayout/" + thisTr.find("[name='layoutId']").val(), function(){
						thisTr.remove();
				        $('#page-content tr').each(function() {
				            $(this).children('td').first().html("第" + ($(this).index() + 1) + "页"); 
				        });
				        disableEdit();
				        gui_loadImage('', '');
					});
				}
			}); 
	    });
		// 点击页
		$("#page-content").on("click", "tr", function () {
			$('#menuLayoutId').val($(this).find("[name='layoutId']").val());
			selectedPageNo = $(this).find("[name='pageNo']").val();
			$('#mapCode').val('');
			$('#metaFileInfo').val('');
			$('.fileinput').fileinput('clear');
			enableEdit();
			$.get("${ctx}/menuLayout/getLayout/" + $('#menuLayoutId').val(), function(result){
				gui_loadImage(result.path, result.mapCode);
			});
		});
	});
	
</script>
</head>

<body>
	<%@ include file="/WEB-INF/views/modal/menuModal.jsp"%>
	
	<div class="data-content">

		<c:if test="${not empty message}">
			<div class="alert alert-success fade in">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<span class="glyphicon glyphicon-ok"></span> ${message}
			</div>
		</c:if>

		<div class="row">
			<div class="col-md-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 分类
						</h3>
					</div>
					<div class="panel-body">
						<ul id="menuTypeTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 分页
						</h3>
					</div>
				    <div class="panel-body">
						<form class="form-inline" valid="false">
							<a id="addPage" class="btn btn-primary disabled"> 
								<span class="glyphicon glyphicon-plus">添加</span> 
							</a>
						</form>
						<br/>
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>页码</th>
									<th>删除</th>
								</tr>
							</thead>
							<tbody id="page-content" clickable="true">
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 热区编辑
						</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="input-group">
									<div class="form-control uneditable-input span3" data-trigger="fileinput">
										<i class="glyphicon glyphicon-file fileinput-exists"></i> 
										<span class="fileinput-filename"></span>
									</div>
									<span class="input-group-addon btn btn-default btn-file disabled">
										<span class="fileinput-new">选择</span>
										<span class="fileinput-exists">选择</span> 
										<input type="file" id="uploadFile" accept="image/*" disabled/>
									</span> 
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<select onchange="gui_zoom(this)" id="dd_zoom" class="form-control" disabled>
								<option value=''>请选择缩放比例</option>
								<option value='0.25'>25%</option>
								<option value='0.5'>50%</option>
								<option value='1'>100%</option>
								<option value='2'>200%</option>
							</select>
						</div>
						<a id="saveBtn" onclick="saveImgMap()" class="btn btn-success disabled">
							<span class="glyphicon glyphicon-ok"></span> 保存
						</a>
						<br/>
						<div id="pic_container" >
						</div>
						<br/>
						<form action="${ctx}/menuLayout/createImgMap" valid="false" method="post" id="inputForm">
						<div class="panel panel-info">
  							<div class="panel-heading">热区列表</div>
  							<div class="panel-body">
	    						<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>商品绑定</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody id="bind-content" clickable="true">
									</tbody>
								</table>
  							</div>
						</div>
						<input name="mapCode" id="mapCode" type="hidden"/>
						<input name="menuLayoutId" id="menuLayoutId" type="hidden"/>
						<input name="metaFileInfo" id="metaFileInfo" type="hidden"/>
						</form>
					</div>
				</div>
			</div>
		</div>
	<script src="${ctx}/static/script/imgmap/imgmap.js" type="text/javascript"></script>
	<script src="${ctx}/static/script/imgmap/interface.js" type="text/javascript"></script>
</body>
</html>
