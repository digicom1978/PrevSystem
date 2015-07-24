<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

		<title>Insert title here</title>
		<style>
			body
			{
				font-family:Verdana, Arial, Helvetica, sans-serif
			}
			.dsb
			{
				background-color:#EEEEEE
			}
		</style>

	</head>
<body>
<%!
public int nullIntconv(String inv)
{   
	int conv=0;
	     
	try{
		conv=Integer.parseInt(inv);
	}
	catch(Exception e)
	{}
	return conv;
}
%>
<%
	int iYear=nullIntconv(request.getParameter("iYear"));
	int iMonth=nullIntconv(request.getParameter("iMonth"));
	
	Calendar ca = new GregorianCalendar();
	int iTDay=ca.get(Calendar.DATE);
	int iTYear=ca.get(Calendar.YEAR);
	int iTMonth=ca.get(Calendar.MONTH);
	
	if(iYear==0)
	{
	     iYear=iTYear;
	     iMonth=iTMonth;
	}
	
	GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 
	
	int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int weekStartDay=cal.get(Calendar.DAY_OF_WEEK);
	
	cal = new GregorianCalendar (iYear, iMonth, days); 
	int iTotalweeks=cal.get(Calendar.WEEK_OF_MONTH);

%>

<body>
<form name="frm" method="post">
<table width="50%" border="0" cellspacing="0" cellpadding="0">
	<c:forEach var="name" varStatus="status" items="${Results}" begin="0">
	<tr>
		<td><input type="text" name="date" value="<c:out value="${name[0]}"></c:out>" readOnly="true"></td>
		<td><input type="text" name="total" value="<c:out value="${name[1]}"></c:out>" readOnly="true"></td>
		<c:if test = "${name[2] == 'O'}">
			<td><font color="RED"> Over time</font></td>
		</c:if>
		<c:if test = "${name[2] == 'N'}">
			<td><font color="BLACK"> Regular</font></td>
		</c:if>
		
		<c:if test="${null eq name[2]}">
			<td><font color="BLUE"> No time</font></td>
		</c:if>
				
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>