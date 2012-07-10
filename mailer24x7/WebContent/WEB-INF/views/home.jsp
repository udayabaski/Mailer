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


<form:form action="home.form" method="POST" commandName="homeBean">


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
            <li><a href="#" title="content_1" class="tab active">Campaigns </a></li>
            <li><a href="#" title="content_2" class="tab">Scheduled Campaigns </a></li>
            <li><a href="#" title="content_3" class="tab">Completed Campaigns </a></li>
        </ul>
        
        <div id="content_1" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Name</td>
            <td class="table-heading">Subject</td>
            <td class="table-heading">Created By</td>
            <td class="table-heading">Last Modified</td>
            </tr>
            <c:choose>
            
            <c:when test="${homeBean.draftsNotEmpty}">
              <c:forEach items="${homeBean.draftCampaigns}" var="pageItem" varStatus="reqStatus">
            
             <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
             
              <td class="tbl-btm-bdr"><a href="campaign.form?action=edit&object=<c:out value="${pageItem.campaignId}"/>"><c:out value="${pageItem.campaignName}"></c:out></a></td>
              <td class="tbl-btm-bdr"><a href="campaign.form?action=delete&object=<c:out value="${pageItem.campaignId}"/>"><c:out value="${pageItem.subject}"></c:out></a></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.createdEmailId}"></c:out></td>
              <td class="tbl-btm-bdr"><c:out value="${pageItem.lastModifiedTime}"></c:out></td>
             </tr>
            
            </c:forEach>
            </c:when>
            
            <c:otherwise>
            <tr>
              <td colspan="4" class="tbl-btm-bdr">No Campaigns are created. <a href="../usr/campaign.form?action=new">Create</a> a campaign.</td>
             </tr>
            </c:otherwise>
            </c:choose>
            
            </table>
        </div>
        <div id="content_2" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Name</td>
            <td class="table-heading">Subject</td>
            <td class="table-heading">Created By</td>
            <td class="table-heading">Shedule Time</td>
            </tr>
            
             <c:forEach items="${homeBean.scheduledCampaigns}" var="pageItem" varStatus="reqStatus">
             
            <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
             <td class="tbl-btm-bdr"><c:out value="${pageItem.campaignName}"></c:out></td>
             <td class="tbl-btm-bdr"><c:out value="${pageItem.subject}"></c:out></td>
			 <td class="tbl-btm-bdr"><c:out value="${pageItem.createdEmailId}"></c:out></td>
             <td class="tbl-btm-bdr"><c:out value="${pageItem.scheduledOn}"></c:out></td>
            </tr>
            
            </c:forEach>
            
            </table>
        </div>
        <div id="content_3" class="content">
        	<table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            <td class="table-heading">Name</td>
            <td class="table-heading">Created By</td>
            <td class="table-heading">Last Modified</td>
           <td class="table-heading">Opened</td>
           <td class="table-heading">Clicked</td>
           <td class="table-heading">Bounced</td>
            </tr>
            
            <c:forEach items="${homeBean.completedCampaigns}" var="pageItem" varStatus="reqStatus">
            
            <tr onmouseout="this.className='emailcampheader'" onmouseover="this.className='emailcampHeaderHover'" class="emailcampheader">
            <td class="tbl-btm-bdr"><c:out value="${pageItem.campaignName}"></c:out></td>
            <td class="tbl-btm-bdr"><c:out value="${pageItem.createdEmailId}"></c:out></td>
            <td class="tbl-btm-bdr"><c:out value="${pageItem.lastModifiedTime}"></c:out></td>
            <td class="tbl-btm-bdr"><c:out value="${pageItem.opened}"></c:out></td>
            <td class="tbl-btm-bdr"><c:out value="${pageItem.clicked}"></c:out></td>
            <td class="tbl-btm-bdr"><c:out value="${pageItem.bounced}"></c:out></td>           
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
