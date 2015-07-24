<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Manager Intermediate Page</title>
	</head>
<body>
	<%
		Calendar ca = new GregorianCalendar();
		int iTDay=ca.get(Calendar.DATE);
		int iTYear=ca.get(Calendar.YEAR);
		int iTMonth=ca.get(Calendar.MONTH);
	%>
<%
	//allow access only if session exists
//	String user = null;
//	if(session.getAttribute("user") == null){
//		System.out.println(user);
 //   	//response.sendRedirect("/login.jsp");
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
//		dispatcher.forward(request, response);
//	} else
//		user = (String) session.getAttribute("user");
//	String userName = null;
//	String sessionID = null;
//	Cookie[] cookies = request.getCookies();
//	if(cookies !=null){
//		for(Cookie cookie : cookies){
//		    if(cookie.getName().equals("user")) userName = cookie.getValue();
//   		if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
//		}
//	}
%>
	<table>
		<tr>
			<td><h3>Manager Intermediate page</h3></td>
			<td>User : ${user_name} (${user_role})</td>
			<td align="right">
				<form action="query1?param1=logout" method="post">
					<input type="submit" name="logoutBtn" value="Logout" >
				</form>
			</td>
		</tr>
	</table>
	
	<UL>
		<li><a href="query1?param1=manager&iyear=<%= iTYear %>&imonth=0<%= iTMonth+1 %>>" target="resultframe">Manager</a> </li>
		<li><a href="query2?param1=Qry1" target="resultframe">As Employee</a> </li>
	</ul>

</body>
</html>