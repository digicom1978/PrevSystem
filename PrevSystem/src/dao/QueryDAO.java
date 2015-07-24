package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import utils.Utils;

public class QueryDAO {

	public ArrayList<String[]> queryList(String param1) throws Exception {
		ArrayList<String[]> resultSet = null;
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null ) throw new Exception("Exception: No Context");

			// /jdbc/DBPool is the name of the resource above 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MyLocalDB");

			PreparedStatement pstmt = null;
			ResultSet rst = null;

			if (ds != null) {
				Connection conn = ds.getConnection();

				if(conn != null) {
					  try {
						  
						  String sql = "SELECT myid, myname FROM test WHERE myid = ? ";
						  pstmt = conn.prepareStatement(sql);
						  pstmt.setInt(1,1);

						  rst = pstmt.executeQuery();

						  resultSet = new ArrayList<String[]>();
						  String[] strVo = null;
						  while(rst.next())
						  {
							  strVo = new String[2];
							  strVo[0] = rst.getString("myid");
							  strVo[1] = rst.getString("myname");
							  resultSet.add(strVo);
						  }

					  } catch (Exception e) {
						  throw e;
					  } finally {
						  try {
							  if( rst != null ) rst.close();
							  if( pstmt != null ) pstmt.close();
							  if( conn != null ) conn.close();
							  if( ctx != null ) ctx.close();
				            } catch (SQLException e) {
				                System.out.println("Exception in closing DB resources");
				                throw e;
				            } catch (NamingException e) {
				                System.out.println("Exception in closing Context");
				                throw e;
				            }
					  }
				}
			}
		} catch(Exception e) {
			System.out.println("Exception in queryList()");
			e.printStackTrace();
			throw new Exception("Exception in queryList()");
		}
		
		return resultSet;
	}
	
	
	public String[] checkAuthentication(String username, String password) throws Exception {
		String[] strVo = null;

		try {
			Context ctx = new InitialContext();
			if(ctx == null ) throw new Exception("Exception: No Context");

			// /jdbc/DBPool is the name of the resource above 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MyLocalDB");

			PreparedStatement pstmt = null;
			ResultSet rst = null;

			if (ds != null) {
				Connection conn = ds.getConnection();

				if(conn != null) {
					try {
						  
						String sql = "SELECT username, role FROM user_info WHERE username = ? AND password = ?";
						// TODO check special character
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1,username);
						pstmt.setString(2,password);

						rst = pstmt.executeQuery();

						while(rst.next())
						{
							strVo = new String[2];
							strVo[0] = rst.getString("username");

							if( "Manager".equals(rst.getString("role")) )
								strVo[1] = "M";
							else if( "Employee".equals(rst.getString("role")) )
								strVo[1] = "E";
						}

					} catch (Exception e) {
						throw e;
					} finally {
						try {
							if( rst != null ) rst.close();
							if( pstmt != null ) pstmt.close();
							if( conn != null ) conn.close();
							if( ctx != null ) ctx.close();
						} catch (SQLException e) {
							System.out.println("Exception in closing DB resources");
							throw e;
						} catch (NamingException e) {
							System.out.println("Exception in closing Context");
							throw e;
						}
					}
				}
			}
		} catch(Exception e) {
			System.out.println("Exception in checkAuthentication()");
			e.printStackTrace();
			throw new Exception("Exception in checkAuthentication()");
		}

		return strVo;
	}
	
	public int updateWorkTime(String username, String date, String startTime, String endTime) throws Exception {
		int result = 0;

		try {
			Context ctx = new InitialContext();
			if(ctx == null ) throw new Exception("Exception: No Context");

			// /jdbc/DBPool is the name of the resource above 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MyLocalDB");

			PreparedStatement pstmt = null, pstmtCheck = null, pstmtUpdate = null;
			ResultSet rst = null;

			if (ds != null) {
				Connection conn = ds.getConnection();

				if(conn != null) {
					try {
						  
						String sqlUser = "SELECT _id FROM user_info WHERE username = ? ";
						// TODO check special character
						pstmt = conn.prepareStatement(sqlUser);
						pstmt.setString(1,username);

						rst = pstmt.executeQuery();
						
						int userId = 0;

						while(rst.next()) {
							userId = rst.getInt("_id");

							if( userId != 0 ) {
								;
							}
						}
						
						String sqlCheckExist = "SELECT _id, date, start, end, totalperday, overperday FROM employee_record " +
								" WHERE date = ? AND uid = ? ";
						// TODO check special character
						pstmtCheck = conn.prepareStatement(sqlCheckExist);
						pstmtCheck.setString(1,date);
						pstmtCheck.setInt(2,userId);

						rst = pstmtCheck.executeQuery();
						
						if( rst.next() ) {
							Utils ut = new Utils();

							int _id = rst.getInt("_id");
							String sqlUpdate = "UPDATE employee_record SET start = ?, end = ?, totalperday = ?, overperday = ? WHERE _id = ? ";
							// TODO check special character
							pstmtUpdate = conn.prepareStatement(sqlUpdate);
							pstmtUpdate.setString(1,startTime);
							pstmtUpdate.setString(2,endTime);
							String totalTime = ut.getTotalTimePerDay(date+" "+startTime, date+" "+endTime);
							pstmtUpdate.setString(3,totalTime);
							pstmtUpdate.setString(4,ut.getOverTimePerday(totalTime));
							pstmtUpdate.setInt(5,_id);
							
							result = pstmtUpdate.executeUpdate();
						} else {
							Utils ut = new Utils();

//							INSERT INTO employee_record (uid, date, start, end, totalperday, overperday) VALUES (7,'2015/05/08', '09:55', '18:45', '08:50', '00:50');
							String sqlUpdate = "INSERT INTO employee_record (uid, date, start, end, totalperday, overperday) VALUES (?,?, ?, ?, ?, ?); ";
							// TODO check special character
							pstmtUpdate = conn.prepareStatement(sqlUpdate);
							pstmtUpdate.setInt(1,userId);
							pstmtUpdate.setString(2,date);
							pstmtUpdate.setString(3,startTime);
							pstmtUpdate.setString(4,endTime);
							String totalTime = ut.getTotalTimePerDay(date+" "+startTime, date+" "+endTime);
							pstmtUpdate.setString(5,totalTime);
							pstmtUpdate.setString(6,ut.getOverTimePerday(totalTime));
							
							result = pstmtUpdate.executeUpdate();
						}

					} catch (Exception e) {
						throw e;
					} finally {
						try {
							if( rst != null ) rst.close();
							if( pstmt != null ) pstmt.close();
							if( conn != null ) conn.close();
							if( ctx != null ) ctx.close();
						} catch (SQLException e) {
							System.out.println("Exception in closing DB resources");
							throw e;
						} catch (NamingException e) {
							System.out.println("Exception in closing Context");
							throw e;
						}
					}
				}
			}
		} catch(Exception e) {
			System.out.println("Exception in checkAuthentication()");
			e.printStackTrace();
			throw new Exception("Exception in checkAuthentication()");
		}

		return result;
	}
	
	
	
	public ArrayList<String[]> bringMonthlyRecord(String username, String year, String month) throws Exception {
		ArrayList<String[]> resultSet = null;
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null ) throw new Exception("Exception: No Context");

			// /jdbc/DBPool is the name of the resource above 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MyLocalDB");

			PreparedStatement pstmt = null, pstmtRec = null;;
			ResultSet rst = null;

			if (ds != null) {
				Connection conn = ds.getConnection();

				if(conn != null) {
					try {
						String sqlUser = "SELECT _id FROM user_info WHERE username = ? ";
						pstmt = conn.prepareStatement(sqlUser);
						pstmt.setString(1,username);

						rst = pstmt.executeQuery();
						
						int _id = 0;

						while(rst.next()) {
							_id = rst.getInt("_id");

							if( _id != 0 ) {
								;
							}
						}
						  
						String sqlRecord = "SELECT date, start, end, totalperday, overperday FROM employee_record WHERE uid = ? AND SUBSTRING(date, 1, 7) = ? ";
						pstmtRec = conn.prepareStatement(sqlRecord);
						pstmtRec.setInt(1,_id);
						pstmtRec.setString(2,year+"/"+month);

						rst = pstmtRec.executeQuery();

						resultSet = new ArrayList<String[]>();
						String[] strVo = null;
						while(rst.next())
						{
							strVo = new String[3];
							strVo[0] = rst.getString("date");
							strVo[1] = rst.getString("totalperday");
							strVo[2] = null;
							if( "08:00".equals(strVo[1]) || "8:0".equals(strVo[1]) ) 
								strVo[2] = "N";
							if( !"0:0".equals(rst.getString("overperday")) && !"00:00".equals(rst.getString("overperday")) )
								strVo[2] = "O";
							System.out.println("Result: "+strVo[0]+"/"+strVo[1]+"/"+strVo[2]+"?"+rst.getString("overperday"));
							resultSet.add(strVo);
						}

					  } catch (Exception e) {
						  throw e;
					  } finally {
						  try {
							  if( rst != null ) rst.close();
							  if( pstmt != null ) pstmt.close();
							  if( conn != null ) conn.close();
							  if( ctx != null ) ctx.close();
				            } catch (SQLException e) {
				                System.out.println("Exception in closing DB resources");
				                throw e;
				            } catch (NamingException e) {
				                System.out.println("Exception in closing Context");
				                throw e;
				            }
					  }
				}
			}
		} catch(Exception e) {
			System.out.println("Exception in queryList()");
			e.printStackTrace();
			throw new Exception("Exception in queryList()");
		}
		
		return resultSet;
	}
	
	public ArrayList<String[]> bringMonthlyRecordForAll(String usernameMgr, String year, String month) throws Exception {
		ArrayList<String[]> resultSet = null;
		try
		{
			Context ctx = new InitialContext();
			if(ctx == null ) throw new Exception("Exception: No Context");

			// /jdbc/DBPool is the name of the resource above 
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MyLocalDB");

			PreparedStatement pstmt = null, pstmtRec = null;;
			ResultSet rst = null;

			if (ds != null) {
				Connection conn = ds.getConnection();

				if(conn != null) {
					try {
						String sqlUser = "SELECT _id FROM user_info WHERE username = ? ";
						pstmt = conn.prepareStatement(sqlUser);
						pstmt.setString(1,usernameMgr);

						rst = pstmt.executeQuery();
						
						int _id = 0;

						while(rst.next()) {
							_id = rst.getInt("_id");

							if( _id != 0 ) {
								;
							}
						}

						System.out.println("mgrID: "+_id+" ? "+year+"/"+month);
						String sqlRecord = "SELECT eu.username user, date, start, end, totalperday, overperday FROM employee_record e, user_info su, user_info eu "+ 
										   " WHERE su._id = ? AND su._id = eu.supervisor " +
										     " AND eu._id = e.uid AND SUBSTRING(date, 1, 7) = ? ";
						pstmtRec = conn.prepareStatement(sqlRecord);
						pstmtRec.setInt(1,_id);
						pstmtRec.setString(2,year+"/"+month);

						rst = pstmtRec.executeQuery();

						resultSet = new ArrayList<String[]>();
						String[] strVo = null;
						while(rst.next())
						{
							strVo = new String[2];
							strVo[0] = rst.getString("user");
							strVo[1] = rst.getString("totalperday");
							System.out.println("Result: "+strVo[0]+"/"+strVo[1]+"?"+rst.getString("overperday"));
							resultSet.add(strVo);
						}

					  } catch (Exception e) {
						  throw e;
					  } finally {
						  try {
							  if( rst != null ) rst.close();
							  if( pstmt != null ) pstmt.close();
							  if( conn != null ) conn.close();
							  if( ctx != null ) ctx.close();
				            } catch (SQLException e) {
				                System.out.println("Exception in closing DB resources");
				                throw e;
				            } catch (NamingException e) {
				                System.out.println("Exception in closing Context");
				                throw e;
				            }
					  }
				}
			}
		} catch(Exception e) {
			System.out.println("Exception in queryList()");
			e.printStackTrace();
			throw new Exception("Exception in queryList()");
		}
		
		return resultSet;
	}
}
