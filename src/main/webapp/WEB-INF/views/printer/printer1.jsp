<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>xxxxxxxxx</title>
	<%@ include file="printer-common-css.jsp"%>
</head>
<body>
 <div class="print-main" id="div_print-main" style="display: none;">
	 <div id="headerInfo1">
		<div class="print-title"> 
			<h3>xxxxxx</h3>
		</div>
		<div  class="print-chara">
			<span>xxxxxx</span>
		</div>
	</div>	
		<div class="print-content">
			 <table id="tabContent1" class="print-table-border" style="width: 100%;">  
				<thead>
					<tr>
						<th width="3%">xxxx</th>  
					</tr>
					</thead>
					<tbody>
					<% int indexNum = 1; %>
						<tr scope="row" class="specalt">
							<td class="text-center"><%= indexNum++ %></td>  
							<td>xxxxxx</td>
						</tr>
				</tbody>
				</table>  
	</div>
				<!-- 页码信息 -->
				<div id="footerInfo1">
					<span class="floatRight pageNum">1/1</span>
				</div>
</div>	 
 
<script type="text/javascript">
$(function(){
	/**
	* headerInfo 头部信息ID
	* tabContent 表格内容ID
	* footerInfo 底部信息ID
	* pageBreak 分页样式 class
	*　pageNum　页码样式　class
	×　10　每页显示多少条记录
	* 	index 当前处理组
	*  totalPage 一共有多少组数据
	*/
	for (var i = 1; i<=1 ;i++ ) {
		AutoPage.init("headerInfo"+i, "tabContent"+i, "footerInfo"+i,"pageBreak","pageNum",10,i,1);
	}
	
	$("div#div_print-main").printArea(); 
});
</script>
</body>
</html>