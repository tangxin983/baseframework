<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myfn" uri="/myfn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta name="decorator" content="default1" />
<title>编辑角色</title>
</head>

<body>
 	<div class="data-content">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title"><span class="glyphicon glyphicon-user"></span> 编辑角色</h3>
		</div>
		<form action="${ctx}/role/${action}" method="post" id="inputForm">
			<input type="hidden" name="id" value="${role.id}" />
			<div class="panel-body">
				<div class="form-group">
		   			<div class="row">
		    			<div class="col-lg-6">
						    <label for="roleName">角色名称（英文）:</label>
							<input type="text" id="roleName" name="roleName" value="${role.roleName}" class="form-control required" />		
						</div>
						<div class="col-lg-6">
							<label for="state">状态:</label>
							<select class="form-control required" name="state" id="state">
								<option value="1" 
									<c:if test="${role.state == '1'}">selected="selected"</c:if>>
									启用
								</option>
						        <option value="0"
									<c:if test="${role.state == '0'}">selected="selected"</c:if>>
						        	停用
						        </option>
					        </select>
						</div>
		   			</div>
	   			</div>
	   			<div class="form-group">
		   			<div class="row">
		    			<div class="col-lg-12">
							<!-- 表格 开始 -->
							<div class="panel panel-default">
							
								<div class="panel-heading">
									<h3 class="panel-title"><span class="glyphicon glyphicon-link"></span> 拥有资源</h3>
								</div>
								
								<table class="table table-bordered table-hover">
						            <thead>
							            <tr>
							                <th><input type="checkbox" id="selectAll" /></th>
											<th>资源名称</th>
											<th>资源URI</th>
											<th>权限描述</th>
							            </tr>
							        </thead>
							        <tbody>
							            <c:forEach items="${resources}" var="resource">
											<tr>
												<td><input type="checkbox" value="${resource.id}" name="resourceId"
															<c:if test="${myfn:contains(selectedResources, resource.id)}">checked</c:if> /></td>
												<td>${resource.resourceName}</td>
												<td>${resource.uri}</td>
												<td>${resource.permission}</td>
											</tr>
										</c:forEach>	
							        </tbody>
							    </table>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
		   			<div class="row">
		    			<div class="col-lg-12">
							<label for="remark">备注:</label>
							<textarea id="remark" name="remark" rows="3" class="form-control">${role.remark}</textarea>
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

	<script>
		$(document).ready(
				function() {
					$("#selectAll").click(
							function() {
								var childrens = $(this).parents("table").find(
										"tbody input[type='checkbox']");
								childrens.prop("checked", $(this)
										.is(":checked"));
							});
				});
	</script>
</body>
</html>
