<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<%-- <script type="text/javascript" src="${basePath}/js/easyui/jquery.min.js"></script> --%>
<%--   <script type="text/javascript" src="${basePath}/js/paging_print.js"></script> --%>
<%--  <script type="text/javascript" src="${basePath}/js/jquery.PrintArea.js"></script> --%>
 
<style type="text/css" class="printStyle" > 

.print-main{
	/*padding: 3% 67px; */
	width: 1123px; 
}

.print-title{
	text-align: center;
	padding: 0px;
}
.print-chara{ 
	font-size: 14;
}
.print-content{
	
}

.print-table-border{ 
	border: 1px solid #000 ; 
	border-width:1px 0px 0px 1px;  
	word-break:break-all;  
	border-spacing: 0px;
}
 
.print-table-border th,
.print-table-border td{ 
	border: 1px solid #000 ;  
	border-width:0px 1px 1px 0px; 
}

.print-table-border th{
     font-size: 12; 
}
.print-table-border td{
	padding:3px 0px;
	font-size: 12;
}

.text-center{
	text-align: center;
}

.text-right{
	text-align: right;
}

.floatRight {
	float: right;
}

@media print
{
.pageBreak {page-break-after:always;}
.noPrint{
	display:none;
}
}
@page { size: landscape; }

</style>
<body>

</body>
</html>