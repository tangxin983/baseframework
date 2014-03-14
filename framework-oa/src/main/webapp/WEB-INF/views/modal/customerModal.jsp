<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var customerModalId;
	$(document).ready(function() {
		$("#customerModal").on('shown.bs.modal', function() {
			customerModalQuery();
		});
		// 点击行
		$("#list-content-customerModal").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				customerModalId = $(this).find("[name='clickId']").val();
			}
		});
	});
	
	function customerModalQuery(){
		ajaxPagination("${ctx}/customer/template/modal", {
			data : "search_param=" + $("#search_param").val(),
			tbodyId : "list-content-customerModal",
			paginatorId : "pagination-customerModal"
		});
	}

	function confirm() {
		if (typeof customerModalId === "undefined") {
			return false;
		} else {
			var vals = customerModalId.split('||');
			$('#customerId').val(vals[0]);
			$('#customerName').val(vals[1]);
		}
		$('#canBtn').click();
	}
</script>

<div class="modal fade" id="customerModal" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="customerModalLabel">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<span class="glyphicon glyphicon-file"></span> 选择会员
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline" valid="false">
					<div class="form-group">
						<input type="text" id="search_param" class="form-control"
							placeholder="姓名、手机">
					</div>
					<a class="btn btn-info" onclick="customerModalQuery()"> <span
						class="glyphicon glyphicon-search">查询</span>
					</a>
				</form>
				<br/>
				<table class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>姓名</th>
							<th>性别</th>
							<th>手机</th>
							<th>公司</th>
						</tr>
					</thead>
					<tbody id="list-content-customerModal" clickable="true">
					</tbody>
				</table>
				<br/>
				<div>
					<ul id='pagination-customerModal' class='pull-right'></ul>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="confirm()">确定</button>
				<button type="button" class="btn btn-default" data-dismiss="modal"
					id="canBtn">取消</button>
			</div>
		</div>
	</div>
</div>


