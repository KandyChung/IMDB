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

public class PcoEditDataController {
	
	public static Handler serveEditDataPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        List<Show> shows = ShowDAO.getAllShowsWithoutPendingOrSuspension();
    	List<String> showsOutputWithForm = new ArrayList<>();
    	if (shows == null)
        {
        	model.put("noShows", true);
        	ctx.render(Template.EDITDATA, model);
        }
        else
        {
        	
        	for (Show show: shows)
	        {
        		showsOutputWithForm.add(getShowsOutput(show));
        		
	        }
        	model.put("noShows", false);
        	model.put("showsOutputWithFormArray", showsOutputWithForm);
        	model.put("showsOutputWithForm", showsOutputWithForm.toString());
	    	ctx.render(Template.EDITDATA, model);
        }
        ctx.render(Template.EDITDATA, model);
    };
    
    public static Handler handleEditData = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	
    	List<String> showsOutputWithForm = new ArrayList<>();
    	
    	ShowDAO.editShowDataByShowID(getQueryShowGenre(ctx), 
    			Double.parseDouble(getQueryShowLength(ctx)), 
    			Integer.parseInt(getQueryShowYear(ctx)),
    			Integer.parseInt(getQueryShowID(ctx)));
    	List<Show> shows = ShowDAO.getAllShowsWithoutPendingOrSuspension();
    	if (shows == null)
        {
        	model.put("noShows", true);
        	ctx.render(Template.EDITDATA, model);
        }
        else
        {
        	for (Show show: shows)
        	{
            	
        		showsOutputWithForm.add(getShowsOutput(show));
        		
	        }
        	model.put("noShows", false);
        	model.put("showsOutputWithFormArray", showsOutputWithForm);
        	model.put("showsOutputWithForm", showsOutputWithForm.toString());

        	
        	
	    	ctx.render(Template.EDITDATA, model);
        }
    	
    	
    	ctx.render(Template.EDITDATA, model);
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
    			"		<form id=\"submitForm"+show.getShowid()+"\" method = \"post\" > " +
    			"				<input type=\"hidden\"  id=\"editDate\" name=\"showid\" value=\"" + show.getShowid() + "\"> </li>\n" +
    			"    	     	<li> Genre: <input type=\"text\" id=\"editDate\" name=\"genre\" value=\"" + show.getGenre()+ "\"> </li>\n" + 
    			"    			<li> Runtime: <input type=\"text\" id=\"editDate\" name=\"length\" value=\"" + show.getLength() + "\"> </li> \n" + 
    			"    			<li> Year released: <input type=\"text\" id=\"editDate\" name=\"year\" value=\""+ show.getYear() + "\"> </li> \n" + 
    			"   			<input id =\"submitFormLoginButton\" type=\"submit\" value=\"Submit\">" +
    			"		</form>" +
    			"    		</div> \n" + 
    			"</div>";
    	
    	return showsOutputWithForm;
    }
    
    public static String getQueryShowGenre(Context ctx)
    {
		return ctx.formParam("genre");
    }
    public static String getQueryShowLength(Context ctx)
    {
		return ctx.formParam("length");
    }
    public static String getQueryShowYear(Context ctx)
    {
		return ctx.formParam("year");
    }
    public static String getQueryShowID(Context ctx)
    {
		return ctx.formParam("showid");
    }

}
    
