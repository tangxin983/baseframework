<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta name="decorator" content="default1" />
<title>资源管理</title>
<script type="text/javascript" src="${ctx}/static/script/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.fileupload.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		query();
		// 点击行
		$("#list-content").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				id = $(this).find("[name='clickId']").val();
				if (id) {
					viewFormNew("${ctx}/${module}/get/" + id);
					$.get("${ctx}/attachment/query/${module}/" + id, function(resp){
						if(resp.path && resp.path != ""){
							$("#viewImg").attr("src", "${ctx}/" + resp.path);
						}else{
							$("#viewImg").removeAttr("src");
							Holder.run();
						}
					});
					$("#editBtn").removeClass("disabled");
				}
			}
		});
		// 附件上传
		$("[name='upload']").fileupload({
			done : function(e, data) {
				if (data.result && data.resul != ""){
					$("[name='metaInfo']").val(data.result);
				}
			}
		});
		$("[name='meta-upload-remove']").on('click', function() {
			$("[name='metaInfo']").val('');
		});
	});

	function query() {
		ajaxPagination("${ctx}/${templateUrl}", {
			data : "search_resourceName=" + $("#search_resourceName").val() + "&search_type=" + $("#search_type").val()
		});
	}
	
	function create() {
		createFormNew("${ctx}/${module}/create");
		attachment();		
	}
	
	function edit() {
		createFormNew("${ctx}/${module}/update"); 
		$.get("${ctx}/${module}/get/" + id, function(resp){
			$('#inputForm').populate(resp);
			$("#inputForm [name='sort']").val(resp.sort);
			attachment({
				context:"${ctx}",
				serviceType:"${module}",
				serviceId:id
			});			
		});
	}
	
	function del() {
		$("#user-view-form").submit();
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
			<div class="col-md-7">
				<div class="panel panel-default">
					<div class="panel-heading">资源列表</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_resourceName" class="form-control"
									placeholder="资源名称">
							</div>
							<div class="form-group">
								<select id="search_type" class="form-control">
									<option value="">请选择资源类型...</option>
									<option value="1">菜单</option>
									<option value="2">资源</option>
								</select>
							</div>
							<a onclick="query()" class="btn btn-info"> <span
								class="glyphicon glyphicon-search"></span> 查询
							</a> <a onclick="del()" class="btn btn-danger"> <span
								class="glyphicon glyphicon-remove"></span> 删除
							</a>
						</form>
						<br />
						<form id="user-view-form" action="${ctx}/${module}/delete"
							valid="false" method="post">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>资源名称</th>
										<th>资源URI</th>
										<th>权限描述</th>
										<th>资源类型</th>
										<th>上级资源</th>
									</tr>
								</thead>
								<tbody id="list-content" clickable="true">
								</tbody>
							</table>
						</form>
						<div>
							<ul id='pagination' class='pull-right'></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="panel panel-default">
					<div class="panel-heading">编辑</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">资源图片:</label> 
								<div class="col-sm-5">
									<a href="#" class="thumbnail">
								      <img id="viewImg" data-src="holder.js/100%x150/text:资源图片">
								    </a>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源类型:</label> 
								<label class="control-label" id="resourceTypeName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源名称:</label> 
								<label class="control-label" id="resourceName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源链接:</label> 
								<label class="control-label" id="uri"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源序号:</label> 
								<label class="control-label" id="sort"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">权限描述:</label> 
								<label class="control-label" id="permission"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">上级资源:</label> 
								<label class="control-label" id="parentResourceName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注:</label> 
								<label class="control-label" id="remark"></label>
							</div>
						</form>
						<form action="${ctx}/${module}/create" method="post"
							id="inputForm" class="form-horizontal" style="display: none">
							<input type="hidden" name="id" />
							<div class="form-group">
								<label class="col-sm-3 control-label">资源图片:</label>
								<div class="col-sm-9 fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail"
										style="width: 150px; height: 150px;">
										<img data-src="holder.js/150x150/text:资源图片" />
									</div>
									<div class="fileinput-preview fileinput-exists thumbnail"
										style="width: 150px; height: 150px;">
									</div>
									<div>
										<span class="btn btn-default btn-file"> 
											<span class="fileinput-new">选择</span> 
											<span class="fileinput-exists">重选</span>
											<input type="file" name="upload"
												data-url="${ctx}/attachment/upload/${module}" accept="image/*">
										</span> 
										<a href="#" name="meta-upload-remove"
											class="btn btn-default fileinput-exists" data-dismiss="fileinput">移除</a>
										<input type="hidden" name="metaInfo"/>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源类型:</label>
								<div class="col-sm-9">
									<select name="type" class="form-control required">
										<option value="">请选择...</option>
										<option value="1">菜单</option>
										<option value="2">资源</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源名称:</label>
								<div class="col-sm-9">
									<input type="text" name="resourceName" class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源链接:</label>
								<div class="col-sm-9">
									<input type="text" name="uri" class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">资源序号:</label>
								<div class="col-sm-9">
									<input type="text" name="sort" class="form-control digits" />
								</div>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">权限描述:</label>
								<div class="col-sm-9">
									<input type="text" name="permission" class="form-control" />
								</div>
							</div> 
							<div class="form-group">
								<label class="col-sm-3 control-label">上级资源:</label>
								<div class="col-sm-9">
									<select name="parentId" class="form-control">
										<option value="">请选择...</option>
										<c:forEach items="${resourceList}" var="r">
											<option value="${r.id}">${r.resourceName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="remark" class="col-sm-3 control-label">备注:</label>
					   			<div class="col-sm-9">
									<textarea name="remark" rows="5" class="form-control" maxlength="200"></textarea>
								</div>
				   			</div>
						</form>
					</div>
					<div id="viewfooter" class="panel-footer">
						<a onclick="create()" class="btn btn-primary"> <span
							class="glyphicon glyphicon-plus"></span> 新增
						</a> <a id="editBtn" onclick="edit()" class="btn btn-default disabled">
							<span class="glyphicon glyphicon-edit"></span> 修改
						</a>
					</div>
					<div id="createfooter" class="panel-footer" style="display: none">
						<a onclick="save()" class="btn btn-success"> <span
							class="glyphicon glyphicon-ok"></span> 保存
						</a> <a onclick="returnToViewForm()" class="btn btn-default"> <span
							class="glyphicon glyphicon-backward"></span> 返回
						</a>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
