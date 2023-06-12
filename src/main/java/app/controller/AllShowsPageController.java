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

public class AllShowsPageController {

	public static Handler serveAllShowsPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        List<String> showsOutputWithForm = new ArrayList<>();
        List<Show> shows = ShowDAO.getAllShowsWithoutPendingOrSuspension();
        if (shows == null)
        {
        	model.put("noShows", true);
        	ctx.render(Template.SHOWS, model);
        }
        //if have pending shows, list all pending shows
        else
        {
        	for (Show show: shows)
	        {
        		showsOutputWithForm.add(getShowsOutput(show));

	        	
	        }
        	model.put("noShows", false);
        	model.put("showsOutputWithFormArray", showsOutputWithForm);
        	model.put("showsOutputWithForm", showsOutputWithForm.toString());
	    	ctx.render(Template.SHOWS, model);
        }
    	ctx.render(Template.SHOWS, model);   
    };
    
    public static Handler suspenseShow = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	
    	if(getQuerySuspense(ctx) != null)
    	{
    		if(getQuerySuspense(ctx).substring(0, 4).equalsIgnoreCase("true"))
    		{
    			ShowDAO.suspenseShowByShowID(Integer.parseInt(getQuerySuspense(ctx).substring(4, 5)));
    			List<String> showsOutputWithForm = new ArrayList<>();
    			List<Show> shows = ShowDAO.getAllShowsWithoutPendingOrSuspension();
    			if (shows == null)
    	        {
    	        	model.put("noShows", true);
    	        	ctx.render(Template.SHOWS, model);
    	        }
    	        //if have pending shows, list all pending shows
    	        else
    	        {
    	        	for (Show show: shows)
    		        {
    		        	showsOutputWithForm.add(getShowsOutput(show));
    		        }
    	        	model.put("noShows", false);
    	        	model.put("showsOutputWithFormArray", showsOutputWithForm);
    	        	model.put("showsOutputWithForm", showsOutputWithForm.toString());
    		    	ctx.render(Template.SHOWS, model);
    	        }
    		}
    	}
    	else
    	{
    		System.out.println("error");
    	}
    	
    	ctx.render(Template.SHOWS, model);
    };
    
    public static String getShowsOutput(Show show)
    {
    	String showsOutputWithForm = "";
    	
    	showsOutputWithForm = showsOutputWithForm + "<div style = \"display: inline-block \n" + 
    			"    		vertical-align : middle; \n" + 
    			"    		border: 1px solid; \n" + 
    			"    		max-width : 100% \" >  \n" + 
    			"    <h3 style =\"text-align: center\"> \n" + 
    			"    	"+show.getShowTitle()+ "\n" + 
    			"   	</h3> \n" + 
    			"    <img style = \" vertical-align: middle; hidden; \n" + 
    			"    			 	max-height:300px; \n" + 
    			"	 				max-width:60%\"  \n" + 
    			"    			src=\""+"/img/shows/"+show.getShowid()+".jpg\" \r\n" + 
    			"    		alt=\"$Show1Img\" \n" + 
    			"    	   	height = '300px' >\n" + 
    			"    		<div style =\" display: inline-block\n" + 
    			"		   			    vertical-align : middle;\n" + 
    			"		  			   	border: 1px solid;\n" + 
    			"	    			    max-width : 100%\" >\n" + 
    			"    	     	<li> Genre: "+show.getGenre()+"</li>\n" + 
    			"    			<li> Runtime:"+show.getLength()+"</li> \n" + 
    			"    			<li> Year released:"+show.getYear()+"</li> \n" + 
    			"    		<form id=\"submitForm\" method = \"post\">	\n" + 
    			"			    <input type=\"hidden\" id= \"suspense\"  name= \"suspense\"  value= \"true"+show.getShowid()+"\">\n" + 
    			"			    <input type=\"submit\" value=\"Suspense\" > \n" + 
    			"    		</form> \n" + 
    			
    			"    		</div> \n" + 
    			
    			"</div>";
    	
    	return showsOutputWithForm;
    }
    
    public static String getQuerySuspense(Context ctx)
    {
		return ctx.formParam("suspense");
    }
}
