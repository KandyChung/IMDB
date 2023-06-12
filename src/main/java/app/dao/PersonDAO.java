package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import app.model.Person;
import app.model.Show;

public class PersonDAO {
	
	
	public static Person getPersonByID(String ID) {
        // Fish out the results

        try {
            // Here you prepare your sql statement
            String sql = "select person_id, fullname, person.role, birthdate, bio\n" + 
            		"from imbd.person\r\n" + 
            		"where person_id=" + ID;

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) 
            {
            	//System.out.println(result.getString("person_id"));
            	Person person = new Person(Integer.parseInt(result.getString("person_id")),result.getString("fullname"),
            			result.getString("person.role"),java.sql.Date.valueOf(result.getString("birthdate")),
            			result.getString("bio"));
            	DatabaseUtils.closeConnection(connection);
            	return person;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
	
	public static int getNumPeopleInDB() {
		int num = 0;
		try {
            // Here you prepare your sql statement
            String sql = "select count(*) from imbd.person;";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) 
            {
            	num = result.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		return num;
	}
	
	public static Person getPersonByName(String actorname)
	{
		 try {
	            // Here you prepare your sql statement
	            String sql = "select person_id, fullname, person.role, birthdate, bio\n" + 
	            		"from imbd.person\r\n" + 
	            		"where fullname = '" + actorname + "' ";

	            // Execute the query
	            Connection connection = DatabaseUtils.connectToDatabase();
	            Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(sql);
	            
	            while(result.next()) 
	            {
	            	Person person = new Person(Integer.parseInt(result.getString("person_id")),
	            			result.getString("fullname")
	            			,result.getString("person.role")
	                		,java.sql.Date.valueOf(result.getString("birthdate")),
	                		result.getString("bio"));
	            	
	            	DatabaseUtils.closeConnection(connection);
	            	return person;
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
		 
		return null;
	}
	
	public static Person searchPersonByName(String actorname)
	{
		 try {
			 	String nameCleaned = actorname.toLowerCase();
	            // Here you prepare your sql statement
	            String sql = "select person_id, fullname, person.role, birthdate, bio\n" + 
	            		"from imbd.person\r\n" + 
	            		"where lower(fullname) like '%" + nameCleaned + "%' ";

	            // Execute the query
	            Connection connection = DatabaseUtils.connectToDatabase();
	            Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(sql);
	            while(result.next()) 
	            {
	            	
	            	Person person = new Person(Integer.parseInt(result.getString("person_id")),
	            			result.getString("fullname")
	            			,result.getString("person.role")
	                		,java.sql.Date.valueOf(result.getString("birthdate")),
	                		result.getString("bio"));
	            	
	            	DatabaseUtils.closeConnection(connection);
	            	return person;
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
		 
		return null;
	}

	public static List<Person> getPeopleByName(String queryActorname) {
		List <Person> people = new ArrayList<>();
		
		try {
			
			String sql = "SELECT * FROM imbd.person WHERE lower(fullname) like '%" + queryActorname + "%'";
			
			Connection connection = DatabaseUtils.connectToDatabase();
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        
	        while(result.next()) {
	            // 2) Add it to the list we have prepared
	            people.add(
	              // 1) Create a new account object
	            		new Person(Integer.parseInt(result.getString("person_id")),
		            			result.getString("fullname")
		            			,result.getString("person.role")
		                		,java.sql.Date.valueOf(result.getString("birthdate")),
		                		result.getString("bio"))
	            );
	        
	        }
			
	        DatabaseUtils.closeConnection(connection);
	        
		}
		
		catch (Exception e) {
	        e.printStackTrace();
	    }
		
		if(!people.isEmpty()) return people;
	    return null;
	}
}
