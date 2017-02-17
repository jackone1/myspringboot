<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}" />

 <script type="text/javascript">
 var basePath = "${basePath}";
 </script>

 <link type="text/css" rel="stylesheet" href="${basePath}/css/bootstrap/bootstrap.min.css" />  
 <link type="text/css" rel="stylesheet" href="${basePath}/css/common.css" /> 
 <link rel="stylesheet" type="text/css" href="${basePath}/css/easyui/easyui.css">
 <link rel="stylesheet" type="text/css" href="${basePath}/css/easyui/new-easyui.css">
 <link rel="stylesheet" href="${basePath}/css/login.css" media="screen" type="text/css" /> 
 	<link rel="stylesheet" type="text/css" href="${basePath}/css/index.css">
 	
 <script type="text/javascript" src="${basePath}/js/jquery-1.9.1.js"></script>  
 <script type="text/javascript" src="${basePath}/js/easyui/jquery.min.js"></script> 
 <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.js"></script>   
 <script type="text/javascript" src="${basePath}/js/paging_print.js"></script>
 <script type="text/javascript" src="${basePath}/js/jquery.PrintArea.js"></script>
 <script type="text/javascript" src="${basePath}/js/ajaxfileupload.js"></script>
 <script type="text/javascript" src="${basePath}/js/pageLoading.js"></script>
 
 