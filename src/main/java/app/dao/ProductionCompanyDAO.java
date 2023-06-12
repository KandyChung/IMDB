package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import app.dao.utils.DatabaseUtils;
import app.model.ProductionCompany;

public class ProductionCompanyDAO {

	public static final ProductionCompany getProductionCompanyByName(String name) {
				
		try 
		{
			String sql = "SELECT *\n"
						+ "FROM imbd.production_company\n"
						+ "WHERE lower(proco_name) like '%" 
						+ name + "%' ";
			
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement =connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				
				ProductionCompany pc = new ProductionCompany
						(
							result.getInt("proco_id"),
							result.getString("proco_name")
						);
			
				return pc;
			}
			DatabaseUtils.closeConnection(connection);
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static final void insertProductionCompanyByName(String name)
	{
		try
		{
			String sql = "INSERT INTO imbd.production_company(proco_id, proco_name)\n"
						+ "VALUES(" + getLastIDFromProductionCompany() +", '" 
						+ name + "') ";
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getLastIDFromProductionCompany()
	{
		int last_id = 0;
		
		try
		{
			String sql = "SELECT MAX(proco_id)\n"
						+ "FROM imbd.production_company";
			
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				last_id = result.getInt("max(proco_id)");
			}
			
			DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		last_id = last_id+1;
		
		return last_id;
	}
}