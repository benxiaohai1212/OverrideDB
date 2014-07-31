package com.chinaops.db.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.chinaops.db.entity.Dept;
import com.chinaops.db.util.Conn;

public class CreateTable {
	/*
	public static final String Y_RACENTER_USER = "root";
	public static final String Y_RACENTER_PASS = "root";
	public static final String Y_RACENTER_URL = "jdbc:mysql://localhost:3306/ecloud_auth";
	
	public static final String Y_METADATE_USER = "root";
	public static final String Y_METADATE_PASS = "root";
	public static final String Y_METADATE_URL = "jdbc:mysql://localhost:3306/ecloud_metadata";
	
	public static final String Y_BILLING_USER = "root";
	public static final String Y_BILLING_PASS = "root";
	public static final String Y_BILLING_URL = "jdbc:mysql://localhost:3306/ecloud_billing";
	
	public static final String M_USER = "root";
	public static final String M_PASS = "root";
	public static final String M_URL = "jdbc:mysql://localhost:3306/ecloud_billing";
	*/
	/*
	public static final String Y_RACENTER_USER = "racenter";
	public static final String Y_RACENTER_PASS = "china-ops";
	public static final String Y_RACENTER_URL = "jdbc:mysql://172.16.56.62:3306/ecloud_auth";
	
	public static final String Y_METADATE_USER = "metadata";
	public static final String Y_METADATE_PASS = "china-ops";
	public static final String Y_METADATE_URL = "jdbc:mysql://172.16.56.64:3306/ecloud_metadata";
	
	public static final String Y_BILLING_USER = "billing";
	public static final String Y_BILLING_PASS = "china-ops";
	public static final String Y_BILLING_URL = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
	
	public static final String M_USER = "billing";
	public static final String M_PASS = "china-ops";
	public static final String M_URL = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
	*/
	
	public static final String Y_RACENTER_USER = GetProperties.readValue("Y_RACENTER_USER");
	public static final String Y_RACENTER_PASS = GetProperties.readValue("Y_RACENTER_PASS");
	public static final String Y_RACENTER_URL = GetProperties.readValue("Y_RACENTER_URL");
	
	public static final String Y_METADATE_USER = GetProperties.readValue("Y_METADATE_USER");
	public static final String Y_METADATE_PASS = GetProperties.readValue("Y_METADATE_PASS");
	public static final String Y_METADATE_URL = GetProperties.readValue("Y_METADATE_URL");
	
	public static final String Y_BILLING_USER = GetProperties.readValue("Y_BILLING_USER");
	public static final String Y_BILLING_PASS = GetProperties.readValue("Y_BILLING_PASS");
	public static final String Y_BILLING_URL = GetProperties.readValue("Y_BILLING_URL");
	
	public static final String M_USER = GetProperties.readValue("M_USER");
	public static final String M_PASS = GetProperties.readValue("M_PASS");
	public static final String M_URL = GetProperties.readValue("M_URL");
	
