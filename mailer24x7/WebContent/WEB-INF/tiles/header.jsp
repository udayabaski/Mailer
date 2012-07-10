<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<!--logo part starts!-->
<td width="100%">
<!--logo main controller table starts!-->
<table cellpadding="0" cellspacing="0" width="100%">


<tr>
<td width="10%"></td>
<td width="80%" >
<table cellpadding="0" cellspacing="0" width="100%">
<tr><td colspan="2" style="height:10px;"></td></tr>

<tr>
<td  width="10%" style="height:60px;">
<img src="images/logo.png" /> 
</td>
<td align="right" valign="top">
<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>
</td>

</tr>
</table>
</td>
<td width="10%"></td>
</tr>
</table>
<!--logo main controller table ends!-->
</td>
<!--logo part ends!-->
</tr>

</table>

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<!--header part starts!-->
<td width="100%">
<!--header main controller table starts!-->
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="10%"></td>
<td width="80%" >
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="100%">
<div id="menu_wrapper" class="grey">
<div class="left"></div>
<ul id="menu">
         

<li><a href="home.form">Home</a></li>  
<li><a href="#">Email</a></li>
<li><a href="subscriber.form?action=viewSubGroups">Subscribe</a></li>
<li><a href="#">Reports</a></li>
<li class="active"><a href="profile.form?action=view">Admin</a></li>


</ul>
</div>
</td>
</tr>
</table>
</td>
<td width="10%"></td>
</tr>
</table>
<!--header main controller table ends!-->
</td>
<!--header part ends!-->
</tr>

</table>
