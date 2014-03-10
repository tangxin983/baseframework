<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>首页</title>
<script type="text/javascript">
	$(document).ready(function() {
		$.get("${ctx}/module/${pid}", function(resp) {
			$(".data-content").html(resp);
			Holder.run();
		});
	});
</script>
</head>

<body>
	<div class="data-content">
	</div>
</body>
</html>
