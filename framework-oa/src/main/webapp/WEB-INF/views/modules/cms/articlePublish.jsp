<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>内容发布</title>
<%@ include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
	var tree;

	$(document).ready(function() {
		var setting = {view:{selectedMulti:false},data:{simpleData:{enable:true}}};
		$.get("${ctx}/cms/category/treeData", function(zNodes){
			for(var i=0; i<zNodes.length; i++) {
				// 移除顶级节点
				if (zNodes[i] && zNodes[i].id == 1){
					zNodes.splice(i, 1);
				} 
			}
			// 初始化树结构
			tree = $.fn.zTree.init($("#categoryTree"), setting, zNodes);
			// 默认展开全部节点
			tree.expandAll(true);
		});
	});
</script>
</head>
<body>
	<tags:message content="${message}" />
	<div class="row">
		<div class="col-md-2">
			<!-- 栏目树 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="text-muted bootstrap-admin-box-title">栏目列表</div>
				</div>
				<div class="panel-body">
					<div id="categoryTree" class="ztree"></div>
				</div>
			</div>
		</div>
		<div class="col-md-10">.col-md-10</div>
	</div>
</body>
</html>
