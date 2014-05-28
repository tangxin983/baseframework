<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假管理</title>
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
				<input name="s_processInstanceId" value="${param.s_processInstanceId}" class="form-control" placeholder="processInstanceId">
			</div>
			<div class="form-group">
				<input name="s_startTime" value="${param.s_startTime}" class="form-control" placeholder="startTime">
			</div>
			<div class="form-group">
				<input name="s_endTime" value="${param.s_endTime}" class="form-control" placeholder="endTime">
			</div>
			<div class="form-group">
				<input name="s_leaveType" value="${param.s_leaveType}" class="form-control" placeholder="leaveType">
			</div>
			<div class="form-group">
				<input name="s_reason" value="${param.s_reason}" class="form-control" placeholder="reason">
			</div>
			<div class="form-group">
				<input name="s_applyTime" value="${param.s_applyTime}" class="form-control" placeholder="applyTime">
			</div>
			<div class="form-group">
				<input name="s_realityStartTime" value="${param.s_realityStartTime}" class="form-control" placeholder="realityStartTime">
			</div>
			<div class="form-group">
				<input name="s_realityEndTime" value="${param.s_realityEndTime}" class="form-control" placeholder="realityEndTime">
			</div>
			<div class="form-group">
				<input name="s_processStatus" value="${param.s_processStatus}" class="form-control" placeholder="processStatus">
			</div>
			<div class="form-group">
				<input name="s_createBy" value="${param.s_createBy}" class="form-control" placeholder="createBy">
			</div>
			<div class="form-group">
				<input name="s_createDate" value="${param.s_createDate}" class="form-control" placeholder="createDate">
			</div>
			<div class="form-group">
				<input name="s_updateBy" value="${param.s_updateBy}" class="form-control" placeholder="updateBy">
			</div>
			<div class="form-group">
				<input name="s_updateDate" value="${param.s_updateDate}" class="form-control" placeholder="updateDate">
			</div>
			<div class="form-group">
				<input name="s_remarks" value="${param.s_remarks}" class="form-control" placeholder="remarks">
			</div>
			<div class="form-group">
				<input name="s_delFlag" value="${param.s_delFlag}" class="form-control" placeholder="delFlag">
			</div>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<a href="${ctxModule}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加请假
			</a>
			<shiro:hasPermission name="oa:leave:delete">
			<a onclick="multiDel()" class="btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span> 删除
			</a>
			</shiro:hasPermission>
		</form>
	</nav>
	
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">请假列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${ctxModule}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<th>processInstanceId</th>
							<th>startTime</th>
							<th>endTime</th>
							<th>leaveType</th>
							<th>reason</th>
							<th>applyTime</th>
							<th>realityStartTime</th>
							<th>realityEndTime</th>
							<th>processStatus</th>
							<th>createBy</th>
							<th>createDate</th>
							<th>updateBy</th>
							<th>updateDate</th>
							<th>remarks</th>
							<th>delFlag</th>
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
		    						<shiro:hasPermission name="oa:leave:edit">
		    						<a href="${ctxModule}/update/${entity.id}" title="修改">
										${entity.processInstanceId}
									</a>
									</shiro:hasPermission>
									<shiro:lacksPermission name="oa:leave:edit">
									${entity.processInstanceId}
									</shiro:lacksPermission>
		    					</td>
							  	<td>${entity.startTime}</td>
							  	<td>${entity.endTime}</td>
							  	<td>${entity.leaveType}</td>
							  	<td>${entity.reason}</td>
							  	<td>${entity.applyTime}</td>
							  	<td>${entity.realityStartTime}</td>
							  	<td>${entity.realityEndTime}</td>
							  	<td>${entity.processStatus}</td>
							  	<td>${entity.createBy}</td>
							  	<td>${entity.createDate}</td>
							  	<td>${entity.updateBy}</td>
							  	<td>${entity.updateDate}</td>
							  	<td>${entity.remarks}</td>
							  	<td>${entity.delFlag}</td>
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
