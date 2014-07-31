package com.chinaops.db.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chinaops.db.util.Conn;


public class CheckTable {
	
	public static void main(String[] args){
		String userName = "billing";
		String password = "china-ops";
		String url = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
		System.out.println(new CheckTable().isExist(userName,password,url,"company_res_order_cfg"));
	}
	
	public boolean isExist(String userName,String password,String url,String tableName){
		
		Connection conn = Conn.getConn(url, userName, password);
		ResultSet rs = null;
		try {
			DatabaseMetaData meta = (DatabaseMetaData)conn.getMetaData();
			rs = meta.getTables(null, null, tableName, null);
			if(rs.next()){
				// 存在返回TRUE否则返回FALSE
				System.err.println(true);
				return true;
			}/*else{
				System.err.println(false);
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
