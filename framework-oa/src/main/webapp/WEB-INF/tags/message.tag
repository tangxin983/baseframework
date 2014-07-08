<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="content" type="java.lang.String" required="true" description="消息内容"%>
<%@ attribute name="type" type="java.lang.String" description="消息类型：info、success、warning、error、loading"%>
<!-- <script type="text/javascript">top.$.jBox.closeTip();</script>  -->
<c:if test="${not empty content}">
	<div id="messageBox" class="alert alert-${ctype}">
  		<button class="close" data-dismiss="alert">&times;</button>${content}
	</div>
	<!-- 
	<script type="text/javascript">
		if(!top.$.jBox.tip.mess){
			top.$.jBox.tip.mess=1;
			top.$.jBox.tip("${content}","${ctype}",{persistent:true,opacity:0});
			$("#messageBox").show();
		}
	</script>
	 -->
</c:if>