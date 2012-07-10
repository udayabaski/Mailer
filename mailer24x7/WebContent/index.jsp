<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String encodedBasePath = URLEncoder.encode(basePath,"ISO-8859-1");
basePath +="usr/home.form";
%>
<SCRIPT>

 window.location = "<%=basePath%>";

</script>
