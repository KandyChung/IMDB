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
public class PendingAccountsControllerTest 
{
	private String password;
	private Account test;
	private static List<Account> accountDatabase;
	private static List<Account> pendingAccounts;
	@BeforeAll
	public void setup() 
	{
		//public Account(String username, String fn, String ln,String c, String g, String email, String type)
		accountDatabase = new ArrayList<Account>();
		pendingAccounts = new ArrayList<Account>();
		test = new Account("Test","test","tester","australia","male","test@gmail.com","pco");
		password = "abc123";
		
		
	}
	
	//Testing if the pending accounts are properly displayed to an admin
	@Test
	public void getPendingAccounts_returnsCorrectAccounts_whenDatabaseContainsPendingAccount() 
	{
		creatingNewAccount(test.getUsername(), password, test.getEmail(), test.getCountry(), test.getGender(), 
				test.getFirstName(), test.getLastName(), test.getType());
		List<Account> accounts = getPendingAccounts();
		assertTrue(checkIfUserInList(accounts, this.test));
		deleteAccountFromDatabaseByUsername(test.getUsername());
		accounts = getPendingAccounts();
		assertFalse(checkIfUserInList(accounts, this.test));
	}
	
	//Testing if an accepted account is shown to the pending accounts page
	@Test
	public void getPendingAccounts_doesNotReturnAccount_thatHasBeenAccepted()
	{
		Account caramel = new Account("caramel6", "cara", "mel", "australia", "female", "caramel6@gmail.com","admin");
		this.updatePendingAccountByUsername(caramel.getUsername());
		List<Account> accounts = getPendingAccounts();
		assertFalse(checkIfUserInList(accounts,caramel));
	}
	
	
	public static boolean checkIfUserInList(List<Account> list, Account test) {
		boolean check = false;
		if(list != null) 
		{
			for(Account acc : list) 
			{
				if(acc.getUsername().equals(test.getUsername())) {
					check = true;
				}
			}
		}
		return check;
	}
	
	//Checking if the approval system works
	@Test
	public void updatePendingAccountByUsername_removesPlayerFromPendingList_whenUserIsPending() 
	{
		creatingNewAccount(test.getUsername(), password, test.getEmail(), test.getCountry(), test.getGender(), 
				test.getFirstName(), test.getLastName(), test.getType());
		List<Account> accounts = getPendingAccounts();
		assertTrue(checkIfUserInList(accounts, this.test));
		updatePendingAccountByUsername(test.getUsername());
		accounts = getPendingAccounts();
		assertFalse(checkIfUserInList(accounts, this.test));
		deleteAccountFromDatabaseByUsername(test.getUsername());
		accounts = getPendingAccounts();
		assertFalse(checkIfUserInList(accounts, this.test));
	}
	
	//Testing that a standard user never has pending status
	@Test
	public void getPendingAccounts_doesNotReturnUser_ifUserIsAStandardUser() {
		creatingNewAccount(test.getUsername(), password, test.getEmail(), test.getCountry(), test.getGender(), 
				test.getFirstName(), test.getLastName(), "users");
		List<Account> accounts = getPendingAccounts();
		assertFalse(checkIfUserInList(accounts, this.test));
		deleteAccountFromDatabaseByUsername(test.getUsername());
	}
	
	//THESE STATIC METHODS ARE MEANT TO SIMULATE HOW OUR METHODS WORK IN CONJUNCTION WITH OUR DATABASE
	//This method is works essentially identically to how the AccountDAO.creatingNewAccount method functions,
	//it has just been modified so we can test the functionality here.
	public static void creatingNewAccount(String username, String password, String email, String country, String gender,
			String firstName,String lastname, String type) {
		Account acc = new Account(username, email, country, gender, firstName, lastname, type);
		if(type.equals("users")) 
		{
			accountDatabase.add(acc);
		}
		else
		{
			pendingAccounts.add(acc);
		}
		
	}
	
	//This simply returns the pending accounts
	public static List<Account> getPendingAccounts(){
		return pendingAccounts;
	}
	
	public static void deleteAccountFromDatabaseByUsername(String username)
	{
		Account toBeDeleted = null;
		boolean check = true;
		for(Account account : pendingAccounts) 
		{
			if(account.getUsername().equals(username)) {
				check = false;
				toBeDeleted = account;
			}
		}
		if(check) {
			for(Account account : accountDatabase) 
			{
				if(account.getUsername().equals(username)) 
				{
					toBeDeleted = account;
				}
			}
			if(toBeDeleted != null) 
			{
				accountDatabase.remove(toBeDeleted);
			}
		}
		else
		{
			if(toBeDeleted != null) 
			{
				pendingAccounts.remove(toBeDeleted);
			}
		}
	}
	
	public static void updatePendingAccountByUsername(String username) 
	{
		boolean check = false;
		Account updated = null;
		for(Account acc : pendingAccounts) {
			if(acc.getUsername().equals(username)) 
			{
				updated = acc;
				check = true;
			}
		}
		if(check) 
		{
			pendingAccounts.remove(updated);
			accountDatabase.add(updated);
		}
	}
	
}
