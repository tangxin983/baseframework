<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.tx.framework.web.common.page.Page" required="true"%>

<script type='text/javascript'>
	$(document).ready(function(){
		var options = {
		    bootstrapMajorVersion: 3,
		    currentPage: <%=page.getCurrentPage()%>,
		    totalPages: <%=page.getTotalPage()%>,
		    tooltipTitles: function (type, page, current) {
                switch (type) {
	                case "first":
	                    return "首页";
	                case "prev":
	                    return "上一页";
	                case "next":
	                    return "下一页";
	                case "last":
	                    return "末页";
	                case "page":
	                    return "";
                }
            },
		     
		    pageUrl: function(type, page, current){
                return "?page=" + page + "&${searchParams}";
            }
		};
		if(<%=page.getTotalPage()%> > 1){
			$('#pagination').bootstrapPaginator(options);
		}
	});
	
</script>

<ul class="pagination">
	<li class="disabled">
		<a>第<%=page.getCurrentResult() + 1%> - <%=page.getCurrentEndResult()%>条记录 / 共<%=page.getTotal()%>条</a>
	</li>
</ul>
<ul id='pagination' class='pull-right'></ul>
