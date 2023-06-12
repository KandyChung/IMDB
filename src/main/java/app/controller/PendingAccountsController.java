package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import app.model.Account;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PendingAccountsController {

	public static Handler servePendingAccountPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        List<String> accountOutputArray = new ArrayList<>();
        List<Account> accounts = AccountDAO.getPendingAccounts();
        if (accounts == null)
        {
        	System.out.println("No Pending Accounts");
        	model.put("noPendingAccounts", true);
        	ctx.render(Template.PENDINGACCOUNT, model);
        }
        else
        {
        	for (Account account: accounts)
	        {
        		accountOutputArray.add(getAccountsOutput(account));
	        }
        	model.put("noPendingAccounts", false);
	        model.put("accountOutputArray", accountOutputArray);
	        model.put("eachAccountOutput", accountOutputArray.toString());
	    	ctx.render(Template.PENDINGACCOUNT, model);
        }
    	ctx.render(Template.PENDINGACCOUNT, model);
    };
    
    public static Handler handleApproveAccounts = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	List<String> accountOutputArray = new ArrayList<>();
    	
    	//check if have response or not
    	if(getQueryResponse(ctx) != null)
    	{
    		//compare the characters because the value accept have showid. Ex: accept4
    		if(getQueryResponse(ctx).substring(0,6).equalsIgnoreCase("accept"))
    		{
    			AccountDAO.updatePendingAccountByUsername(getQueryResponse(ctx).substring(6));
    			
    			List<Account> accounts = AccountDAO.getPendingAccounts();
    	        
    			//check if there are pending shows again
    			//if not, show "no pending show"
    			if (accounts == null)
    	        {
    	        	model.put("noPendingAccounts", true);
    	        	ctx.render(Template.PENDINGACCOUNT, model);
    	        }
    	        else
    	        {
    	        	for (Account account: accounts)
    		        {
    	        		accountOutputArray.add(getAccountsOutput(account));
    		        }
    	        	model.put("noPendingAccounts", false);
    		        model.put("accountOutputArray", accountOutputArray);
    		        model.put("eachAccountOutput", accountOutputArray.toString());
    		    	ctx.render(Template.PENDINGACCOUNT, model);
    	        }
    		}
    		//if reject, remove row from database
    		else if (getQueryResponse(ctx).substring(0,6).equalsIgnoreCase("reject"))
    		{
    			AccountDAO.deleteAccountFromDatabaseByUsername(getQueryResponse(ctx).substring(6));			
    			List<Account> accounts = AccountDAO.getPendingAccounts();
    	       
    			if (accounts == null)
    	        {
    	        	model.put("noPendingAccounts", true);
    	        	ctx.render(Template.PENDINGACCOUNT, model);
    	        }
    	        else
    	        {
    	        	for (Account account: accounts)
    		        {
    	        		accountOutputArray.add(getAccountsOutput(account));
    		        }
    	        	model.put("noPendingAccounts", false);
    		        model.put("accountOutputArray", accountOutputArray);
    		        model.put("eachAccountOutput", accountOutputArray.toString());
    		    	ctx.render(Template.PENDINGACCOUNT, model);
    	        }
    		}
    		else
    		{
    			System.out.println("error");
    		}
    	}
    	
    	ctx.render(Template.PENDINGACCOUNT, model);
    };
    
    public static String getAccountsOutput(Account account)
    {
    	String showsOutput = "";
    	
    	showsOutput = showsOutput + 
    			"<div style = \"display: \n" + 
    			"    					 vertical-align : middle;\n" + 
    			"    					 max-width : 100%;\n" + 
    			"    					 border: 1px solid;\" >\n" + 
    			"			</br>"+
    			"    		<label>Fullname: "+account.getFirstName()+" "+account.getLastName()+"</label></br>\n" + 
    			"    		<label>Gender: "+account.getGender()+"</label></br>\n" + 
    			"    		<label>Email: "+account.getEmail()+"</label></br>\n" + 
    			"    		<label>Country: "+account.getCountry()+"</label></br>\n" + 
    			"    		<label>Type Of Account: "+account.getType()+"</label></br>\n" + 
    			"    		\n" + 
    			"	<form id=\"submitForm\" method = \"post\">\n" + 
    			"		<input type=\"radio\"  name=\"response\" value=\"accept" + account.getUsername()+ "\">\n" + 
    			"  		<label for=\"accept" + account.getUsername()+ "\">Accept</label>\n" + 
    			"  		\n" + 
    			"  		<input type=\"radio\" name=\"response\" value=\"reject" + account.getUsername()+"\">\n" + 
    			"  		<label for=\"reject" + account.getUsername()+ "\">Reject</label> \n" + 
    			"  		\n" + 
    			"  		<input id =\"submitFormLoginButton\" type=\"submit\" value=\"Submit\">\n" + 
    			"	</form>"+
    			"</div>";
    	
    	return showsOutput;
    }
    
    public static String getQueryResponse(Context ctx)
    {
		return ctx.formParam("response");
    }
}
