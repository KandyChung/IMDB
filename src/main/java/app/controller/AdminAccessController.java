package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.model.Show;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AdminAccessController { 
 
	//handle the page
	public static Handler serveAdminAccessPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        List<String> showsOutputArray = new ArrayList<>();
        List<Show> shows = ShowDAO.getAllPendingShows();
        //check if no pending shows
        if (shows == null)
        {
        	System.out.println("No Shows");
        	model.put("noPendingShows", true);
        	ctx.render(Template.ADMINACCESS, model);
        }
        //if have pending shows, list all pending shows
        else
        {
        	for (Show show: shows)
	        {
	        	showsOutputArray.add(getShowsOutput(show));
	        }
        	model.put("noPendingShows", false);
	        model.put("showDetialsArray", showsOutputArray);
	        model.put("eachShowOutput", showsOutputArray.toString());
	    	ctx.render(Template.ADMINACCESS, model);
        }
    };
    
    //this method handles submit button
    public static Handler handleApproveShows = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	List<String> showsOutputArray = new ArrayList<>();
    	
    	//check if have response or not
    	if(getQueryResponse(ctx) != null)
    	{
    		//compare the characters because the value accept have showid. Ex: accept4
    		if(getQueryResponse(ctx).substring(0,6).equalsIgnoreCase("accept"))
    		{
    			ShowDAO.updatePendingShowByShowID(Integer.parseInt(getQueryResponse(ctx).substring(6, 7)));
    			
    			List<Show> shows = ShowDAO.getAllPendingShows();
    	        
    			//check if there are pending shows again
    			//if not, show "no pending show"
    			if (shows == null)
    	        {
    	        	model.put("noPendingShows", true);
    	        	ctx.render(Template.ADMINACCESS, model);
    	        }
    	        else
    	        {
    	        	for (Show show: shows)
    		        {
    		        	showsOutputArray.add(getShowsOutput(show));
    		        }
    	        	model.put("noPendingShows", false);
    		        model.put("showDetialsArray", showsOutputArray);
    		        model.put("eachShowOutput", showsOutputArray.toString());
    		    	ctx.render(Template.ADMINACCESS, model);
    	        }
    		}
    		//if reject, remove row from database
    		else if (getQueryResponse(ctx).substring(0,6).equalsIgnoreCase("reject"))
    		{
    			ShowDAO.deleteRowFromDatabaseByShowID(Integer.parseInt(getQueryResponse(ctx).substring(6, 7)));
    			
    			List<Show> shows = ShowDAO.getAllPendingShows();
    			
    			//check if there are pending shows again
    			//if not, show "no pending show"
    			if (shows == null)
    	        {
    	        	model.put("noPendingShows", true);
    	        	ctx.render(Template.ADMINACCESS, model);
    	        }
    	        else
    	        {
    	        	for (Show show: shows)
    		        {
    		        	showsOutputArray.add(getShowsOutput(show));
    		        }
    	        	model.put("noPendingShows", false);
    		        model.put("showDetialsArray", showsOutputArray);
    		        model.put("eachShowOutput", showsOutputArray.toString());
    		    	ctx.render(Template.ADMINACCESS, model);
    	        }
    		}
    		else
    		{
    			System.out.println("error");
    		}
    	}
    	
    	ctx.render(Template.ADMINACCESS, model);
    };
    
    //help method
    public static String getShowsOutput(Show show)
    {
    	String showsOutput = "";
    	
    	showsOutput = showsOutput + 
    			"<div style = \" display:\n" + 
    			"    				vertical-align : middle;\n" + 
    			"    				border: 1px solid;\n" + 
    			"    				max-width : 100%\" >\n" + 
    			"    	<h3 style = \"text-align: center\">\n" + 
    			"    	\n" + show.getShowTitle()+ 
    			"    	\n" + 
    			"    	</h3>\n" + 
    			"    	\n" +  
    			"    		\n" + 
    			"    	<li> Genre: " + show.getGenre() + "</li>\n" + 
    			"    	<li> Runtime: " + show.getLength() +" </li>\n" + 
    			"    	<li> Year released: " + show.getYear() +"</li>\n" + 
    			"<form id=\"submitForm\" method = \"post\">\n" + 
    			"		<input type=\"radio\"  name=\"response\" value=\"accept" + show.getShowid()+ "\">\n" + 
    			"  		<label for=\"accept" + show.getShowid()+ "\">Accept</label>\n" + 
    			"  		\n" + 
    			"  		<input type=\"radio\" name=\"response\" value=\"reject" + show.getShowid()+"\">\n" + 
    			"  		<label for=\"reject" + show.getShowid()+ "\">Reject</label> \n" + 
    			"  		\n" + 
    			"  		<input id =\"submitFormLoginButton\" type=\"submit\" value=\"Submit\">\n" + 
    			"</form>"+
    			"    </div>" ;
    	
    	return showsOutput;
    }
    
    public static String getQueryResponse(Context ctx)
    {
		return ctx.formParam("response");
    }

}
