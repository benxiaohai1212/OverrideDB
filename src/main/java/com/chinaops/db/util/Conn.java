package com.chinaops.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		}catch(Exception e) {
			System.out.println("Error loading Mysql Driver!");
			e.printStackTrace();
		} 
	}
	
	public static Connection getConn(String url,String userName,String password){
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Success Connection Mysql!");
			return conn;
		} catch (SQLException e) {
			System.out.println("Error Connection Mysql!");
			e.printStackTrace();
		}
		return null;
	} 
}
