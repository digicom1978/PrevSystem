<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
<body>
<%
	//allow access only if session exists
	String user = null;
	System.out.println("here");
	if(session.getAttribute("user") == null){
		System.out.println(user);
    	//response.sendRedirect("/login.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	} else
		user = (String) session.getAttribute("user");
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
		for(Cookie cookie : cookies){
		    if(cookie.getName().equals("user")) userName = cookie.getValue();
    		if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		}
	}
%>
Hello, world!<br>
<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>
<br>
User=<%=user %>
	<UL>
		<li><a href="query1?param1=Qry1" target="resultframe">querying</a> </li>
		<li><a href="query2?param1=Qry1" target="resultframe">parsing</a> </li>
	</ul>

	<form action="query1?param1=logout" method="post">
		<input type="submit" value="Logout" >
	</form>

	<fieldset>
		<legend>Results of Queries</legend>
		<iframe name="resultframe" width="100%" height="350" frameborder="0"></iframe>
	</fieldset>
</body>
</html>