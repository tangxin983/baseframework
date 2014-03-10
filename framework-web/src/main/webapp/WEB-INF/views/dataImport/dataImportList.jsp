<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<script type="text/javascript"
	src="${ctx}/static/script/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/jquery.iframe-transport.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/jquery.fileupload.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#card-remove-btn').on('click', function() {
			$('#card-upload-btn').addClass("disabled");
		});
		$('#menu-remove-btn').on('click', function() {
			$('#menu-upload-btn').addClass("disabled");
		});
		$('#seat-remove-btn').on('click', function() {
			$('#seat-upload-btn').addClass("disabled");
		});
		$('#cardImport').fileupload({
			//dataType : 'json',
			add : function(e, data) {
				$('#card-upload-btn').removeClass("disabled");
				$('#card-upload-btn').off('click').on('click', function() {
					data.submit();
				});
			},
			done : function(e, data) {
				if(data.result.msg && data.result.msg != "")
					bootbox.alert(data.result.msg);
			}
		});
		$('#menuImport').fileupload({
			//dataType : 'json',
			add : function(e, data) {
				$('#menu-upload-btn').removeClass("disabled");
				$('#menu-upload-btn').off('click').on('click', function() {
					data.submit();
				});
			},
			done : function(e, data) {
				if(data.result.msg && data.result.msg != "")
					bootbox.alert(data.result.msg);
			}
		});
		$('#seatImport').fileupload({
			//dataType : 'json',
			add : function(e, data) {
				$('#seat-upload-btn').removeClass("disabled");
				$('#seat-upload-btn').off('click').on('click', function() {
					data.submit();
				});
			},
			done : function(e, data) {
				if(data.result.msg && data.result.msg != "")
					bootbox.alert(data.result.msg);
			}
		});
	});
</script>
<title>数据导入</title>
</head>

<body>
	<div class="data-content">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-user"></span> 数据导入
				</h3>
			</div>
			<div class="panel-body">
				<div class="alert alert-info">
					<strong>如何批量导入数据?</strong> <br> 
					第1步：下载Excel模板<br>
					第2步：按照Excel模板中的要求录入数据内容 <br> 
					第3步：上传填写好的Excel表格
				</div>
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-2 control-label">批量导入会员卡:</label>
						<div class="col-lg-6">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="input-group">
									<div class="form-control uneditable-input span3"
										data-trigger="fileinput">
										<i class="glyphicon glyphicon-file fileinput-exists"></i> <span
											class="fileinput-filename"></span>
									</div>
									<span class="input-group-addon btn btn-default btn-file">
										<span class="fileinput-new">选择文件</span> <span
										class="fileinput-exists">重新选择</span> <input type="file"
										id="cardImport" data-url="${ctx}/dataImport/upload/card"
										accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									</span> <a href="#" id="card-remove-btn"
										class="input-group-addon btn btn-default fileinput-exists"
										data-dismiss="fileinput">移除文件</a>
								</div>
							</div>
						</div>
						<div class="col-lg-4" id="btnDiv">
							<a href="${ctx}/dataImport/download/excel/excelImportCard"
								class="btn btn-primary"> <span
								class="glyphicon glyphicon-download">下载模板</span>
							</a> <a id="card-upload-btn" class="btn btn-primary disabled"> <span
								class="glyphicon glyphicon-upload">上传表格</span>
							</a>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">批量导入商品:</label>
						<div class="col-lg-6">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="input-group">
									<div class="form-control uneditable-input span3"
										data-trigger="fileinput">
										<i class="glyphicon glyphicon-file fileinput-exists"></i> <span
											class="fileinput-filename"></span>
									</div>
									<span class="input-group-addon btn btn-default btn-file">
										<span class="fileinput-new">选择文件</span> <span
										class="fileinput-exists">重新选择</span> <input type="file"
										id="menuImport" data-url="${ctx}/dataImport/upload/menu"
										accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									</span> <a href="#" id="menu-remove-btn"
										class="input-group-addon btn btn-default fileinput-exists"
										data-dismiss="fileinput">移除文件</a>
								</div>
							</div>
						</div>
						<div class="col-lg-4" id="btnDiv">
							<a href="${ctx}/dataImport/download/excel/excelImportMenu"
								class="btn btn-primary"> <span
								class="glyphicon glyphicon-download">下载模板</span>
							</a> <a id="menu-upload-btn" class="btn btn-primary disabled"> <span
								class="glyphicon glyphicon-upload">上传表格</span>
							</a>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">批量导入桌位:</label>
						<div class="col-lg-6">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="input-group">
									<div class="form-control uneditable-input span3"
										data-trigger="fileinput">
										<i class="glyphicon glyphicon-file fileinput-exists"></i> <span
											class="fileinput-filename"></span>
									</div>
									<span class="input-group-addon btn btn-default btn-file">
										<span class="fileinput-new">选择文件</span> <span
										class="fileinput-exists">重新选择</span> <input type="file"
										id="seatImport" data-url="${ctx}/dataImport/upload/seat"
										accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
									</span> <a href="#" id="seat-remove-btn"
										class="input-group-addon btn btn-default fileinput-exists"
										data-dismiss="fileinput">移除文件</a>
								</div>
							</div>
						</div>
						<div class="col-lg-4" id="btnDiv">
							<a href="${ctx}/dataImport/download/excel/excelImportSeat"
								class="btn btn-primary"> <span
								class="glyphicon glyphicon-download">下载模板</span>
							</a> <a id="seat-upload-btn" class="btn btn-primary disabled"> <span
								class="glyphicon glyphicon-upload">上传表格</span>
							</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
