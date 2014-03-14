<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/static/script/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.fileupload.js"></script>
<script type="text/javascript">
	var button;
	$(document).ready(function() {
		$("#attachmentModal").on('shown.bs.modal', function(e) {
			button = $(e.relatedTarget);
			$("#attachmentModalTitle").html(button.data('servicetitle') + "——附件上传");
			$.ajax({
				url : "${ctx}/attachment/modal/" + button.data('servicetype') + "/" + button.data('serviceid'),
				type : "POST",
				async : false,
				success : function(resp) {
					if(resp && resp != ""){
						$("#attachmentModalBody").html(resp);
						Holder.run();
					}
				}
			});
			$("[name='upload']").fileupload({
				done : function(e, data) {
					if (data.result && data.resul != ""){
						$(this).parent().parent().find("[name='meta-upload']").val(data.result);
					}
				}
			});
			$("[name='meta-upload-remove']").on('click', function() {
				$(this).parent().find("[name='meta-upload']").val('');
			});
		});
		$("#attachmentModal").on('hidden.bs.modal', function () {
			$("#attachmentModalBody").html("");	 
		});
	});
	
	function confirm() {
		$('#inputForm').ajaxSubmit(function() {
			var img = "#" + button.data("serviceid");
			$.ajax({
				url : "${ctx}/attachment/display/" + button.data('serviceid'),
				type : "POST",
				success : function(resp) {
					if(resp && resp != ""){
						$(img).removeAttr("data-src");
						$(img).attr("src","${ctx}/" + resp);
					}
					if(resp == ""){
						$(img).removeAttr("src");
						$(img).attr("data-src","holder.js/100%x150/text:请上传图片");
						Holder.run({images: img});
					}
				}
			});
            $('#canBtn').click();
        });
	}
</script>

<div class="modal fade" id="attachmentModal" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="customerModalLabel">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<span class="glyphicon glyphicon-file" id="attachmentModalTitle">附件上传</span> 
				</h4>
			</div>
			<div class="modal-body" id="attachmentModalBody">
				 
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="confirm()">确定</button>
				<button type="button" class="btn btn-default" data-dismiss="modal" id="canBtn">取消</button>
			</div>
		</div>
	</div>
</div>


