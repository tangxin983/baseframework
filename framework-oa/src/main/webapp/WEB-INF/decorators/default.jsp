<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="adminTemplate by tangxin.">
<meta name="author" content="tangxin">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<!--[if lt IE 9]>
<script src="${ctx}/static/script/html5shiv.js"></script>
<script src="${ctx}/static/script/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/layout.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap-datetimepicker.min.css" />
<!-- jasny must before bootstrap -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jasny-bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/bootstrap-select.min.css" />
<!-- jquery -->
<script type="text/javascript" src="${ctx}/static/script/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.validate.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/script/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.extends.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.populate.pack.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jquery.transit.min.js"></script>
<!-- bootstrap -->
<script type="text/javascript" src="${ctx}/static/script/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/jasny-bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bootbox.min.js"></script>
<script type="text/javascript" src="${ctx}/static/script/holder.js"></script>
<script type="text/javascript" src="${ctx}/static/script/default.config.min.js"></script>
<sitemesh:head />
</head>

<body>
	<div class="main-container">
		<div class="panel panel-primary">
			<shiro:user>
				<%@ include file="/WEB-INF/decorators/navbar.jsp"%>
			</shiro:user>
			<sitemesh:body />
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/WEB-INF/decorators/footer.jsp"%>
</body>
<script type="text/javascript">
var currentLocation = window.location.pathname.split("/")[window.location.pathname.split("/").length -1];
if(currentLocation != "" && typeof(currentLocation) != "undefined"){
	$.get("${ctx}/resource/breadcrumb?path=" + currentLocation, function(resp){
		if(resp && resp != ""){
			$('.data-content').prepend(resp);
		}
	});
}
</script>
</html>