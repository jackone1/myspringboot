<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="taglibs.jsp"%>
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
 <title>xxxxxxx</title>
 </head> 
 <body>  
     <div class="container"> 
         <div class="middle-div" style="width: 553px;"> 
         	      <div  style="text-align: center;height: 98px;">
         	                <div style="height: 40px;"> <h1>xxxxxxxxx</h1></div>
         	                <div id="errorMsg" style="height: 58px;padding: 18px;">        
        	                            <c:if test="${message !=null && 'ENTERPRISEERROR' != message && message !=''  }"> 
		         	                            <p style="color: #f24811;"><c:if test="${message!='SYS_UPDATE_FALG'}">温馨提示：${message}</c:if>
		         	                    </c:if>      
         	               </div> 
         	       </div>
	         	  <div class="sub-element" style="text-align: center;line-height: 37px;"> 
	         	  	 <form id="loginForm" method="post"  action="${basePath}/entry/login">
	         	  	 <div>
						<span>登录名：</span>
						<input type="text" id="userName" name="userName" placeholder="输入账号" value=""  class="input-text" onkeyup="changeTextClass(this)"> 
					 </div>
					 <div>
						<span>密 &nbsp;&nbsp;&nbsp;码：</span>
						<input type="password"  id="password" name="password" placeholder="输入密码" value=""  class="input-text"  onkeyup="changeTextClass(this)">
					 </div>  
					 <div class="button-bottom">   
		         	  	    <input type="button" class="downButton" value="登 &nbsp;&nbsp;&nbsp;录" id="entryButton" />
		         	  	    <input type="reset" class="downButton" value="重 &nbsp;&nbsp;&nbsp;置"/> 
	         	     </div> 
	         	     </form>
	         	     <form id="clearDataForm" method="get" action="<c:url value='/entry/clear/data'/>">
	         	     	<input type="hidden" name="clearStatus" id="clearStatus"/>
	         	     </form>
	         	 </div> 
         </div>  
     </div> 
     <div class="main-bottom">版权所有xxxxxxxxx</div>
 </body>
 </html>