	public static void main(String[] args) {
		CreateTable c = new CreateTable();
		// ***Tables 为写入如数据的操作，数据库连接的是目标数据，方法内调用了***SQL
		// ***SQL 为查询要写入数据的sql语句，数据库连接的是源数据
		c.createTables();
		c.insertSQLTable();
		c.updateResourcesTable();
		c.updateOrderTable();
		System.out.println("Success");
		/*
		List<String> b = c.updateResourcesSQL();
		int j = 0;
		if(b != null && b.size()>0){
			for(String s:b){
				System.out.println(j+" ** "+s);
				j++;
			}
		}
		
		List<String> orders = c.updateOrderSQL();
		if(orders != null && orders.size()>0){
			for(int i=0;i<orders.size();i++){
				System.out.println(i+" ** "+orders.get(i));
				if(i%19 == 0 && i != 0){
					System.out.println("--------------------------------------------------------------------------------------------------------------------");
				}
				j++;
			}
		}
		System.out.println("================================================ "+j+" ================================================");
		*/
	}
//	OK
	public void createTables(){
		CheckTable ct = new CheckTable();
//		要构建的数据库的SQL语句信息
//		String userName = "billing";
//		String password = "china-ops";
//		String url = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
		Connection conn = null;
		Statement stmt = null;
		if(!ct.isExist(M_USER,M_PASS,M_URL,"company_res_order_cfg")){
			try {
				conn = Conn.getConn(M_URL, M_USER, M_PASS);
				stmt = conn.createStatement();
				String sql="CREATE TABLE company_res_order_cfg ("+
							  "id int(11) NOT NULL AUTO_INCREMENT,"+
							  "company_id varchar(25) DEFAULT NULL COMMENT '公司ID',"+
							  "max_instances int(11) DEFAULT '0' COMMENT '最大云主机数量',"+
							  "max_volumes int(11) DEFAULT '0' COMMENT '最大存储数量',"+
							  "max_snapshots int(11) DEFAULT '0' COMMENT '最大快照数量',"+
							  "min_ebs_size int(11) DEFAULT '0' COMMENT '最小ebs启动存储大小',"+
							  "max_ebs_size int(11) DEFAULT '0' COMMENT '最大ebs启动存储大小',"+
							  "max_security_groups int(11) DEFAULT '0' COMMENT '最大安全规则组（虚拟网络防火墙）数量',"+
							  "max_keypairs int(11) DEFAULT '0' COMMENT '最大密钥数量',"+
							  "ecu_mon_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包月数量',"+
							  "vol_mon_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包月大小',"+
							  "snap_mon_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包月大小',"+
							  "ha_mon_count int(11) DEFAULT '0' COMMENT 'HA(主机保护)产品(ha)包月数量',"+
							  "elb_mon_count int(11) DEFAULT '0' COMMENT 'Elb产品(elb)包天数量',"+
							  "ip_mon_count int(11) DEFAULT '0' COMMENT 'IP产品(ip)包月数量',"+
							  "ecu_day_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包天数量',"+
							  "vol_day_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包天小大',"+
							  "snap_day_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包天小大',"+
							  "ecu_hour_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)计时数量',"+
							  "vol_hour_size int(11) DEFAULT '0' COMMENT '存储产品(volume)计时小大',"+
							  "snap_hour_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)计时大小',"+
							  "create_time datetime DEFAULT NULL COMMENT '创建时间',"+
							  "update_time datetime DEFAULT NULL COMMENT '更新时间',"+
							  "PRIMARY KEY (id)"+
							") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ;";
				System.out.println(stmt.executeUpdate(sql)+"  "+sql);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try{
					stmt.close();
					conn.close();
				}catch(Exception ex){
					ex.getStackTrace();
				}
			}
		}else{
			try {
				conn = Conn.getConn(M_URL, M_USER, M_PASS);
				stmt = conn.createStatement();
				String dropsql = "DROP TABLE IF EXISTS company_res_order_cfg;";
				System.out.println(stmt.executeUpdate(dropsql)+"  "+dropsql);
				String sql="CREATE TABLE company_res_order_cfg ("+
							  "id int(11) NOT NULL AUTO_INCREMENT,"+
							  "company_id varchar(25) DEFAULT NULL COMMENT '公司ID',"+
							  "max_instances int(11) DEFAULT '0' COMMENT '最大云主机数量',"+
							  "max_volumes int(11) DEFAULT '0' COMMENT '最大存储数量',"+
							  "max_snapshots int(11) DEFAULT '0' COMMENT '最大快照数量',"+
							  "min_ebs_size int(11) DEFAULT '0' COMMENT '最小ebs启动存储大小',"+
							  "max_ebs_size int(11) DEFAULT '0' COMMENT '最大ebs启动存储大小',"+
							  "max_security_groups int(11) DEFAULT '0' COMMENT '最大安全规则组（虚拟网络防火墙）数量',"+
							  "max_keypairs int(11) DEFAULT '0' COMMENT '最大密钥数量',"+
							  "ecu_mon_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包月数量',"+
							  "vol_mon_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包月大小',"+
							  "snap_mon_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包月大小',"+
							  "ha_mon_count int(11) DEFAULT '0' COMMENT 'HA(主机保护)产品(ha)包月数量',"+
							  "elb_mon_count int(11) DEFAULT '0' COMMENT 'Elb产品(elb)包天数量',"+
							  "ip_mon_count int(11) DEFAULT '0' COMMENT 'IP产品(ip)包月数量',"+
							  "ecu_day_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包天数量',"+
							  "vol_day_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包天小大',"+
							  "snap_day_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包天小大',"+
							  "ecu_hour_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)计时数量',"+
							  "vol_hour_size int(11) DEFAULT '0' COMMENT '存储产品(volume)计时小大',"+
							  "snap_hour_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)计时大小',"+
							  "create_time datetime DEFAULT NULL COMMENT '创建时间',"+
							  "update_time datetime DEFAULT NULL COMMENT '更新时间',"+
							  "PRIMARY KEY (id)"+
							") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
			
				
				System.out.println(stmt.executeUpdate(sql)+"  "+sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					stmt.close();
					stmt = null;
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
//		要构建的数据库的SQL语句信息
//		String userName = "root";
//		String password = "root";
//		String url = "jdbc:mysql://localhost:3306/ecloud_billing";
		if(!ct.isExist(M_USER,M_PASS,M_URL,"dept_res_order_cfg")){
			try {
				conn = Conn.getConn(M_URL, M_USER, M_PASS);
				stmt = conn.createStatement();
				String sql="CREATE TABLE dept_res_order_cfg ("+
						"id int(11) NOT NULL AUTO_INCREMENT,"+
						"department_id varchar(25) DEFAULT NULL COMMENT '部门ID',"+
						"company_id varchar(25) DEFAULT NULL COMMENT '公司ID',"+
						"max_instances int(11) DEFAULT '0' COMMENT '最大云主机数量',"+
						"max_volumes int(11) DEFAULT '0' COMMENT '最大存储数量',"+
						"max_snapshots int(11) DEFAULT '0' COMMENT '最大快照数量',"+
						"min_ebs_size int(11) DEFAULT '0' COMMENT '最小ebs启动存储大小',"+
						"max_ebs_size int(11) DEFAULT '0' COMMENT '最大ebs启动存储大小',"+
						"max_security_groups int(11) DEFAULT '0' COMMENT '最大安全规则组（虚拟网络防火墙）数量',"+
						"max_keypairs int(11) DEFAULT '0' COMMENT '最大密钥数量',"+
						"ecu_mon_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包月数量',"+
						"vol_mon_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包月大小',"+
						"snap_mon_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包月大小',"+
						"ha_mon_count int(11) DEFAULT '0' COMMENT 'HA(主机保护)产品(ha)包月数量',"+
						"elb_mon_count int(11) DEFAULT '0' COMMENT 'Elb产品(elb)包天数量',"+
						"ip_mon_count int(11) DEFAULT '0' COMMENT 'IP产品(ip)包月数量',"+
						"ecu_day_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包天数量',"+
						"vol_day_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包天小大',"+
						"snap_day_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包天小大',"+
						"ecu_hour_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)计时数量',"+
						"vol_hour_size int(11) DEFAULT '0' COMMENT '存储产品(volume)计时小大',"+
						"snap_hour_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)计时大小',"+
						"create_time datetime DEFAULT NULL COMMENT '创建时间',"+
						"update_time datetime DEFAULT NULL COMMENT '更新时间',"+
						"PRIMARY KEY (id)"+
						") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
				System.out.println(stmt.executeUpdate(sql)+"  "+sql);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try{
					stmt.close();
					conn.close();
				}catch(Exception ex){
					ex.getStackTrace();
				}
			}
		}else{
			try {
				conn = Conn.getConn(M_URL, M_USER, M_PASS);
				stmt = conn.createStatement();
				String dropSql = "DROP TABLE IF EXISTS dept_res_order_cfg;";
				System.out.println(stmt.executeUpdate(dropSql)+"  " +dropSql);
				String sql="CREATE TABLE dept_res_order_cfg ("+
						"id int(11) NOT NULL AUTO_INCREMENT,"+
						"department_id varchar(25) DEFAULT NULL COMMENT '部门ID',"+
						"company_id varchar(25) DEFAULT NULL COMMENT '公司ID',"+
						"max_instances int(11) DEFAULT '0' COMMENT '最大云主机数量',"+
						"max_volumes int(11) DEFAULT '0' COMMENT '最大存储数量',"+
						"max_snapshots int(11) DEFAULT '0' COMMENT '最大快照数量',"+
						"min_ebs_size int(11) DEFAULT '0' COMMENT '最小ebs启动存储大小',"+
						"max_ebs_size int(11) DEFAULT '0' COMMENT '最大ebs启动存储大小',"+
						"max_security_groups int(11) DEFAULT '0' COMMENT '最大安全规则组（虚拟网络防火墙）数量',"+
						"max_keypairs int(11) DEFAULT '0' COMMENT '最大密钥数量',"+
						"ecu_mon_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包月数量',"+
						"vol_mon_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包月大小',"+
						"snap_mon_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包月大小',"+
						"ha_mon_count int(11) DEFAULT '0' COMMENT 'HA(主机保护)产品(ha)包月数量',"+
						"elb_mon_count int(11) DEFAULT '0' COMMENT 'Elb产品(elb)包天数量',"+
						"ip_mon_count int(11) DEFAULT '0' COMMENT 'IP产品(ip)包月数量',"+
						"ecu_day_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)包天数量',"+
						"vol_day_size int(11) DEFAULT '0' COMMENT '存储产品(volume)包天小大',"+
						"snap_day_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)包天小大',"+
						"ecu_hour_count float(11,2) DEFAULT '0.00' COMMENT 'ECU产品(ecu)计时数量',"+
						"vol_hour_size int(11) DEFAULT '0' COMMENT '存储产品(volume)计时小大',"+
						"snap_hour_size int(11) DEFAULT '0' COMMENT '快照产品(snapshot)计时大小',"+
						"create_time datetime DEFAULT NULL COMMENT '创建时间',"+
						"update_time datetime DEFAULT NULL COMMENT '更新时间',"+
						"PRIMARY KEY (id)"+
						") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
				System.out.println(stmt.executeUpdate(sql)+"  "+sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try{
					stmt.close();
					conn.close();
				}catch(Exception ex){
					ex.getStackTrace();
				}
			}
		
		}
		
	}
//	OK
	public List<String> getInsertCompanySQL(){
//		String userName = "racenter";
//		String password = "china-ops";
//		String url = "jdbc:mysql://172.16.56.62:3306/ecloud_auth";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> companys =  new ArrayList<String>();
		try {
			conn = Conn.getConn(Y_RACENTER_URL, Y_RACENTER_USER, Y_RACENTER_PASS);
			stmt = conn.createStatement();
			String comSql= "select * from company;";
			rs = stmt.executeQuery(comSql);
			while(rs.next()){
				String companyId = rs.getInt("id")+"";
				companys.add(companyId);
			}
			return companys;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public List<Dept> getInsertDeptSQL(){
//		String userName = "racenter";
//		String password = "china-ops";
//		String url = "jdbc:mysql://172.16.56.62:3306/ecloud_auth";
		List<String> companys = this.getInsertCompanySQL();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Dept> depts =  new ArrayList<Dept>();
		try {
			if(companys != null && companys.size()>0){
				conn = Conn.getConn(Y_RACENTER_URL, Y_RACENTER_USER, Y_RACENTER_PASS);
				stmt = conn.createStatement();
				for(String companyId:companys){
					String comSql= "select * from dept where company_id="+companyId;
					rs = stmt.executeQuery(comSql);
					while(rs.next()){
						Dept d = new Dept();
						d.setCompanyId(rs.getString("company_id"));
						d.setDeptId(rs.getInt("id")+"");
						depts.add(d);
					}
				}
			}
			if(depts != null && depts.size()>0)
				return depts;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
//	OK
	public void insertSQLTable(){
		List<String> insertCompanyList = this.getInsertCompanySQL();
		List<Dept> insertDeptList = this.getInsertDeptSQL();
		if(insertCompanyList != null && insertCompanyList.size()>0){
//			要写入的数据库信息
//			String userName = "billing";
//			String password = "china-ops";
//			String url = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
			Connection conn = null;
			Statement stmt = null;
			conn = Conn.getConn(M_URL, M_USER, M_PASS);
			try {
				stmt = conn.createStatement();
				for(String companyId :insertCompanyList){
					stmt.executeUpdate("INSERT INTO company_res_order_cfg(company_id) values("+companyId+");");
				}
				for(Dept d :insertDeptList){
					stmt.executeUpdate("INSERT INTO dept_res_order_cfg(company_id,department_id) values("+d.getCompanyId()+","+d.getDeptId()+");");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					stmt.close();
					stmt = null;
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
//	OK
	public List<String> updateResourcesSQL(){
		List<String> companys = getInsertCompanySQL();
		List<Dept> depts = getInsertDeptSQL();
		List<String> resources = new ArrayList<String>();
		if(companys != null && companys.size()>0){
			for(String companyId:companys){
				String updateCompanySQL = "select company_id,CONCAT('update company_res_order_cfg set max_instances=',max_instances,CONCAT(',max_volumes=',max_volumes),CONCAT(',max_volumes=',max_volumes),CONCAT(',min_ebs_size=',min_ebs_size),CONCAT(',max_ebs_size=',max_ebs_size),CONCAT(',max_snapshots=',max_snapshots),CONCAT(',max_security_groups=',max_security_groups),CONCAT(',max_keypairs=',max_keypairs),CONCAT(' where company_id=',company_id)	) as updatesql from company_res_cfg where company_id="+companyId;
				resources.add(updateCompanySQL);
			}
		}
		if(depts != null && depts.size()>0){
			for(Dept d:depts){
				String updateDeptSQL = "select department_id,company_id,CONCAT('update dept_res_order_cfg set max_instances=',max_instances,CONCAT(',max_volumes=',max_volumes),CONCAT(',max_volumes=',max_volumes),CONCAT(',min_ebs_size=',min_ebs_size),CONCAT(',max_ebs_size=',max_ebs_size),CONCAT(',max_snapshots=',max_snapshots),CONCAT(',max_security_groups=0,max_keypairs=0'),CONCAT(' where company_id=',company_id,CONCAT(' and department_id=',department_id))) as updatesql from dept_res_cfg where company_id="+d.getCompanyId()+" and department_id="+d.getDeptId();
				resources.add(updateDeptSQL);
			}
		}
		
//		要构建的数据库的SQL语句信息
//		String userName = "metadata";
//		String password = "china-ops";
//		String url = "jdbc:mysql://172.16.56.64:3306/ecloud_metadata";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> insertSQL = new ArrayList<String>();
		try {
			if(resources != null && resources.size()>0){
				conn = Conn.getConn(Y_METADATE_URL, Y_METADATE_USER, Y_METADATE_PASS);
				stmt = conn.createStatement();
				for(String sql:resources){
					rs = stmt.executeQuery(sql);
					while(rs.next()){
						insertSQL.add(rs.getString("updatesql"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertSQL;
	}
//	OK
	public void updateResourcesTable(){
		List<String> resources = this.updateResourcesSQL();
		Connection conn = null;
		Statement stmt = null;
		if(resources != null && resources.size()>0){
			conn = Conn.getConn(M_URL, M_USER, M_PASS);
			try {
				stmt = conn.createStatement();
				int i = 0;
				for(String sql:resources){
					stmt.executeUpdate(sql);
					i++;
				}
				System.out.println("一共执行了 "+i+" 条更新资源SQL");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(stmt != null){
						stmt.close();
						stmt = null;
					}
					if(conn != null){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
//	OK
	public List<String> updateOrderSQL(){
		List<String> companys = getInsertCompanySQL();
		List<Dept> depts = getInsertDeptSQL();
		List<String> orders = new ArrayList<String>();
		
//		要构建的数据库的SQL语句信息
//		String userName = "billing";
//		String password = "china-ops";
//		String url = "jdbc:mysql://172.16.56.74:3306/ecloud_billing";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	/*
		List<String> cecuOrders = new ArrayList<String>();
		List<String> cvolOrders = new ArrayList<String>();
		List<String> csnapOrders = new ArrayList<String>();
		List<String> chaOrders = new ArrayList<String>();
		List<String> cipOrders = new ArrayList<String>();
		List<String> celbOrders = new ArrayList<String>();
		
		List<String> dEcuOrders = new ArrayList<String>();
		List<String> dVolOrders = new ArrayList<String>();
		List<String> dSnapOrders = new ArrayList<String>();
		List<String> dHaOrders = new ArrayList<String>();
		List<String> dElbOrders = new ArrayList<String>();
		List<String> dIpOrders = new ArrayList<String>();
	*/
		conn = Conn.getConn(Y_BILLING_URL, Y_BILLING_USER, Y_BILLING_PASS);
		try {
			stmt = conn.createStatement();
			/*构建公司订单SQL语句*/
			if(companys != null && companys.size()>0){
//				ECU个数订单
				for(String companyId:companys){
					String ecuSql = "select CONCAT('update company_res_order_cfg set ecu_hour_count=',ecu_p_h.ecu_hour_count,CONCAT(',ecu_day_count=',ecu_p_d.ecu_day_count),CONCAT(',ecu_mon_count=',ecu_p_m.ecu_mon_count),CONCAT( ' where company_id=',ecu_p_m.company_id)) as sqls from (select quantity as ecu_hour_count,company_id from company_ordered_products where charge_duration='pay-for-time' and product_id='1') as ecu_p_h ,(select quantity as ecu_day_count,company_id from company_ordered_products where charge_duration='daily' and product_id='1') as ecu_p_d ,(select quantity as ecu_mon_count,company_id from company_ordered_products where charge_duration='monthly' and product_id='1') as ecu_p_m where ecu_p_h.company_id=ecu_p_d.company_id and ecu_p_h.company_id=ecu_p_m.company_id and ecu_p_m.company_id="+companyId+" group by ecu_p_h.company_id";
					rs = stmt.executeQuery(ecuSql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				存储大小订单
				for(String companyId:companys){
					String volsql = "select CONCAT('update company_res_order_cfg set vol_hour_size=',vol_p_h.vol_hour_size,CONCAT(',vol_day_size=',vol_p_d.vol_day_size),CONCAT(',vol_mon_size=',vol_p_m.vol_mon_size),CONCAT( ' where company_id=',vol_p_m.company_id)) as sqls from (select quantity as vol_hour_size,company_id from company_ordered_products where charge_duration='pay-for-time' and product_id='2') as vol_p_h ,(select quantity as vol_day_size,company_id from company_ordered_products where charge_duration='daily' and product_id='2') as vol_p_d ,(select quantity as vol_mon_size,company_id from company_ordered_products where charge_duration='monthly' and product_id='2') as vol_p_m where vol_p_h.company_id=vol_p_d.company_id and vol_p_h.company_id=vol_p_m.company_id and vol_p_m.company_id="+companyId+"  group by vol_p_h.company_id";
					rs = stmt.executeQuery(volsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				快照大小订单
				for(String companyId:companys){
					String snapsql = "select CONCAT('update company_res_order_cfg set snap_hour_size=',snap_p_h.snap_hour_size,CONCAT(',snap_day_size=',snap_p_d.snap_day_size),CONCAT(',snap_mon_size=',snap_p_m.snap_mon_size),CONCAT( ' where company_id=',snap_p_m.company_id)) as sqls from (select quantity as snap_hour_size,company_id from company_ordered_products where charge_duration='pay-for-time' and product_id='3') as snap_p_h ,(select quantity as snap_day_size,company_id from company_ordered_products where charge_duration='daily' and product_id='3') as snap_p_d ,(select quantity as snap_mon_size,company_id from company_ordered_products where charge_duration='monthly' and product_id='3') as snap_p_m where snap_p_h.company_id=snap_p_d.company_id and snap_p_h.company_id=snap_p_m.company_id and snap_p_m.company_id="+companyId+"  group by snap_p_h.company_id";
					rs = stmt.executeQuery(snapsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				主机保护个数订单
				for(String companyId:companys){
					String hasql = "select CONCAT('update company_res_order_cfg set ha_mon_count=',ha_p_m.ha_mon_count,CONCAT( ' where company_id=',ha_p_m.company_id)) as sqls from (select quantity as ha_mon_count,company_id from company_ordered_products where charge_duration='monthly' and product_id='58' and company_id="+companyId+") as ha_p_m group by ha_p_m.company_id";
					rs = stmt.executeQuery(hasql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				负载均衡个数订单
				for(String companyId:companys){
					String elbsql = "select CONCAT('update company_res_order_cfg set elb_mon_count=',elb_p_m.elb_mon_count,CONCAT( ' where company_id=',elb_p_m.company_id)) as sqls from (select quantity as elb_mon_count,company_id from company_ordered_products where charge_duration='monthly' and product_id='20' and company_id="+companyId+") as elb_p_m group by elb_p_m.company_id";
					rs = stmt.executeQuery(elbsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				ip个数订单
				for(String companyId:companys){
					String ipsql = "select CONCAT('update company_res_order_cfg set ip_mon_count=',ip_p_m.ip_mon_count,CONCAT( ' where company_id=',ip_p_m.company_id)) as sqls from (select quantity as ip_mon_count,company_id from company_ordered_products where charge_duration='monthly' and product_id='4' and company_id="+companyId+") as ip_p_m group by ip_p_m.company_id";
					rs = stmt.executeQuery(ipsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
			}
			/*构建部门订单SQL语句*/
			if(depts != null && depts.size()>0){
//				ECU个数订单
				for(Dept d:depts){
					String decuSql = "select CONCAT('update dept_res_order_cfg set ecu_hour_count=0,ecu_day_count=0',CONCAT(',ecu_mon_count=',ecu_p_m.ecu_mon_count),CONCAT(' where company_id=',ecu_p_m.company_id),CONCAT(' and department_id=',ecu_p_m.department_id)) as sqls from (select quantity as ecu_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='1') as ecu_p_m, (select quantity as ecu_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='1') as ecu_p_m1 where ecu_p_m.department_id=ecu_p_m1.department_id and ecu_p_m.company_id=ecu_p_m1.company_id and ecu_p_m.company_id="+ d.getCompanyId()+" and ecu_p_m.department_id="+d.getDeptId()+" group by ecu_p_m.company_id ,ecu_p_m.department_id";
					rs = stmt.executeQuery(decuSql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				存储大小订单
				for(Dept d:depts){
					String dvolsql = "select CONCAT('update dept_res_order_cfg set vol_hour_size=0,vol_day_size=0',	CONCAT(',vol_mon_size=',vol_p_m.vol_mon_size),CONCAT(' where company_id=',vol_p_m.company_id),CONCAT(' and department_id=',vol_p_m.department_id)) as sqls from (select quantity as vol_mon_size,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='2') as vol_p_m, (select quantity as vol_mon_size,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='2') as vol_p_m1 where vol_p_m.department_id=vol_p_m1.department_id and vol_p_m.company_id=vol_p_m1.company_id and vol_p_m.company_id="+d.getCompanyId()+" and vol_p_m.department_id="+d.getDeptId()+" group by vol_p_m.company_id ,vol_p_m.department_id";
					rs = stmt.executeQuery(dvolsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				快照大小订单
				for(Dept d:depts){
					String dsnapsql = "select CONCAT('update dept_res_order_cfg set snap_hour_size=0,snap_day_size=0',CONCAT(',snap_mon_size=',snap_p_m.snap_mon_size),CONCAT(' where company_id=',snap_p_m.company_id),CONCAT(' and department_id=',snap_p_m.department_id)) as sqls from (select quantity as snap_mon_size,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='3') as snap_p_m, (select quantity as snap_mon_size,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='3') as snap_p_m1 where snap_p_m.department_id=snap_p_m1.department_id and snap_p_m.company_id=snap_p_m1.company_id and snap_p_m.company_id="+d.getCompanyId()+" and snap_p_m.department_id="+d.getDeptId()+" group by snap_p_m.company_id ,snap_p_m.department_id";
					rs = stmt.executeQuery(dsnapsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				主机保护订单
				for(Dept d:depts){
					String dhasql = "select CONCAT('update dept_res_order_cfg set ha_mon_count=',ha_p_m.ha_mon_count,CONCAT(' where company_id=',ha_p_m.company_id),CONCAT(' and department_id=',ha_p_m.department_id)) as sqls from (select quantity as ha_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='58') as ha_p_m, (select quantity as ha_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='58') as ha_p_m1 where ha_p_m.department_id=ha_p_m1.department_id and ha_p_m.company_id=ha_p_m1.company_id and ha_p_m.company_id="+d.getCompanyId()+" and ha_p_m.department_id="+d.getDeptId()+" group by ha_p_m.company_id ,ha_p_m.department_id";
					rs = stmt.executeQuery(dhasql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				负载均衡订单
				for(Dept d:depts){
					String delbsql = "select CONCAT('update dept_res_order_cfg set elb_mon_count=',elb_p_m.elb_mon_count,CONCAT(' where company_id=',elb_p_m.company_id),CONCAT(' and department_id=',elb_p_m.department_id)) as sqls from (select quantity as elb_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='20') as elb_p_m, (select quantity as elb_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='20') as elb_p_m1 where elb_p_m.department_id=elb_p_m1.department_id and elb_p_m.company_id=elb_p_m1.company_id and elb_p_m.company_id="+d.getCompanyId()+" and elb_p_m.department_id="+d.getDeptId()+" group by elb_p_m.company_id ,elb_p_m.department_id";
					rs = stmt.executeQuery(delbsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
//				ip个数订单
				for(Dept d:depts){
					String dipsql = "select CONCAT('update dept_res_order_cfg set ip_mon_count=',ip_p_m.ip_mon_count,CONCAT(' where company_id=',ip_p_m.company_id),CONCAT(' and department_id=',ip_p_m.department_id)) as sqls from (select quantity as ip_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='4') as ip_p_m, (select quantity as ip_mon_count,company_id,department_id from department_ordered_products where charge_duration='monthly' and product_id='4') as ip_p_m1 where ip_p_m.department_id=ip_p_m1.department_id and ip_p_m.company_id=ip_p_m1.company_id and ip_p_m.company_id="+d.getCompanyId()+" and ip_p_m.department_id="+d.getDeptId()+" group by ip_p_m.company_id ,ip_p_m.department_id";
					rs = stmt.executeQuery(dipsql);
					while(rs.next()){
						orders.add(rs.getString("sqls"));
					}
				}
			}
			return orders;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				rs = null;
				stmt.close();
				stmt = null;
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
//	OK
	public void updateOrderTable(){
		List<String> orders = this.updateOrderSQL();
		Connection conn = null;
		Statement stmt = null;
		if(orders != null && orders.size()>0){
			conn = Conn.getConn(M_URL, M_USER, M_PASS);
			try {
				stmt = conn.createStatement();
				int i = 0;
				for(String sql:orders){
					stmt.executeUpdate(sql);
					i++;
				}
				System.out.println("一共执行了 "+i+" 条更新订单SQL");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(stmt != null){
						stmt.close();
						stmt = null;
					}
					if(conn != null){
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
