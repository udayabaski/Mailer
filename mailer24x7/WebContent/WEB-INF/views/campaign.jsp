<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../script/jquery-min.js"></script>
 <script>
	function alertValues()
	{
	  //window.alert(document.getElementById("editor1").value);	
	  window.alert(CKEDITOR.instances.editorContent.getData());
	}
	
	function showContent()
	{
	  writeConsole(CKEDITOR.instances.editorContent.getData());
	}
	function writeConsole(content) {
      top.consoleRef=window.open('','Preview',
       'width=600,height=650'
        +',menubar=0'
   		+',toolbar=1'
   		+',status=0'
   		+',scrollbars=1'
   		+',resizable=1')
 	 top.consoleRef.document.writeln(
  		'<html><head><title>Preview</title></head>'
   			+'<body bgcolor=white onLoad="self.focus()">'
   					+content
   		+'</body></html>'
 		)
 		top.consoleRef.document.close()
	}
	
	function sendTestMail_Ajax() {
    /*$.getJSON("/comn/seekerregform.form?action=validateEmail", { emialId: $('#emailId').val() }, function(data) {
        window.alert(data);
    });*/
    $.ajax({
        url: 'campaign.form?action=sendTestMail',
        data: ({emailId : $("#testMailId").val()}),
        success: function(data) {
          window.alert("Response is : "+data);
          //$('#emailId').html(data1);
        }
     });
   }

   function sendTestMail() {
    
     document.getElementById("actionId").value = "sendTestMail";
     document.forms[0].submit();
   }

$(document).ready(function() {

	$('#testing').click(function() {
        sendTestMail();
    });
    
});
</script>

<body>

<form:form action="campaign.form"  commandName="campaignForm" method="POST" id="campaign" enctype="multipart/form-data">

<form:hidden path="action" id="actionId"/>
<form:hidden path="campaignId" />
<form:hidden path="filename" />

<c:if test="${not empty campaignForm.testMailStatus}">
    <div class="info"><c:out value="${campaignForm.testMailStatus}"/></div>
</c:if>


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
<td class="content-heading" colspan="4"><img src="../images/email_add.png" class="top-icon-padd" />Add New email Campaign</td>
</tr>

<tr>
<td class="empty" colspan="4"></td>
</tr>
<tr>
<td width="2%"></td>
<td width="20%"><span class="formtext">Campaign Name</span></td>
<td width="38%">
  <form:input path="campaignName" id="campaignName" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="campaignName" /></span>
 </td>
<td width="44%"></td>
</tr>


<tr>
<td></td>
<td><span class="formtext">Sender Name</span></td>
<td>
  <form:input path="senderName" id="senderName" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="senderName" /></span>
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Sender Email</span></td>
<td>
  <form:input path="senderEmail" id="senderEmail" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="senderEmail" /></span>
 </td>
<td></td>
</tr>


<tr>
<td></td>
<td><span class="formtext">Subject</span></td>
<td>
  <form:input path="subject" id="subject" cssClass="inputwidth" />
  <span class="errortxt"><form:errors path="subject" /></span>
 </td>
<td></td>
</tr>

<tr><td colspan="4" class="bottom-tile" height="40"></td></tr>
<tr><td colspan="4"  height="20"></td></tr>
<tr>
<td></td>
<td><span class="formtext">Email Template</span></td>
<td>
<table cellpadding="0" cellspacing="0" width="100%">
<tr>
<td>
  <form:radiobutton path="contentType" id="contentType" cssClass="inputradio" value="RTE"/><span class="chk-box-paddingfont">Rich Text Editor</span>
</td>
<td>
 <form:radiobutton path="contentType" id="contentType" cssClass="inputradio" value="IHTML"/><span class="chk-box-paddingfont">Import your HTML</span>
</td>
</tr>
</table>
</td>
<td></td>
</tr>

<tr>
<td></td>
<td></td>
<td width="100%">
  <div class="form-divtext">Import HTML : 
    <form:input path="fileData" type="file"/>
   </div>
   <span class="errortxt"><form:errors path="fileData" /></span>
 </td>
<td></td>
</tr>


<tr>
<td></td>
<td></td>
<td>
<table cellpadding="0" cellspacing="0" width="95%"  class="form-divtext">
<tr>
<td colspan="2">
 <div >
  
  <form:textarea path="editorContent" id="editorContent" />
									<!-- <textarea id="content" name="content">&lt;p&gt;Initial value.&lt;/p&gt;</textarea>  -->
									<script type="text/javascript">
						                  CKEDITOR.replace('editorContent');
						                  /* CKEDITOR.replace( 'editorContent',
						                			{
						                				//extraPlugins : 'uicolor',
						                				language: 'fr',
						                			    language: 'en'
						                			}); */ 
					                </script>
   
 </div></td>
 
</tr>
<tr>
 <td colspan="2"><a onclick="showContent()" class="button green" href="#">Preview</a></td>
<td>
</table>
</td>
<td></td>
</tr>
<tr><td colspan="4" class="bottom-tile" height="40"></td></tr>


<tr><td colspan="4" class="empty"></td></tr>


<tr>
<td></td>
<td><span class="formtext">Subscriber SubGroup</span></td>
<td>
   <form:select path="subscriberGroup" cssClass="selbox2"
									id="sub_group">
									<form:options items="${subscriberGroupMap}" />
   </form:select> 
   <span class="errortxt"><form:errors path="subscriberGroup" /></span>
 </td>
<td></td>
</tr>

<tr>
<td></td>
<td><span class="formtext">Sending Options</span></td>
<td>
<table cellpadding="0" cellspacing="0">
<tr>
<td>
  <form:radiobutton path="sendingOption" id="sendingOption" cssClass="inputradio" value="DRAFT"/><span class="chk-box-paddingfont">Draft</span>
  <form:radiobutton path="sendingOption" id="sendingOption" cssClass="inputradio" value="NOW"/><span class="chk-box-paddingfont">Send Now</span>
 </td>
 <td> 
  <form:radiobutton path="sendingOption" id="sendingOption" cssClass="inputradio" value="LATER"/><span class="chk-box-paddingfont">Send Later</span>
 </td>
</tr>
</table>
</td>
<td></td>
</tr>


<!-- <tr>
<td></td>
<td></td>
<td><div class="form-divtext"><input value="From" type="time" class="calendarReportImg " style="position: relative; left: 40px; width: 120px; height: 19px; -moz-border-radius:0;
	-khtml-border-radius:0; height:17px;" > <input value="To" type="time" class="calendarReportImg " style="position: relative; left: 40px; width: 120px; height: 19px; -moz-border-radius:0;
	-khtml-border-radius:0; height:17px;" ></div></td>
<td></td>
</tr>-->


<tr>
<td></td>
<td></td>
<td>
  <div class="form-divtext">
      Send Test Mail ID : <form:input path="testMailId" id="testMailId" cssClass="inputwidth" /> 
      <span class="errortxt"><form:errors path="testMailId" /></span>
      <a onclick="sendTestMail()" class="button green" href="#">Send</a>
  </div>
 </td>
<td></td>
</tr>

<tr><td colspan="4" class="bottom-tile" height="40"></td></tr>

<tr><td colspan="4" height="20"></td></tr>
<tr>
<td></td>
<td></td>
<td  align="left"><a onclick="document.forms[0].submit();return false;" class="button green" href="#">Submit</a> <a class="button green" href="#">Cancel</a></td>
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


</body>

