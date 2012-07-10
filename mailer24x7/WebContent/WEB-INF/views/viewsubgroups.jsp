<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	
	 
  </script>


<form:form action="subscriber.form" method="POST" commandName="subscriberHomeBean">


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
    
        <div id="content_1" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Group Name</td>
            <td class="table-heading">Active</td>
            <td class="table-heading">Unsubscribed</td>
            <td class="table-heading">Bounced</td>
            <td class="table-heading">Last Modified</td>
            </tr>
            <c:choose>
            
            <c:when test="${subscriberHomeBean.subscriberListNotEmpty}">
              <c:forEach items="${subscriberHomeBean.subscriberList}" var="pageItem" varStatus="reqStatus">
            
             <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
             
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=showGroup&object=<c:out value="${pageItem.subscriberListId}"/>"><c:out value="${pageItem.subscriberListName}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=deleteGroup&object=<c:out value="${pageItem.subscriberListId}"/>"><c:out value="${pageItem.activeCount}"></c:out></a></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.unsubscriberCount}"></c:out></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.bouncedCount}"></c:out></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.lastModifiedTime}"></c:out></td>
             </tr>
            
            </c:forEach>
            </c:when>
            
            <c:otherwise>
            <tr>
              <td colspan="4" class="tbl-btm-bdr">No Groups are created. <a href="../usr/subscriber.form?action=new">Create</a> a Group.</td>
             </tr>
            </c:otherwise>
            </c:choose>
            
            </table>
        </div>
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
