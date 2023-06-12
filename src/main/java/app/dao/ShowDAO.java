package app.dao;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import app.model.ProductionCompany;
import app.model.Show;

import org.eclipse.collections.impl.list.mutable.FastList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShowDAO {

	
	
	public static final Show getShowByTitle(String title) {
		
		List <Show> shows = new ArrayList<>();
		
		try {
			
			String sql = "SELECT * FROM imbd.show WHERE show_title ='" + title + "'";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) 
            {
                // 2) Add it to the list we have prepared
            	if (result.getBoolean("pending") == false || result.getBoolean("suspense") == false)
            	{
            		
            		shows.add(
	            	// 1) Create a new account object
	            	new Show(result.getInt("showid"),
                          result.getString("show_Title"),
                          result.getDouble("length"),
                          result.getString("genre"),
                          result.getInt("year"), 
                          result.getBoolean("movie"),
                          result.getBoolean("series"),
                          result.getInt("proco_id"),
                          result.getBoolean("pending"),
                          result.getDate("submission_date"),
                          result.getBoolean("suspense")
                          )
	                );
            		
	            	
            	}
            }
			
            DatabaseUtils.closeConnection(connection);
            
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		if(!shows.isEmpty()) return shows.get(0);
        return null;
	}
	
	public static Show getShowByID(int id){
		
		try {
			// Here you prepare your sql statement
			String sql = "SELECT *\n"
					+"FROM imbd.show WHERE showid= '" + id+"' ";
			// Execute the query
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next())  {
            	if (result.getBoolean("pending") == false  || result.getBoolean("suspense") == false)
            	{
            		Show show = new Show(result.getInt("showid"),
                        result.getString("show_Title"),
                        result.getDouble("length"),
                        result.getString("genre"),
                        result.getInt("year"), 
                        result.getBoolean("movie"),
                        result.getBoolean("series"),
                        result.getInt("proco_id"),
                        result.getBoolean("pending"),
                        result.getDate("submission_date"),
                        result.getBoolean("suspense"))  ;
            		return show;
            	}  
		}
           DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
	return null;
	}

	public static Show searchShowByShowTitle(String title){
		
		try {
			String searching = title.toLowerCase();
			// Here you prepare your sql statement
			String sql = "SELECT *\n"
					+"FROM imbd.show WHERE lower(show_title) like '%" + title+"%' ";
			// Execute the query
			Connection connection = DatabaseUtils.connectToDatabase();
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        
	        while(result.next())  {
	        	if (result.getBoolean("pending") == false  || result.getBoolean("suspense") == false)
	        	{
		        		Show show = new Show(result.getInt("showid"),
		                    result.getString("show_Title"),
		                    result.getDouble("length"),
		                    result.getString("genre"),
		                    result.getInt("year"), 
		                    result.getBoolean("movie"),
		                    result.getBoolean("series"),
		                    result.getInt("proco_id"),
		                    result.getBoolean("pending"),
		                    result.getDate("submission_date"),
	                          result.getBoolean("suspense"))  ;
		        	 
		        		return show;
	        	}
	        	
		}
	       DatabaseUtils.closeConnection(connection);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
	return null;
	}
	
public static final List<Show> getAllPendingShows() {
		
		List <Show> shows = new ArrayList<>();
		
		try {
			
			String sql = "SELECT * FROM imbd.show WHERE pending = 1";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) 
            {
            	if(result.getBoolean("suspense") == false)
            	{
	            	shows.add(
	            	// 1) Create a new account object
	            	new Show(result.getInt("showid"),
	                      result.getString("show_Title"),
	                      result.getDouble("length"),
	                      result.getString("genre"),
	                      result.getInt("year"), 
	                      result.getBoolean("movie"),
	                      result.getBoolean("series"),
	                      result.getInt("proco_id"),
	                      result.getBoolean("pending"),
	                      result.getDate("submission_date"),
                          result.getBoolean("suspense")
	                      )
	                );     
            	}
            	    	
            }
			
            DatabaseUtils.closeConnection(connection);
            
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		if(!shows.isEmpty()) return shows;
        return null;
	}

	public static final List <Show> getAllShowsWithoutPendingOrSuspension()
	{
		List<Show> shows = new ArrayList<>();
		
		try {
			
			String sql = "SELECT * FROM imbd.show WHERE pending = 0 AND suspense = 0";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) 
            {
            	shows.add(
            	// 1) Create a new account object
            	new Show(result.getInt("showid"),
                      result.getString("show_Title"),
                      result.getDouble("length"),
                      result.getString("genre"),
                      result.getInt("year"), 
                      result.getBoolean("movie"),
                      result.getBoolean("series"),
                      result.getInt("proco_id"),
                      result.getBoolean("pending"),
                      result.getDate("submission_date"),
                      result.getBoolean("suspense")
                      )
                );         	
            }
			
            DatabaseUtils.closeConnection(connection);
            
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		if(!shows.isEmpty()) return shows;
        return null;
	}

	public static final List <Show> getShowsByTitle(String title) {
		
		List <Show> shows = new ArrayList<>();
		
		try {
			
			String sql = "SELECT * FROM imbd.show WHERE show_title like '%" + title + "%'";
			
			Connection connection = DatabaseUtils.connectToDatabase();
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        
	        while(result.next()) {
	            // 2) Add it to the list we have prepared
	        	if (result.getBoolean("pending") == false  || result.getBoolean("suspense") == false)
	        	{
		        		shows.add(
		              // 1) Create a new account object
		              new Show(result.getInt("showid"),
		                      result.getString("show_Title"),
		                      result.getDouble("length"),
		                      result.getString("genre"),
		                      result.getInt("year"), 
		                      result.getBoolean("movie"),
		                      result.getBoolean("series"),
		                      result.getInt("proco_id"),
		                      result.getBoolean("pending"),
		                      result.getDate("submission_date"),
	                          result.getBoolean("suspense")
		                      
		                      )
		            );
	        	}
	            
	        
	        }
			
	        DatabaseUtils.closeConnection(connection);
	        
		}
		
		catch (Exception e) {
	        e.printStackTrace();
	    }
		
		if(!shows.isEmpty()) return shows;
	    return null;
	}
	
	public static void insertShow(String show_title, double length, String genre, int year, 
									boolean series, boolean movies, String productionCompanyName)
	{
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = dateFormat.format(date);
		ProductionCompany productionCompany = ProductionCompanyDAO.getProductionCompanyByName(productionCompanyName);
		try
		{
			String sql = "INSERT INTO imbd.show(showid, show_title, genre, length, movie, series, proco_id, year, pending, submission_date, suspense)\n"
						+ "VALUES (" + getLastIDFromShow() + ", '" + show_title + "' , '" + genre + "'," + length + "," + movies
						+ "," + series + "," + productionCompany.getId() + "," + year + ", 1,'" + formattedDate +"',0 )";
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
	
	public static void updatePendingShowByShowID(int showid)
	{
		try
		{
			String sql = "UPDATE `imbd`.`show` SET `pending` = '0' WHERE (`showid` = '" + showid +"')";
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
	
	public static void suspenseShowByShowID(int showid)
	{
		try
		{
			String sql = "UPDATE `imbd`.`show` SET `suspense` = '1' WHERE (`showid` = '" + showid +"')";
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
	
	public static void editShowDataByShowID(String genre, double length, int year, int showid)
	{
		try
		{
			String sql = "UPDATE `imbd`.`show` SET `genre` = ' "+genre+"', `length` = '"+length+"', `year`='"+year+"' WHERE (`showid` = '"+showid+"')";
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
	//DELETE FROM `imbd`.`show` WHERE (`showid` = '4');
	
	public static void deleteRowFromDatabaseByShowID(int showid)
	{
		try
		{
			String sql = "DELETE FROM `imbd`.`show` WHERE (`showid` = '"+showid+"')";
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
	
	public static int getLastIDFromShow()
	{
		int last_id = 0;
		
		try
		{
			String sql = "SELECT MAX(showid)\n"
						+ "FROM imbd.show";
			
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				last_id = result.getInt("max(showid)");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		last_id = last_id + 1;
		
		return last_id;
	}
	
}