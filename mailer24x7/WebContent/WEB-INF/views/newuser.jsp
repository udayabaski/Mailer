<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>User Profile</title>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>

<form:form action="profile.form"  commandName="profileForm" method="POST" id="profile" >

<form:hidden path="action" />
<form:hidden path="userId" />


<!--content main controller table starts!-->
<table cellpadding="0" cellspacing="0" width="100%" class="content-padding">
<tr>
<td width="10%"></td>
<td width="80%" >
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="100%" class="contentLayer">
<table width="98%" cellpadding="0" cellspacing="0" align="center" >
<tr>
<td class="content-heading" colspan="4"><img src="images/user.png" class="top-icon-padd" />User Profile</td>
</tr>

<tr>
<td class="empty" colspan="4"></td>
</tr>
<tr>
<td width="2%"></td>
<td width="20%"><span class="formtext">Email ID</span></td>
<td width="30%">
  <form:input path="emailId" id="emailId" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="emailId" /></span>
 </td>
<td width="52%"></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Password</span></td>
<td>
  <form:input path="password" id="password" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="password" /></span>
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Conform Password</span></td>
<td>
  <form:input path="confirmPassword" id="confirmPassword" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="confirmPassword" /></span>
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Role</span></td>
<td>
   <form:select path="role" cssClass="selbox2"
									id="sub_group">
									<form:options items="${roleMap}" />
   </form:select> 
   <span class="errortxt"><form:errors path="role" /></span>
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td colspan="3"><div class="email-content"><a href="#">For more setting &raquo;</a></div></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Full Name</span></td>
<td>
  <form:input path="fullName" id="fullName" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="fullName" /></span>
 </td>
<td></td>
</tr>


<tr>
<td></td>
<td><span class="formtext">Display Name</span></td>
<td>
  <form:input path="displayName" id="displayName" cssClass="inputwidth" />
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Contact Number</span></td>
<td><form:input path="contactNumber" id="contactNumber" cssClass="inputwidth" /></td>
<td></td>
</tr> 


<tr>
<td></td>
<td><span class="formtext">Language</span></td>
<td><form:input path="language" id="language" cssClass="inputwidth" /></td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Time Zone</span></td>
<td><form:input path="timeZone" id="timeZone" cssClass="inputwidth" /></td>
<td></td>
</tr>

<tr>
<td></td>
<td></td>
<td  align="left"><a onclick="document.forms[0].submit();return false;" class="button green" href="#">Submit</a> <a class="button green" href="#">cancel</a></td>
<td></td>
</tr>


</table>
</td>
</tr>
</table>
</td>
<td width="10%"></td>
</tr>
</table>
<!--content main controller table ends!-->


</form:form>




<!--footer part starts!-->
<table cellpadding="0" cellspacing="0"  width="100%">
<tr>

<td width="10%"></td>
<td width="80%" align="center" style="font-size:12px; color:#595959;">Copyright@companyname.com</td>
<td width="10%"></td>

</tr>
</table>
<!--footer part ends!-->
</body>
</html>
