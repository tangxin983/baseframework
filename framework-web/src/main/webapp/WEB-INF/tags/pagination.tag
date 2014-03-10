<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="body" type="java.lang.String" required="true"%>
<script type='text/javascript'>
	$(document).ready(function(){
		ajaxPagination("<%=url%>?${searchParams}", "<%=body%>", "<%=id%>");
	});
	
</script>

<ul id='<%=id%>' class='pull-right'></ul>