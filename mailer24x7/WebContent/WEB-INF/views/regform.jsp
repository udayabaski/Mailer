<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<title>Mailer247 - Registration</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <script type="text/javascript" src="../script/jquery.js"></script>
    <script type="text/javascript">
    
    function checkIfExist() {
    /*$.getJSON("/comn/seekerregform.form?action=validateEmail", { emialId: $('#emailId').val() }, function(data) {
        window.alert(data);
    });*/
   /* $.ajax({
        url: 'regform.form?action=checkOrg',
        data: ({orgName : $("#organization").val()}),
        success: function(data) {
          window.alert("Response is : "+data);
          //$('#organization').html(data1);
        }
     }); */
   }

  </script>
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

      <form:form action="regform.form"  commandName="regForm" method="POST" id="registration">
      <form:hidden path="userId" />
      
      <c:if test="${not empty regForm.userId}">
        <div class="info">Thanks for registration. An activation mail has been sent to your email. Please follow the mail. 
          Click the link below to send the mail again if you have not got the mail. <a href="regform.form?action=send&object=<c:out value="${regForm.userId}"/>">Send now.</a></div>
      </c:if>
      
		<div id="signUp" class="clearfix toggleTab">
		
			
            <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
            <td valign="top" width="70%">
            <table width="80%" cellpadding="0" cellspacing="0">
            <tr>
            <td width="45%">Full Name</td>
            <td width="3%"></td>
            <td>
               <form:input path="fullName" id="fullName" />
               <span class="errortxt"><form:errors path="fullName" /></span>
             </td>
            </tr>
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Company</td>
            <td width="3%"></td>
            <td>
              <form:input onchange="checkIfExist()" path="organization" id="organization" />
              <span class="errortxt"><form:errors path="organization" /></span>
            </td>
            </tr>
            
             <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Website</td>
            <td width="3%"></td>
            <td>
              <form:input path="website" id="website" />
              <span class="errortxt"><form:errors path="website" /></span>
             </td>
            </tr>
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Country</td>
            <td width="3%"></td>
            <td>
               <form:select path="country" cssClass="selbox2" id="sub_group">
				  <form:options items="${countryMap}" />
   			   </form:select>
   			   <span class="errortxt"><form:errors path="country" /></span>
            </td>
            </tr>
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Email</td>
            <td width="3%"></td>
            <td>
              <form:input path="emailId" id="emailId" />
              <span class="errortxt"><form:errors path="emailId" /></span>
            </td>
            </tr>
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Password</td>
            <td width="3%"></td>
            <td>
              <form:password path="password" id="password" />
              <span class="errortxt"><form:errors path="password" /></span>
             </td>
            </tr>
            
            <tr><td colspan="3" height="20"></td></tr>
             <tr>
            <td>Retype password</td>
            <td width="3%"></td>
            <td>
              <form:password path="confirmPassword" id="confirmPassword" />
              <span class="errortxt"><form:errors path="confirmPassword" /></span>
            </td>
            </tr>
            
             <tr><td colspan="3" height="20"></td></tr>
            <tr>    
            <td></td>        
            <td colspan="2"  align="center"> <a onclick="document.forms[0].submit();return false;" class="button green" href="#">SignUp</a> <a class="button green" href="#">Cancel</a></td>            
            </tr>
            
            </table>
            </td>
            
            <td width="3%"></td>
            <td valign="top" width="27%">
            <table cellpadding="0" cellspacing="0">
            <tr>
            <td>
            <div id="sidebar">
				<h3>Benefits for signing up</h3>
				
				<ul>
					<li>24/7 support from our team</li>
					<li>Another great benefit</li>
					<li>We're in the cloud, so accessing your data will be 10x faster</li>
					<li>We use the latest technology on the market today</li>
				</ul>
			</div>
            </td>
            </tr>
            </table>
            
            </td>
            </tr>
            </table>
		</div>
		</form:form> 
	</div>
	</body>
</html>
