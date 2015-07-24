<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<TITLE>Customers List</TITLE>
			<script>
		/**
		 * function for Add New Customer
		 */
		function fnQueryVehicles(id)
		{
			 alert(111);
			 return;
			/*var cbxids = document.getElementsByName("cbxId");
			var forms = document.getElementById("mainForm");
			forms.target = "_self";
			forms.action = "query1?param1=ListVehicles&param2="+cbxids[id-1].value;
			forms.submit();*/
		}
		
		/**
		 * function for detailed information

		function fnQueryVehicles(id)
		{
			var cbxids = document.getElementsByName("cbxId");
			var forms = document.getElementById("mainForm");
			forms.target = "_parent";
			forms.action = "query1?param1=QueryCustomerDetailInform&param2="+cbxids[id-1].value;
			forms.submit();
		}		 */
	</script>
	</HEAD>

	<BODY>

	<form id="mainForm" method="post">
		<P>
			<FONT face="Arial" size="3">
			<table border="0">
				<tr>
					<td>Chk</td>
					<td>My ID</td>
					<td>My Name</td>
				</tr>
				<c:forEach var="name" varStatus="status" items="${Results}" begin="0">
				<tr>
					<td><input type="checkbox" size="5" name="cbxId" value="<c:out value="${name[0]}"></c:out>"></td>
					<td><input type="text" size="5" name="myid" value="<c:out value="${name[0]}"></c:out>" readOnly="true"></td>
					<td><input type="text" size="15" name="myname" onClick="javascript:fnQueryVehicles(${status.count});" value="<c:out value="${name[1]}"></c:out>" readOnly="true"></td>
				</tr>
				</c:forEach>
			</table>
			</FONT>
		</P>
	</form>
	</BODY>
</HTML>