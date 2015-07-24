<%@ page language="java"  import="java.util.*,java.text.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Employee Main Page</title>
	<%
		Calendar ca = new GregorianCalendar();
		int iTDay=ca.get(Calendar.DATE);
		int iTYear=ca.get(Calendar.YEAR);
		int iTMonth=ca.get(Calendar.MONTH);
	%>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<link rel="stylesheet" href="/resources/demos/style.css">
		<script>
			$(function() {
				$( "#datepicker" ).datepicker({
					dateFormat: "yy/mm/dd",
					buttonText: "Calendar"
				});
			});
		</script>
		<script type="text/javascript">
			function fnUpdateWorkTime() {
				var forms = document.getElementById("worktimeForm");
				if (forms.idate.value == '' ) {
					alert("Please choose date.");
					return;
				}

				if (forms.start.value == '' ) {
					alert("Please input Start time.");
					return;
				}

				if (forms.end.value == '' ) {
					alert("Please input End time.");
					return;
				}
				forms.target = "_self"; // soon send to hiddenFrame
				forms.action = "query1?param1=updateWorkTime";
				forms.submit();
			}

			function fnMontlyRecord() {
				var forms = document.getElementById("calForm");
				forms.iyear.value = "<%= iTYear %>";
				if( 10 > <%= iTMonth + 1 %>) 
					forms.imonth.value = "0"+"<%= iTMonth + 1 %>";
				else
					forms.imonth.value = "<%= iTMonth + 1 %>";
				forms.action = "query1?param1=bringMonthlyRecord";
				forms.target = "resultframe";
				
				//document.getElementById("qlink").click();
				forms.submit();
			}
		</script>
	</head>
<body onload="javascript:fnMontlyRecord();">

<%
	//allow access only if session exists
	String user = null;
	if(session.getAttribute("iusername") == null){
		System.out.println("employeeMain.jsp: "+user);
    	//response.sendRedirect("/login.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	} else
		user = (String) session.getAttribute("iusername");
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
	<table>
		<tr>
			<td><h3>Employee Main page</h3></td>
			<td>User : ${user_name} (${user_role})</td>
			<td align="right">
				<form action="query1?param1=logout" method="post">
					<input type="submit" name="logoutBtn" value="Logout" >
				</form>
			</td>
		</tr>
	</table>
	
	<table>
		<tr>
			<td valign="top" width="50%" rowspan="2"> Summary<br>of this month </td>
			<td>Total work time : </td>
			<td>000</td>
		</tr>
		<tr>
			<td>Total over time : </td>
			<td>000</td>
		</tr>
	</table>
	
	<form id="worktimeForm" method="post">
		<table>
			<tr>
				<td colspan="6"> Manage work time </td>
			</tr>
			<tr>
				<td><p>Date: <input type="text" name="idate" id="datepicker"></p></td>
				<td>Start time :</td>
				<td><input type="text" name="start"></td>
				<td>End time:</td>
				<td><input type="text" name="end"></td>
				<td><input type="button" value="Update" name="update" onClick="javascript:fnUpdateWorkTime();"></td>
			</tr>
		</table>
	</form>
	<br>
	<fieldset>
		<legend>Monthly records</legend>
		<form id="calForm" method="post">
			<input type="hidden" name="iyear">
			<input type="hidden" name="imonth">
			<iframe name="resultframe" width="100%" height="350" frameborder="0"></iframe>
		</form>
	</fieldset>
</body>
</html>