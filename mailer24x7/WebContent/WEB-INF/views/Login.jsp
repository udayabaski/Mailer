<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<title>Mailer247 - Registration</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<link href="../styles/signup-style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../script/jquery.js"></script>
</head>

<body>


 <div id="pageContainer">
	
		
		<ul id="tabs" class="clearfix">
			<li class="activeTab" id="signInTab">
				<div class="signInTabContent">
					<a href="../auth/loginform.form"><span>Already a member?</span></a>
					<h1>Sign in below</h1>
				</div>
				
				<span class="activeTabArrow"><!-- --></span>
			</li>
			
		</ul>

      <form:form action="../j_spring_security_check" name="loginForm" id="frm_login" method="POST">
      
        <c:if test="${not empty loginForm.generalMessage}">
          <div class="success"><c:out value="${loginForm.generalMessage}"/></div>
        </c:if>
    
		<div id="signUp" class="clearfix toggleTab">
		
			
            <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
            <td valign="top" width="70%">
            <table width="80%" cellpadding="0" cellspacing="0">
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Email</td>
            <td width="3%"></td>
            <td><input name="j_username" class="loginbox" id="j_username" /></td>
            </tr>
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Password</td>
            <td width="3%"></td>
            <td><input type="password" name="j_password" class="loginbox" id="j_password"/></td>
            </tr>
            
             <tr><td colspan="3" height="20"><p>New to Mailer24x7? <a href="../comn/regform.form" >Join Today</a></p></td></tr>
            <tr>    
            <td></td>        
            <td colspan="2"  align="center"> <a onclick="document.forms[0].submit();return false;" class="button green" href="#">Login</a> <a class="button green" href="#">Cancel</a></td>            
            </tr>
            
            </table>
            </td>
            
            <td width="3%"></td>
            </tr>
            </table>
		</div>
		</form:form> 
	</div>
	</body>
</html>
