<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	
	  // When the document loads do everything inside here ...
	  $(document).ready(function(){
		
		// When a link is clicked
		$("a.tab").click(function () {
			
			
			// switch all tabs off
			$(".active").removeClass("active");
			
			// switch this tab on
			$(this).addClass("active");
			
			// slide all content up
			$(".content").slideUp();
			
			// slide this content up
			var content_show = $(this).attr("title");
			$("#"+content_show).slideDown();
		  
		});
	
	  });
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
    
    	
        <ul class="tabs">
            <li><a href="#" title="content_1" class="tab active">Active </a></li>
            <li><a href="#" title="content_2" class="tab">Unsusbscribed </a></li>
            <li><a href="#" title="content_3" class="tab">Bounced </a></li>
        </ul>
        
        <div id="content_1" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Email</td>
            <td class="table-heading">Status</td>
            </tr>
            <c:choose>
            
            <c:when test="${subscriberHomeBean.subscriberListNotEmpty}">
              <c:forEach items="${subscriberHomeBean.activeSubscribers}" var="pageItem" varStatus="reqStatus">
            
             <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
             
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=editSubscriber&object=<c:out value="${pageItem.statusId}"/>&group=<c:out value="${subscriberHomeBean.subscriberGroup.subscriberListId}"/>"><c:out value="${pageItem.emailId}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=deleteSubscriber&object=<c:out value="${pageItem.statusId}"/>&group=<c:out value="${subscriberHomeBean.subscriberGroup.subscriberListId}"/>"><c:out value="${pageItem.statusId}"></c:out></a></td>
             </tr>
            
            </c:forEach>
            </c:when>
            
            <c:otherwise>
            <tr>
              <td colspan="4" class="tbl-btm-bdr">No Subscriber groups are created. <a href="../usr/campaign.form?action=new">Create</a> a Group.</td>
             </tr>
            </c:otherwise>
            </c:choose>
            
            </table>
        </div>
        <div id="content_2" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Email</td>
            <td class="table-heading">Status</td>
            </tr>
            
             <c:forEach items="${subscriberHomeBean.unsubscribedSubscribers}" var="pageItem" varStatus="reqStatus">
             
            <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=editSubscriber&object=<c:out value="${pageItem.statusId}"/>"><c:out value="${pageItem.emailId}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=deleteSubscriber&object=<c:out value="${pageItem.statusId}"/>"><c:out value="${pageItem.statusId}"></c:out></a></td>
            </tr>
            
            </c:forEach>
            
            </table>
        </div>
        <div id="content_3" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Email</td>
            <td class="table-heading">Status</td>
            </tr>
            
            <c:forEach items="${subscriberHomeBean.bouncedSubscribers}" var="pageItem" varStatus="reqStatus">
            
            <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=editSubscriber&object=<c:out value="${pageItem.statusId}"/>"><c:out value="${pageItem.emailId}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="subscriber.form?action=deleteSubscriber&object=<c:out value="${pageItem.statusId}"/>"><c:out value="${pageItem.statusId}"></c:out></a></td>
            </tr>
            
            </c:forEach>
           
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
