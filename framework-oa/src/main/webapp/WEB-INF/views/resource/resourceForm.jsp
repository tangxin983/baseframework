<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>资源管理</title>
</head>

<body>
	<div class="data-content">
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title"><span class="glyphicon glyphicon-user"></span> 编辑资源</h3>
		</div>
		<form action="${ctx}/resource/${action}" method="post" id="inputForm">
			<input type="hidden" name="id" value="${entity.id}"/>
			<div class="panel-body">
				<div class="form-group">
		   			<div class="row">
		    			<div class="col-lg-6">
						    <label for="resourceName">资源名称:</label>
							<input type="text" id="resourceName" name="resourceName"  value="${entity.resourceName}" class="form-control required"/>
						</div>
						<div class="col-lg-6">
					        <label for="uri">资源链接:</label>
							<input type="text" id="uri" name="uri"  value="${entity.uri}" class="form-control required"/>
						</div>
		   			</div>
	   			</div>
	   			<div class="form-group">
		   			<div class="row">
		   				<div class="col-lg-6">
							<label for="sort">资源序号:</label>
							<input type="text" id="sort" name="sort"  value="${entity.sort}" class="form-control"/>
						</div>
		    			<div class="col-lg-6">
						    <label for="permission">权限描述:</label>
							<input type="text" id="permission" name="permission"  value="${entity.permission}" class="form-control"/>
						</div>
		   			</div>
	   			</div>
	   			<div class="form-group">
		   			<div class="row">
		   				<div class="col-lg-6">
							<label for="type">资源类型:</label>
							<select name="type" id="type" class="form-control required">
								<option value="">请选择...</option>
								<option value="1" <c:if test="${entity.type eq 1}">selected="selected"</c:if>>菜单</option>
								<option value="2" <c:if test="${entity.type eq 2}">selected="selected"</c:if>>资源</option>
							</select>
						</div>
		    			<div class="col-lg-6">
		    				<label for="parentId">上级资源:</label>
							<select name="parentId" id="parentId" class="form-control">
								<option value="">请选择...</option>
								<c:forEach items="${resourceList}" var="r">
									<option value="${r.id}" <c:if test="${entity.parentId eq r.id}">selected="selected"</c:if>>${r.resourceName}</option>
								</c:forEach>
							</select>
						</div>
		   			</div>
	   			</div>
			</div>
			<div class="panel-footer">
				<button type="submit" class="btn btn-success">
					<span class="glyphicon glyphicon-ok"></span> 保存
				</button>
				<button type="button" class="btn btn-default" onclick="history.back();">
					<span class="glyphicon glyphicon-backward"></span> 返回
				</button>
			</div>
		</form>
	</div>
	</div>	 
</body>
</html>
