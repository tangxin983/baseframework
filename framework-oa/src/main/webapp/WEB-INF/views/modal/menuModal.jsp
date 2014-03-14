<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var button;
	var menuModalId;
	$(document).ready(function() {
		$("#menuModal").on('shown.bs.modal', function(e) {
			button = $(e.relatedTarget);
			menuModalQuery();
		});
		// 点击行
		$("#list-content-menuModal").on("click", "tr", function(e) {
			if (e.target.type != 'checkbox') {
				menuModalId = $(this).find("[name='clickId']").val();
			}
		});
	});
	
	function menuModalQuery(){
		var selectedNode = $.fn.zTree.getZTreeObj("menuTypeTree").getSelectedNodes()[0];
		ajaxPagination("${ctx}/menu/template/modal", {
			data : "search_menuName=" + $("#search_menuName").val() + "&search_menuTypeId=" + selectedNode.id,
			tbodyId : "list-content-menuModal",
			paginatorId : "pagination-menuModal"
		});
	}

	function confirm() {
		if (typeof menuModalId === "undefined") {
			return false;
		} else {
			var vals = menuModalId.split('||');
			button.parent().parent().find("[name='menuId']").val(vals[0]);
			button.parent().parent().find("[name='menuName']").val(vals[1]);
			if(gui_input_change){
				gui_input_change(button, vals);
			}
		}
		$('#canBtn').click();
	}
</script>

<div class="modal fade" id="menuModal" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<span class="glyphicon glyphicon-file"></span> 绑定商品
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline" valid="false">
					<div class="form-group">
						<input type="text" id="search_menuName" class="form-control" placeholder="商品名">
					</div>
					<a class="btn btn-info" onclick="menuModalQuery()"> <span
						class="glyphicon glyphicon-search">查询</span>
					</a>
				</form>
				<br/>
				<table id="contentTable"
					class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>商品</th>
							<th>价格（元）</th>
							<th>单位</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody id="list-content-menuModal" clickable="true">
					</tbody>
				</table>
				<br/>
				<div>
					<ul id='pagination-menuModal' class='pull-right'></ul>
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


