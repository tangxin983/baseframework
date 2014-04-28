<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="page" type="com.tx.framework.web.common.persistence.entity.Page" required="true"%>
<ul id="pagination"></ul>
<script type='text/javascript'>
	$(document).ready(function(){
		var options = {
		    bootstrapMajorVersion: 3,
		    currentPage: "${page.currentPage}",
		    totalPages: "${page.totalPage}",
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
		var html = "<li class='disabled'><a>第${page.currentResult + 1} - ${page.currentEndResult}条记录 / 共${page.total}条</a></li>";
		if(<%=page.getTotalPage()%> > 1){
			$('#pagination').bootstrapPaginator(options);
			$('#pagination').append(html);
		}else{
			$('#pagination').addClass('pagination');
			$('#pagination').append(html);
		}
	});
	
</script>