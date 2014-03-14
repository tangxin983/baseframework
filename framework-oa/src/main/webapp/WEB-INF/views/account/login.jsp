<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>登录页</title>
</head>

<body>
	<div class="data-content">
		<div class="login-container">
			<div class="panel panel-default">
			
				<div class="panel-heading">
					<h3 class="panel-title"><sapn class="glyphicon glyphicon-lock"></sapn> 用户登录</h3>
				</div>
				<form id="login-form" action="${ctx}/login" method="post">

					<div class="panel-body">
						
						<c:if test="${!empty shiroLoginFailure}">
							<div class="alert alert-danger fade in">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								<span class="glyphicon glyphicon-remove"></span>${shiroLoginFailure}
							</div>
						</c:if>
						
						<div class="form-group">
							<label for="username">帐号:</label> 
							<input type="text" class="form-control required" name="username" id="username" value="${username}">
						</div>
						<div class="form-group">
							<label for="password">密码:</label> 
							<input type="password" class="form-control required" name="password" id="password">
						</div>
						<div class="form-group">
							<label for="rememberMe">有效期:</label> 
							<select class="form-control" name="rememberMe" id="rememberMe">
								<option value="">无</option>
								<option value="25200">一周</option>
								<option value="2592000">一个月</option>
								<option value="31536000">一年</option>
							</select>
						</div>
						<c:if test="${!empty showCaptcha and showCaptcha}">
							<div class="form-group">
								<label for="captcha">验证码:</label>
								<div class="row">
									<div class="col-xs-8">
									<input type="text" class="form-control required" name="captcha" id="captcha" >
									</div>
									<div class="col-xs-2">
										<img id="captchaImg" src="getCaptcha" onclick="javascript:reloadCaptcha();"/>
									</div>
									<div class="col-xs-2">
										<a class="btn btn-link" href="javascript:reloadCaptcha();">看不清?</a>
									</div>
								</div>
							</div>
						</c:if>
					</div>
					
					
					<div class="panel-footer">
						<button type="submit" class="btn btn-primary">登录</button>
					</div>
					
					
					
				</form>
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function reloadCaptcha() {
			$("#captchaImg").attr("src", "getCaptcha");
		}
	</script>
</body>
</html>


