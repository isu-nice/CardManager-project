package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	private static String dburl = "jdbc:mysql://localhost:3306/cardmanagerdb?useSSL=false";
	private static String dbUser = "cardmanageruser";
	private static String dbpasswd = "connect123!@#";

	//SELECT
	public List<BusinessCard> searchBusinessCard(String keyword){
		List<BusinessCard> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		String sql = "SELECT * FROM businessCard where name like '%?%'";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, keyword);
			
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String companyName = rs.getString("companyName");
					//Date date = rs.getDate("createDate");
					BusinessCard businessCard = new BusinessCard(name,phone,companyName);
					list.add(businessCard); 
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//INSERT
	public BusinessCard addBusinessCard(BusinessCard businessCard){

		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		String sql = "INSERT INTO businesscard(name, phone, companyName) VALUES ( ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, businessCard.getName());
			ps.setString(2, businessCard.getPhone());
			ps.setString(3, businessCard.getCompanyName());
			//ps.set(4, businessCard.getCreateDate());

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return businessCard;
	}
}
