<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${functionName}管理</title>
	<!-- 这里引入额外的css和js 
	<link rel="stylesheet" type="text/css" href="" />
	<script type="text/javascript" src=""></script>
	-->
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">${functionName}编辑</div>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" action="${r"${ctxModule}"}/${r"${action}"}" method="post">
				<input type="hidden" name="id" value="${r"${entity.id}"}">
				<#list entityFields as field>
				<div class="form-group">
					<label class="col-md-2 control-label">${field.name}：</label>
					<div class="col-md-6">
						<input name="${field.name}" maxlength="50" class="form-control required"
							value="${r"${entity."}${field.name}}" />
					</div>
				</div>
				</#list>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<input type="submit" class="btn btn-primary" value="保存" /> 
						<a href="${r"${ctxModule}"}" class="btn btn-default">返 回</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
