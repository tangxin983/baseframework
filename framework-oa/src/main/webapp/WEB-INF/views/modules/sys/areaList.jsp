<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<!-- 这里引入额外的css和js 
	<link rel="stylesheet" type="text/css" href="" />
	<script type="text/javascript" src=""></script>
	-->
	<script type="text/javascript">
		function multiDel(){
			$("[name='ids']").each(function(){
				if($(this).is(":checked")){
					return confirmx_func('确定要删除选中的记录吗?', function(){$("#viewForm").submit();})
				}
			});
		}
	</script>
</head>
<body>
	<tags:message content="${message}" />

	<!-- search form -->
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<div class="form-group">
				<input name="s_name" value="${param.s_name}" class="form-control" placeholder="name">
			</div>
			<div class="form-group">
				<input name="s_officeId" value="${param.s_officeId}" class="form-control" placeholder="officeId">
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<shiro:hasPermission name="sys:area:create">
			<a href="${ctxModule}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加区域
			</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:area:delete">
			<a onclick="multiDel()" class="btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span> 删除
			</a>
			</shiro:hasPermission>
		</form>
	</nav>
	
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">区域列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${ctxModule}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<th>name</th>
							<th>officeId</th>
							<!--
							<th>操作</th>
							-->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result}" var="entity">
							<tr>
								<td><input type="checkbox" name="ids" value="${entity.id}"></td>
		    					<td>
		    						<shiro:hasPermission name="sys:area:edit">
		    						<a href="${ctxModule}/update/${entity.id}" title="修改">
										${entity.name}
									</a>
									</shiro:hasPermission>
									<shiro:lacksPermission name="sys:area:edit">
									${entity.name}
									</shiro:lacksPermission>
		    					</td>
							  	<td>${entity.officeId}</td>
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
</body>
</html>
