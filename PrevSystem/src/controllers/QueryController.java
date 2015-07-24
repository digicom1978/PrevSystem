package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.QueryDAO;

@WebServlet("/query1")
public class QueryController extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		response.setContentType("text/html");

		ArrayList<String[]> resultSet = null;
		QueryDAO dao = new QueryDAO();
		
		String targetView = "/WEB-INF/error.jsp";

		try
		{
			String param1 = request.getParameter("param1");
			System.out.println("param1: "+param1);
			
			if( "login".equals(param1) ) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				System.out.println(username + "/" +password);
				String[] userInfo = dao.checkAuthentication( username, password );
				if( userInfo != null ) {
					HttpSession session = request.getSession();
					session.setAttribute("iusername", userInfo[0]);
					session.setAttribute("iuserrole", userInfo[1]);
					// setting session to expiry in 30 mins
					session.setMaxInactiveInterval(30*60);
					Cookie userName = new Cookie("user", userInfo[0]);
					userName.setMaxAge(30*60);
					response.addCookie(userName);
					
					request.setAttribute("user_name", userInfo[0]);
					request.setAttribute("user_role", userInfo[1]);
					if( "M".equals(userInfo[1]) )
						targetView = "/WEB-INF/managerIn.jsp";
					else if( "E".equals(userInfo[1]) )
						targetView = "/WEB-INF/employeeMain.jsp";

				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
		            PrintWriter out= response.getWriter();
		            out.println("<font color=red>Either user name or password is wrong.</font><br>");
		            rd.include(request, response);
		            return;
				}
			} else if( "updateWorkTime".equals(param1) ) {
				// Check login status
		        /*HttpSession preSession = request.getSession(false);
		        if(preSession == null){
		        	System.out.println("User="+preSession.getAttribute("user"));
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
		            PrintWriter out= response.getWriter();
		            out.println("<font color=blue>Need to login.</font><br>");
		            rd.include(request, response);
		            return;
		        } else {
		        	System.out.println("Valid session");
		        }*/
				
				String date = request.getParameter("idate");
				String startTime = request.getParameter("start");
				String endTime = request.getParameter("end");
				System.out.println(startTime + "/" +endTime);

				// manage session
				HttpSession session = request.getSession(false);
				if(session != null){
			        System.out.println("Username="+session.getAttribute("iusername"));
			        System.out.println("Userrole="+session.getAttribute("iuserrole"));
			        
			        request.setAttribute("user_name", session.getAttribute("iusername"));
					request.setAttribute("user_role", session.getAttribute("iuserrole"));
				}

				dao.updateWorkTime((String)session.getAttribute("iusername"), date, startTime, endTime);
		        /*if(session != null){
		        	System.out.println("Why NULL?");
		            session.invalidate();
		        } else {
		        	session.setAttribute( "iusername", session.getAttribute("iusername") );
		        	session.setAttribute( "iuserrole", session.getAttribute("iuserrole") );
		        	System.out.println("Good");
		        }*/
				targetView = "/WEB-INF/employeeMain.jsp";

				/*if( userInfo != null ) {
					HttpSession session = request.getSession();
					session.setAttribute("user", userInfo[0]);
					// setting session to expiry in 30 mins
					session.setMaxInactiveInterval(30*60);
					Cookie userName = new Cookie("user", userInfo[0]);
					userName.setMaxAge(30*60);
					response.addCookie(userName);
					
					request.setAttribute("user_name", userInfo[0]);
					request.setAttribute("user_role", userInfo[1]);
					if( "M".equals(userInfo[1]) )
						targetView = "/WEB-INF/managerIn.jsp";
					else if( "E".equals(userInfo[1]) )
						targetView = "/WEB-INF/employeeMain.jsp";
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
		            PrintWriter out= response.getWriter();
		            out.println("<font color=red>Either user name or password is wrong.</font><br>");
		            rd.include(request, response);
		            return;
				}*/
			} else if( "bringMonthlyRecord".equals(param1) ) {
				// Check login status
		        /*HttpSession preSession = request.getSession(false);
		        if(preSession == null){
		        	System.out.println("User="+preSession.getAttribute("user"));
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
		            PrintWriter out= response.getWriter();
		            out.println("<font color=blue>Need to login.</font><br>");
		            rd.include(request, response);
		            return;
		        } else {
		        	System.out.println("Valid session");
		        }*/
				
				String year = request.getParameter("iyear");
				String month = request.getParameter("imonth");
				System.out.println(year + "/" +month);

				// manage session
				HttpSession session = request.getSession(false);
				if(session != null){
			        System.out.println("Username="+session.getAttribute("iusername"));
			        System.out.println("Userrole="+session.getAttribute("iuserrole"));
			        
			        request.setAttribute("user_name", session.getAttribute("iusername"));
					request.setAttribute("user_role", session.getAttribute("iuserrole"));
				}

				resultSet = dao.bringMonthlyRecord((String)session.getAttribute("iusername"), year, month);
				if( resultSet != null ) {
					
					System.out.println("Bring Monthly Rercord-2 "+resultSet.size());
					request.setAttribute("Results", resultSet);
				}
				targetView = "/WEB-INF/employeeCalendar.jsp";
		        /*if(session != null){
		        	System.out.println("Why NULL?");
		            session.invalidate();
		        } else {
		        	session.setAttribute( "iusername", session.getAttribute("iusername") );
		        	session.setAttribute( "iuserrole", session.getAttribute("iuserrole") );
		        	System.out.println("Good");
		        }*/
				targetView = "/WEB-INF/employeeCalendar.jsp";

			} else if( "manager".equals(param1) ) {
				// Check login status
		        /*HttpSession preSession = request.getSession(false);
		        if(preSession == null){
		        	System.out.println("User="+preSession.getAttribute("user"));
		            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
		            PrintWriter out= response.getWriter();
		            out.println("<font color=blue>Need to login.</font><br>");
		            rd.include(request, response);
		            return;
		        } else {
		        	System.out.println("Valid session");
		        }*/
				
				String year = request.getParameter("iyear");
				String month = request.getParameter("imonth");
				System.out.println(year + "/" +month);

				// manage session
				HttpSession session = request.getSession(false);
				if(session != null){
			        System.out.println("Username="+session.getAttribute("iusername"));
			        System.out.println("Userrole="+session.getAttribute("iuserrole"));
			        
			        request.setAttribute("user_name", session.getAttribute("iusername"));
					request.setAttribute("user_role", session.getAttribute("iuserrole"));
				}

				resultSet = dao.bringMonthlyRecordForAll((String)session.getAttribute("iusername"), year, month);
				if( resultSet != null ) {
					
					System.out.println("Bring Monthly Rercord-2 "+resultSet.size());
					request.setAttribute("Results", resultSet);
				}
				targetView = "/WEB-INF/employeeCalendar.jsp";
		        /*if(session != null){
		        	System.out.println("Why NULL?");
		            session.invalidate();
		        } else {
		        	session.setAttribute( "iusername", session.getAttribute("iusername") );
		        	session.setAttribute( "iuserrole", session.getAttribute("iuserrole") );
		        	System.out.println("Good");
		        }*/
				targetView = "/WEB-INF/employeeCalendar.jsp";

			} else if( "logout".equals(param1) ) {
				response.setContentType("text/html");
		        Cookie[] cookies = request.getCookies();
		        if(cookies != null){
			        for(Cookie cookie : cookies){
			            if(cookie.getName().equals("JSESSIONID")){
			                System.out.println("JSESSIONID="+cookie.getValue());
			                break;
			            }
			        }
		        }
		        //invalidate the session if exists
		        HttpSession session = request.getSession(false);
		        System.out.println("User="+session.getAttribute("user"));
		        if(session != null){
		            session.invalidate();
		        }
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
	            PrintWriter out= response.getWriter();
	            out.println("<font color=blue name=lout>Logged out.</font><br>");
	            rd.include(request, response);
	            return;
			} else if( "AddNewCustomer".equals(param1) ) {
//				UserVO vo = new UserVO();
//				vo.setFirstName(request.getParameter("FirstName"));
//				vo.setLastName(request.getParameter("LastName"));
//				vo.setPolicyNo(request.getParameter("PolicyNo"));
//				vo.setPhoneNo(request.getParameter("PhoneNo"));
//				vo.setEmail(request.getParameter("Email"));
//				vo.setPolicyCoverageAmount(request.getParameter("PolicyCoverageAmount"));
//				vo.setAaaMember(request.getParameter("AAAMember"));
//
//				dao.queryAddNewCustomer(vo);
//				targetView = "/WEB-INF/index.jsp";
				
			}
		}
		catch(Exception e) 
		{
			/**
			 * Exception handling, 
			 * in order to prevent that exception message is exposed to user directly. 
			 */
			e.printStackTrace();
			if( "".equals(e.getMessage()) || null == e.getMessage() )
				request.setAttribute("error", "Unknown Exception");
			else
				request.setAttribute("error", e.getMessage());
			targetView = "/WEB-INF/error.jsp";
		}

		RequestDispatcher dispatcher =
				request.getRequestDispatcher(targetView);
		dispatcher.forward(request, response);
	}
}
