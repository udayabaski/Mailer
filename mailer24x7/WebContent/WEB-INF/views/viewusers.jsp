<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Home</title>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mailer24x7/script/jquery-min.js"></script>
</head>

<body>
<script>
	
	
</script>


<form:form action="profile.form" method="POST" commandName="userBean">


<!--content main controller table starts!-->
<table cellpadding="0" cellspacing="0" width="100%" class="content-padding">
<tr>
<td width="10%"></td>
<td width="80%" valign="top">
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
<td width="100%" class="contentLayer">
<div id="tabbed_box_1" class="tabbed_box">
	
    <div class="tabbed_area">
    
    	
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Email ID</td>
            <td class="table-heading">Full Name</td>
            <td class="table-heading">Role</td>
            </tr>
            
              <c:forEach items="${userBean.users}" var="pageItem" varStatus="reqStatus">
            
             <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
              <td class="tbl-btm-bdr"><a href="profile.form?action=edit&object=<c:out value="${pageItem.userId}"/>"><c:out value="${pageItem.emailId}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="profile.form?action=delete&object=<c:out value="${pageItem.userId}"/>"><c:out value="${pageItem.fullName}"></c:out></a></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.roleName}"></c:out></td>
             </tr>
            
            </c:forEach>
            
            </table>
            
    </div>

</div>
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

<!--footer part ends!-->
</body>
</html>
