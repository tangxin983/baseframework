<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>内容发布</title>
<%@ include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
	function multiDel() {
		$("[name='ids']").each(function() {
			if ($(this).is(":checked")) {
				return confirmx_func('确定要删除选中的记录吗?', function() {
					$("#viewForm").submit();
				})
			}
		});
	}

	var tree;

	$(document).ready(function() {
		var setting = {
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			}
		};
		$.get("${ctx}/cms/category/treeData", function(zNodes) {
			for (var i = 0; i < zNodes.length; i++) {
				// 移除顶级节点
				if (zNodes[i] && zNodes[i].id == 1) {
					zNodes.splice(i, 1);
				}
				// 添加url
				zNodes[i].url = "${ctx}/cms/" + zNodes[i].type + "?s_categoryId=" + zNodes[i].id;
				zNodes[i].target = "_self";
			}
			// 初始化树结构
			tree = $.fn.zTree.init($("#cTree"), setting, zNodes);
			// 默认展开全部节点
			tree.expandAll(true);
			//<c:if test="${not empty param.s_categoryId}">
			var node = tree.getNodeByParam("id", "${param.s_categoryId}");
			tree.selectNode(node);
			//</c:if>
		});
	});
</script>
</head>
<body>
	<tags:message content="${message}" />

	<div class="row">
		<div class="col-md-3">
			<!-- 栏目树 -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="text-muted bootstrap-admin-box-title">栏目列表</div>
				</div>
				<div class="panel-body">
					<div id="cTree" class="ztree"></div>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<!-- search form -->
			<nav class="navbar navbar-default">
				<form class="navbar-form navbar-left" valid="false">
					<div class="form-group col-md-3">
						<tags:treeselect id="category" name="s_categoryId"
								value="${category.id}" labelName="categoryName"
								labelValue="${category.name}" allowClear="false" title="栏目"
								url="/cms/category/treeData" />
					</div>
					<div class="form-group">
						<input name="s_title" value="${param.s_title}"
							class="form-control" placeholder="标题">
					</div>
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"></span> 查询
					</button>
					<shiro:hasPermission name="cms:article:create">
						<a href="${ctxModule}/create" class="btn btn-primary"> <span
							class="glyphicon glyphicon-plus"></span> 添加文章
						</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="cms:article:delete">
						<a onclick="multiDel()" class="btn btn-danger"> <span
							class="glyphicon glyphicon-remove"></span> 删除
						</a>
					</shiro:hasPermission>
				</form>
			</nav>

			<!-- table -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="text-muted bootstrap-admin-box-title">文章列表</div>
				</div>
				<div class="panel-body">
					<form id="viewForm" action="${ctxModule}/delete" valid="false"
						method="post">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th><input type="checkbox" id="selectAll"></th>
									<th>categoryId</th>
									<th>title</th>
									<!--
							<th>操作</th>
							-->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.result}" var="entity">
									<tr>
										<td>
											<input type="checkbox" name="ids" value="${entity.id}">
										</td>
										<td>${entity.category.name}</td>
										<td>
											<shiro:hasPermission name="cms:article:edit">
											<a href="${ctxModule}/update/${entity.id}" title="修改">
												${entity.title}
											</a>
											</shiro:hasPermission> 
											<shiro:lacksPermission name="cms:article:edit">
											${entity.title}
											</shiro:lacksPermission>
										</td>
										<!--
								<td>
									<a href="${ctxModule}/update/${entity.id}" class="btn btn-default" title="修改">
										<span class="glyphicon glyphicon-edit"></span>
									</a>
								    <a href="${ctxModule}/delete/${entity.id}" class="btn btn-danger" title="删除"
								    	onclick="return confirmx('确定要删除吗?', this.href)">
								    	<span class="glyphicon glyphicon-remove"></span>
								    </a> 
								</td>
								-->
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
					<tags:pagination page="${page}" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>
