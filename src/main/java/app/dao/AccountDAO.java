package app.dao;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class AccountDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";


    /**
     * Method to fetch users from the database.
     * You should use this as an example for future queries, though the sql statement
     * will change -and you are supposed to write them.
     *
     * Current user: caramel 6, password (the password is "password" without quotes)
     * @param username what the user typed in the log in form.
     * @return Some of the user data to check on the password. Null if there
     *         no matching user.
     */
    public static Account getUserByUsername(String username) {
        // Fish out the results
        List<Account> accounts = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT username, password FROM account WHERE username ='" + username + "' AND NOT pending = 1";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
                accounts.add(
                  // 1) Create a new account object
                  new Account(result.getString("username"),
                          result.getString("password"))
                );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // If there is a result
        if(!accounts.isEmpty()) return accounts.get(0);
        // If we are here, something bad happened
        return null;
    }
    
    public static Account getUserDetailsByUsername(String username) {
        // Fish out the results
        List<Account> accounts = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM account WHERE username ='" + username + "'";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
                accounts.add(
                  // 1) Create a new account object
                  new Account(result.getString("username"),
                		  		result.getString("first_name"),
                		  		result.getString("last_name"),
                		  		result.getString("country"),
                		  		result.getString("gender"),
                		  		result.getString("email"),
                		  		result.getString("type"))
                );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // If there is a result
        if(!accounts.isEmpty()) return accounts.get(0);
        // If we are here, something bad happened
        return null;
    }

    /*
     * This method will set all new accounts to pending, waiting for admin confirmation
     */
    public static void creatingNewAccount(String username, String password, String email, String country, String gender
    		, String firstname, String lastname, String type)
    {
    	String hashedPassword = BCrypt.hashpw(password, AccountDAO.SALT);
    	try {
    		String sql;
            if(type.equals("users")) {
            	sql = "INSERT INTO imbd.account(username, password, email, country, gender, first_name, last_name, type, pending)\n"
    					+ "VALUES ('" + username + "', '" + hashedPassword + "' , '" + email + "','" + country + "','" + gender
    					+ "','" + firstname + "','" + lastname + "','" + type + "', '0')";
            }
            else {
    		sql = "INSERT INTO imbd.account(username, password, email, country, gender, first_name, last_name, type, pending)\n"
					+ "VALUES ('" + username + "', '" + hashedPassword + "' , '" + email + "','" + country + "','" + gender
					+ "','" + firstname + "','" + lastname + "','" + type + "', '1')";
            }
            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            
        
            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * This method will set all users' account pending value to 0 because 
     * users' account do not need permission from admin, only PCOs' account and Critics' account
     */
    public static void setAllUsersAccountToPendingFalse()
    {
    	try
    	{
    		String sql = "UPDATE `imbd`.`account` SET `pending` = '0' WHERE (`type` = 'Users');";
    		Connection connection = DatabaseUtils.connectToDatabase();
    		Statement statement = connection.createStatement();
    		statement.execute(sql);
    		
    		DatabaseUtils.closeConnection(connection);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /*
     * This method will get all the accounts that has the pending value of 1
     */
    
    public static List<Account> getPendingAccounts()
    {
    	List<Account> accounts = new ArrayList<>();
    	try
    	{
    		String sql = "SELECT * FROM account WHERE pending = 1";
    		
    		Connection connection = DatabaseUtils.connectToDatabase();
    		Statement statement = connection.createStatement();
    		ResultSet result = statement.executeQuery(sql);
           
            while(result.next()) {
                
                accounts.add(
                  
                  new Account(result.getString("username"),
                		  		result.getString("first_name"),
                		  		result.getString("last_name"),
                		  		result.getString("country"),
                		  		result.getString("gender"),
                		  		result.getString("email"),
                		  		result.getString("type"))
                );
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	if(!accounts.isEmpty()) return accounts;
        return null;
    }
    
    /*
     * This method will set the pending value of a given account to 0
     */
    public static void updatePendingAccountByUsername(String username)
	{
		try
		{
			String sql = "UPDATE `imbd`.`account` SET `pending` = '0' WHERE (`username` = '" + username +"')";
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
    
    /*
     * This method will delete accounts that got rejected by admin from the database
     */
    
    public static void deleteAccountFromDatabaseByUsername(String username)
	{
		try
		{
			String sql = "DELETE FROM `imbd`.`account` WHERE (`username` = '"+username+"')";
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

}
