package paper1_GA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Mysql_connection {
	
	public static final String sDriverName = "com.mysql.jdbc.Driver";
	public static final String sDBUrl = "jdbc:mysql://10.0.0.110/sphinx";
	public static final String user="root";
	public static final String password="123456";
	
	public Connection _CONN = null;
	public PreparedStatement pst = null;
	
	public Mysql_connection(){	
		get_connection();
	}
	
	
	//取得连接
	public Connection get_connection() {
		try {			
			Class.forName(sDriverName);
			_CONN = DriverManager.getConnection(sDBUrl, user, password);
		} catch (Exception ex) {
			ex.printStackTrace();  
		}
		return _CONN;
	}
	
	public  PreparedStatement execute(String sql) throws SQLException{
		try {
			pst = _CONN.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
		
	}
	
	//关闭连接
	public void CloseConn()
	{
		try {
			this._CONN.close();
			this.pst.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
 
	

