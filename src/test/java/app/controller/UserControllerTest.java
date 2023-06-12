package app.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.dao.AccountDAO;
import app.model.Account;
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest 
{
	private Account caramel;
	private static List<Account> accounts;
	@BeforeAll
	public void setup() {
		accounts = new ArrayList<Account>();
		caramel = new Account("caramel6", "cara", "mel", "australia", "female", "caramel6@gmail.com","admin");
		accounts.add(caramel);
	}
	@Test
	public void duplicatedUserName_returnsFalse_whenUserIsPresentInDB() 
	{
		boolean check = duplicatedUserName(caramel.getUsername());
		
		assertFalse(check);
	}
	
	@Test
	public void authenticate_returnsFalse_whenUserNotInDatabase() {
		boolean check = UserController.authenticate("This is not a username in the database", "not a password");
		assertFalse(check);
	}
	
	@Test
	public void authenticate_returnsFalse_whenUsernameOrPasswordIsNull() {
		boolean check = UserController.authenticate(null, "not a password");
		assertFalse(check);
		check = UserController.authenticate("caramel6", null);
		assertFalse(check);
		check = UserController.authenticate(null, null);
		assertFalse(check);
		check = UserController.authenticate("caramel6", "password");
		assertTrue(check);
	}
	
	@Test
	public void duplicatedUserName_returnsTrue_whenUserIsNotPresentInDB() 
	{
		boolean check = duplicatedUserName("This is unique");
		
		assertTrue(check);
	}
	
	@Test
	public void checkTwoPasswords_returnsFalse_whenPasswordsDoNotMatch() {
		boolean check = UserController.checkTwoPasswords("abcD", "abcd");
		
		assertFalse(check);
		
		check = UserController.checkTwoPasswords("123", "123");
		
		assertTrue(check);
	}
	
	@Test
	public void checkTwoPasswords_returnsFalse_whenEitherPasswordIsNull() {
		boolean check = UserController.checkTwoPasswords(null, null);
		assertFalse(check);
		check = UserController.checkTwoPasswords(null, "Not Null");
		assertFalse(check);
		check = UserController.checkTwoPasswords("Not Null", null);
		assertFalse(check);
		check = UserController.checkTwoPasswords("Not Null", "Not Null");
		assertTrue(check);
	}
	
	@Test
	public void duplicatedUserName_returnsFalse_whenAccountIsNull() {
		boolean check = duplicatedUserName(null);
		assertFalse(check);
	}
	
	
	public static boolean duplicatedUserName(String username)
    {
    	if (username == null)
    	{
    		return false;
    	}
    	Account user = AccountDAO.getUserByUsername(username);
    	if(user == null)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
	
	//This is essentially meant to mock the functionality of the database, where the list of accounts is effectively our database
	public static Account getUserByUsername(String username) {
		Account user = null;
		for(Account account: accounts) 
		{
			if(account.getUsername() == username) 
			{
				user = account;
			}
		}
		return user;
	}
}
