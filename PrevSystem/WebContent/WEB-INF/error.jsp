<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<TITLE>Error Page</TITLE>
	</HEAD>

	<BODY>
		<P>
			<FONT face="Arial" size="3">
			There is an error with below code.</br>
				<c:forEach var="message" items="${error}" begin="0">
					<c:out value="${message}"></c:out>
				</c:forEach>
			</FONT>
		</P>
	</BODY>
</HTML>