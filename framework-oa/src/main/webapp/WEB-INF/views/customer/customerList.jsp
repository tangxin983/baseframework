<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>会员管理</title>
<script type="text/javascript" src="${ctx}/static/script/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.fileupload.js"></script>
<script type="text/javascript">
	var id;//当前选中行id
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
			data : "search_param=" + $("#search_param").val()
		});
	}

	function edit() {
		createFormNew("${ctx}/${module}/update");
		$.get("${ctx}/${module}/get/" + id, function(resp) {
			$('#inputForm').populate(resp);
			attachment({
				context:"${ctx}",
				serviceType:"${module}",
				serviceId:id
			});	 
		});
	}

	function create() {
		createFormNew("${ctx}/${module}/create");
		attachment();
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
			<div class="col-lg-7">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 会员列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" valid="false">
							<div class="form-group">
								<input type="text" id="search_param" class="form-control"
									placeholder="姓名、手机">
							</div>
							<a onclick="query()" class="btn btn-info" id="search_btn"> 
								<span class="glyphicon glyphicon-search"></span> 查询
							</a> 
							<a onclick="del()" class="btn btn-danger"> 
								<span class="glyphicon glyphicon-remove"></span> 删除
							</a>
						</form>
						<br />
						<form id="user-view-form" action="${ctx}/customer/delete"
							valid="false" method="post">
							<table id="contentTable" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" id="selectAll"></th>
										<th>姓名</th>
										<th>性别</th>
										<th>手机</th>
										<th>公司</th>
									</tr>
								</thead>
								<tbody id="list-content" clickable="true">
								</tbody>
							</table>
						</form>
						<br />
						<div>
							<ul id='pagination' class='pull-right'></ul>
						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-5">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span class="glyphicon glyphicon-user"></span> 会员详情
						</h3>
					</div>
					<div class="panel-body">
						<form id="viewForm" class="form-horizontal" valid="false">
							<div class="form-group">
								<label class="col-sm-3 control-label">照片:</label>
								<div class="col-sm-5">
									<a href="#" class="thumbnail">
								      <img id="viewImg" data-src="holder.js/100%x150/text:照片">
								    </a>
								</div> 
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">姓名:</label> <label
									class="control-label" id="customerName"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">性别:</label> <label
									class="control-label" id="sexCh"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">生日:</label> <label
									class="control-label" id="birthday"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">手机:</label> <label
									class="control-label" id="mobile"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">电话:</label> <label
									class="control-label" id="phone"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">身份证:</label> <label
									class="control-label" id="idcard"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">公司:</label> <label
									class="control-label" id="company"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">职业:</label> <label
									class="control-label" id="job"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">职务:</label> <label
									class="control-label" id="pos"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">地址:</label> <label
									class="control-label" id="address"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">电子邮件:</label> <label
									class="control-label" id="email"></label>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注:</label> <label
									class="control-label" id="memo"></label>
							</div>
						</form>
						<form action="${ctx}/${module}/create" method="post" id="inputForm"
							class="form-horizontal" style="display: none">
							<input type="hidden" name="id" value="" />
							<div class="form-group">
								<label class="col-sm-3 control-label">照片:</label>
								<div class="col-sm-9 fileinput fileinput-new" data-provides="fileinput">
									<div class="fileinput-new thumbnail"
										style="width: 150px; height: 150px;">
										<img data-src="holder.js/150x150/text:照片" />
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
								<label for="customerName" class="col-sm-3 control-label">姓名:</label>
								<div class="col-sm-9">
									<input type="text" name="customerName"
										class="form-control required" />
								</div>
							</div>
							<div class="form-group">
								<label for="sex" class="col-sm-3 control-label">性别:</label>
								<div class="col-sm-9">
									<select name="sex" class="form-control required">
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="birthday" class="col-sm-3 control-label">生日:</label>
								<div class="col-sm-9 input-group date form_date">
									<input type="text" name="birthday"
										class="form-control required" readonly /> <span
										class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
							<div class="form-group">
								<label for="mobile" class="col-sm-3 control-label">手机:</label>
								<div class="col-sm-9">
									<input type="text" name="mobile"
										class="form-control required mobile" />
								</div>
							</div>
							<div class="form-group">
								<label for="phone" class="col-sm-3 control-label">电话:</label>
								<div class="col-sm-9">
									<input type="text" name="phone" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="idcard" class="col-sm-3 control-label">身份证:</label>
								<div class="col-sm-9">
									<input type="text" name="idcard" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="company" class="col-sm-3 control-label">公司:</label>
								<div class="col-sm-9">
									<input type="text" name="company" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="job" class="col-sm-3 control-label">职业:</label>
								<div class="col-sm-9">
									<input type="text" name="job" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="pos" class="col-sm-3 control-label">职务:</label>
								<div class="col-sm-9">
									<input type="text" name="pos" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="address" class="col-sm-3 control-label">地址:</label>
								<div class="col-sm-9">
									<input type="text" name="address" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="col-sm-3 control-label">电子邮件:</label>
								<div class="col-sm-9">
									<input type="text" name="email" class="form-control email" />
								</div>
							</div>
							<div class="form-group">
								<label for="memo" class="col-sm-3 control-label">备注:</label>
								<div class="col-sm-9">
									<textarea name="memo" rows="3" class="form-control"></textarea>
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
